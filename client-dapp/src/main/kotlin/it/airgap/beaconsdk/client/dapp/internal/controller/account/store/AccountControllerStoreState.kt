package it.airgap.beaconsdk.client.dapp.internal.controller.account.store

import it.airgap.beaconsdk.client.dapp.data.PairedAccount
import it.airgap.beaconsdk.core.data.Peer
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public data class AccountControllerStoreState(
    val activeAccount: PairedAccount?,
    val activePeer: Peer?,
)
