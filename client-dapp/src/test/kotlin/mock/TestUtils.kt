package mock

import it.airgap.beaconsdk.core.data.*
import mock.blockchain.MockBlockchain
import mock.blockchain.message.BlockchainMockRequest
import mock.blockchain.message.BlockchainMockResponse
import mock.blockchain.message.PermissionMockRequest
import mock.blockchain.message.PermissionMockResponse
import it.airgap.beaconsdk.core.internal.di.DependencyRegistry
import it.airgap.beaconsdk.core.internal.message.BeaconIncomingConnectionMessage
import it.airgap.beaconsdk.core.internal.message.VersionedBeaconMessage
import it.airgap.beaconsdk.core.message.*
import kotlinx.coroutines.flow.MutableSharedFlow
import mock.data.MockAppMetadata
import mock.data.MockPermission

// -- extensions --

internal fun <T> MutableSharedFlow<Result<T>>.tryEmitValues(values: List<T>) {
    values.forEach { tryEmit(Result.success(it)) }
}

internal val DependencyRegistry.versionedBeaconMessageContext: VersionedBeaconMessage.Context
    get() = VersionedBeaconMessage.Context(blockchainRegistry, compat)

// -- converters --

internal fun versionedBeaconMessage(
    message: BeaconMessage,
    senderId: String = "senderId",
    context: VersionedBeaconMessage.Context,
): VersionedBeaconMessage =
    VersionedBeaconMessage.from(senderId, message, context)

// -- flows --

internal fun beaconConnectionMessageFlow(
    replay: Int,
): MutableSharedFlow<Result<BeaconIncomingConnectionMessage>> = MutableSharedFlow(replay)

// -- factories --

internal fun permissionBeaconRequest(
    type: String = "permission_request",
    id: String = "id",
    senderId: String = "senderId",
    appMetadata: MockAppMetadata = MockAppMetadata(senderId, "mockApp"),
    blockchainIdentifier: String = MockBlockchain.IDENTIFIER,
    origin: Connection.Id = Connection.Id.P2P(senderId),
    destination: Connection.Id? = Connection.Id.P2P("receiverId"),
    version: String = "version",
): PermissionBeaconRequest = PermissionMockRequest(type, id, version, blockchainIdentifier, senderId, origin, destination, appMetadata)

internal fun blockchainBeaconRequest(
    type: String = "beacon_request",
    id: String = "id",
    senderId: String = "senderId",
    accountId: String = "accountId",
    appMetadata: AppMetadata = MockAppMetadata(senderId, "mockApp"),
    blockchainIdentifier: String = MockBlockchain.IDENTIFIER,
    origin: Connection.Id = Connection.Id.P2P(senderId),
    destination: Connection.Id? = Connection.Id.P2P("receiverId"),
    version: String = "version"
): BlockchainBeaconRequest = BlockchainMockRequest(type, id, version, blockchainIdentifier, senderId, appMetadata, origin, destination, accountId)

internal fun permissionBeaconResponse(
    type: String = "permission_response",
    id: String = "id",
    blockchainIdentifier: String = MockBlockchain.IDENTIFIER,
    version: String = "version",
    destination: Connection.Id = Connection.Id.P2P("receiverId"),
): PermissionBeaconResponse = PermissionMockResponse(type, id, version, destination, blockchainIdentifier)

internal fun blockchainBeaconResponse(
    type: String = "beacon_response",
    id: String = "id",
    blockchainIdentifier: String = MockBlockchain.IDENTIFIER,
    version: String = "version",
    destination: Connection.Id = Connection.Id.P2P("receiverId"),
): BlockchainBeaconResponse = BlockchainMockResponse(type, id, version, destination, blockchainIdentifier)

internal fun acknowledgeBeaconResponse(
    id: String = "id",
    senderId: String = "senderId",
    version: String = "version",
    destination: Connection.Id = Connection.Id.P2P("receiverId"),
): AcknowledgeBeaconResponse =
    AcknowledgeBeaconResponse(id, version, destination, senderId)

internal fun errorBeaconResponse(
    id: String = "id",
    errorType: BeaconError = BeaconError.Unknown,
    description: String? = null,
    version: String = "version",
    destination: Connection.Id = Connection.Id.P2P("receiverId"),
): ErrorBeaconResponse = ErrorBeaconResponse(id, version, destination, errorType, description)

internal fun disconnectBeaconMessage(
    id: String = "id",
    senderId: String = "senderId",
    version: String = "version",
    origin: Connection.Id = Connection.Id.P2P(senderId),
    destination: Connection.Id = Connection.Id.P2P("receiverId"),
): DisconnectBeaconMessage = DisconnectBeaconMessage(id, senderId, version, origin, destination)

internal fun errorBeaconResponses(
    id: String = "id",
    version: String = "version",
    destination: Connection.Id = Connection.Id.P2P("receiverId"),
): List<ErrorBeaconResponse> =
    listOf(
        errorBeaconResponse(id, BeaconError.Aborted, version = version, destination = destination),
        errorBeaconResponse(id, BeaconError.Unknown, version = version, destination = destination),
    )

internal fun beaconRequests(version: String = "version", senderId: String = "senderId", destination: Connection.Id = Connection.Id.P2P("senderId")): List<BeaconRequest> =
    listOf(
        permissionBeaconRequest(version = version, senderId = senderId, destination = destination),
        blockchainBeaconRequest(version = version, senderId = senderId, destination = destination),
    )

internal fun beaconVersionedResponses(
    version: String = "version",
    senderId: String = "senderId",
    context: VersionedBeaconMessage.Context
): List<VersionedBeaconMessage> =
    listOf(
        VersionedBeaconMessage.from(senderId, permissionBeaconResponse(version = version), context),
        VersionedBeaconMessage.from(senderId, blockchainBeaconResponse(version = version), context),
        VersionedBeaconMessage.from(senderId, acknowledgeBeaconResponse(version = version), context),
    ) + errorBeaconResponses(version = version).map { VersionedBeaconMessage.from(senderId, it, context) }

internal fun p2pPeers(
    number: Int = 1,
    version: String = "version",
    paired: Boolean = false,
): List<P2pPeer> =
    (0 until number).map {
        P2pPeer("id#$it", "name#$it", "publicKey#$it", "relayServer#$it", version, isPaired = paired)
    }

internal fun appMetadata(number: Int = 1): List<MockAppMetadata> =
    (0 until number).map { MockAppMetadata("sender#$it", "name#$it") }

internal fun permissions(
    number: Int = 1,
    blockchainIdentifier: String = MockBlockchain.IDENTIFIER,
): List<Permission> =
    (0 until number).map {
        MockPermission(
            blockchainIdentifier,
            "accountIdentifier#$it",
            "sender#$it",
            it.toLong(),
        )
    }