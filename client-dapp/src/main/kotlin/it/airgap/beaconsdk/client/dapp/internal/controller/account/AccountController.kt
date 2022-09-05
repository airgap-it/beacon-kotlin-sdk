package it.airgap.beaconsdk.client.dapp.internal.controller.account

import it.airgap.beaconsdk.client.dapp.data.PairedAccount
import it.airgap.beaconsdk.client.dapp.internal.controller.account.store.*
import it.airgap.beaconsdk.core.data.Connection
import it.airgap.beaconsdk.core.data.Peer
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.blockchain.BlockchainRegistry
import it.airgap.beaconsdk.core.message.PermissionBeaconResponse
import it.airgap.beaconsdk.core.transport.data.PairingResponse

@InternalBeaconApi
public class AccountController(private val store: AccountControllerStore, private val blockchainRegistry: BlockchainRegistry) {

    public suspend fun getActivePeer(): Peer? =
        store.state().getOrThrow().activePeer

    public suspend fun getActiveAccount(): PairedAccount? =
        store.state().getOrThrow().activeAccount

    public suspend fun clearActiveAccount() {
        store.intent(ResetActiveAccount)
    }

    public suspend fun clearAll() {
        store.intent(HardReset)
    }

    public suspend fun onPairingResponse(pairingResponse: PairingResponse): Result<Unit> =
        runCatching {
            store.intent(OnPeerPaired(pairingResponse.toPeer()))
        }

    public suspend fun onPermissionResponse(origin: Connection.Id, response: PermissionBeaconResponse): Result<Unit> =
        runCatching {
            val blockchain = blockchainRegistry.get(response.blockchainIdentifier)
            val accountId = blockchain.creator.data.extractAccounts(response).getOrThrow().firstOrNull() /* TODO: other selection criteria? */
            val account = accountId?.let { PairedAccount(it, origin.id) }

            account?.let { store.intent(OnNewActiveAccount(it)) }
        }
}