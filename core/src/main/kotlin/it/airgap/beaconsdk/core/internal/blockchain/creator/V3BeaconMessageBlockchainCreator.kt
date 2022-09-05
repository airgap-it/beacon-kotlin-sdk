package it.airgap.beaconsdk.core.internal.blockchain.creator

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.message.v3.V3BeaconMessage
import it.airgap.beaconsdk.core.message.BeaconMessage

@InternalBeaconApi
public interface V3BeaconMessageBlockchainCreator {
    public fun contentFrom(message: BeaconMessage): Result<V3BeaconMessage.Content>
}