package mock.blockchain.message

import it.airgap.beaconsdk.core.data.AppMetadata
import it.airgap.beaconsdk.core.data.BeaconError
import it.airgap.beaconsdk.core.data.Connection
import mock.data.MockAppMetadata
import it.airgap.beaconsdk.core.internal.message.v1.V1BeaconMessage
import it.airgap.beaconsdk.core.internal.message.v2.V2BeaconMessage
import it.airgap.beaconsdk.core.internal.message.v3.*
import it.airgap.beaconsdk.core.message.BlockchainBeaconRequest
import it.airgap.beaconsdk.core.message.BlockchainBeaconResponse
import it.airgap.beaconsdk.core.message.PermissionBeaconRequest
import it.airgap.beaconsdk.core.message.PermissionBeaconResponse
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

internal enum class MockBeaconMessageType(val value: String) {
    Request("request"),
    Response("response");

    companion object {
        fun from(string: String): MockBeaconMessageType? = values().firstOrNull { string.contains(it.value) }
    }
}

@Serializable
internal data class PermissionMockRequest(
    val type: String,
    override val id: String,
    override val version: String,
    override val blockchainIdentifier: String,
    override val senderId: String,
    override val origin: Connection.Id,
    override val destination: Connection.Id?,
    override val appMetadata: MockAppMetadata,
    val rest: Map<String, JsonElement> = emptyMap(),
) : PermissionBeaconRequest() {
    fun toV1(): V1BeaconMessage =
        V1MockPermissionBeaconRequest(
            type,
            version,
            id,
            senderId,
            appMetadata,
            rest,
        )

    fun toV2(): V2BeaconMessage =
        V2MockPermissionBeaconRequest(
            type,
            version,
            id,
            senderId,
            appMetadata,
            rest,
        )

    fun toV3(): V3BeaconMessage.Content =
        PermissionV3BeaconRequestContent(
            blockchainIdentifier,
            V3MockPermissionBeaconRequestData(
                appMetadata,
                rest,
            ),
        )
}

@Serializable
internal data class BlockchainMockRequest(
    val type: String,
    override val id: String,
    override val version: String,
    override val blockchainIdentifier: String,
    override val senderId: String,
    override val appMetadata: @Contextual AppMetadata?,
    override val origin: Connection.Id,
    override val destination: Connection.Id?,
    override val accountId: String?,
    val rest: Map<String, JsonElement> = emptyMap(),
) : BlockchainBeaconRequest() {
    fun toV1(): V1BeaconMessage =
        V1MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Request,
        )

    fun toV2(): V2BeaconMessage =
        V2MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Request,
        )

    fun toV3(): V3BeaconMessage.Content =
        BlockchainV3BeaconRequestContent(
            blockchainIdentifier,
            accountId ?: "",
            V3MockBlockchainBeaconRequestData(rest),
        )
}

@Serializable
internal data class PermissionMockResponse(
    val type: String,
    override val id: String,
    override val version: String,
    override val destination: Connection.Id,
    override val blockchainIdentifier: String,
    val rest: Map<String, JsonElement> = emptyMap(),
) : PermissionBeaconResponse() {
    fun toV1(senderId: String): V1BeaconMessage =
        V1MockPermissionBeaconResponse(
            type,
            version,
            id,
            senderId,
            rest,
        )

    fun toV2(senderId: String): V2BeaconMessage =
        V2MockPermissionBeaconResponse(
            type,
            version,
            id,
            senderId,
            rest,
        )

    fun toV3(): V3BeaconMessage.Content =
        PermissionV3BeaconResponseContent(
            blockchainIdentifier,
            V3MockPermissionBeaconResponseData(rest),
        )
}

@Serializable
internal data class BlockchainMockResponse(
    val type: String,
    override val id: String,
    override val version: String,
    override val destination: Connection.Id,
    override val blockchainIdentifier: String,
    val rest: Map<String, JsonElement> = emptyMap(),
) : BlockchainBeaconResponse() {
    fun toV1(senderId: String): V1BeaconMessage =
        V1MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Response,
        )

    fun toV2(senderId: String): V2BeaconMessage =
        V2MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Response,
        )

    fun toV3(): V3BeaconMessage.Content =
        BlockchainV3BeaconResponseContent(
            blockchainIdentifier,
            V3MockBlockchainBeaconResponseData(rest),
        )
}

@Serializable
internal sealed class MockError : BeaconError()