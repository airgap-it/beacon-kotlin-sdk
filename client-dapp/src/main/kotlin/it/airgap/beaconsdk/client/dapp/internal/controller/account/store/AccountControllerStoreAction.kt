package it.airgap.beaconsdk.client.dapp.internal.controller.account.store

import it.airgap.beaconsdk.client.dapp.data.PairedAccount
import it.airgap.beaconsdk.core.data.Peer
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public sealed interface AccountControllerStoreAction

internal data class OnPeerPaired(val peer: Peer) : AccountControllerStoreAction
internal data class OnPeerRemoved(val peer: Peer) : AccountControllerStoreAction
internal object ResetActivePeer : AccountControllerStoreAction

internal data class OnNewActiveAccount(val account: PairedAccount) : AccountControllerStoreAction
internal object ResetActiveAccount : AccountControllerStoreAction

internal object HardReset : AccountControllerStoreAction