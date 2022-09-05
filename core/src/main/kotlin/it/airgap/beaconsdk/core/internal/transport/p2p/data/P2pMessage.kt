package it.airgap.beaconsdk.core.internal.transport.p2p.data

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public data class P2pMessage(val publicKey: String, val content: String) {
    public companion object {}
}