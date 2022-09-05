package it.airgap.beaconsdk.core.internal.blockchain.creator

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.message.v2.V2BeaconMessage
import it.airgap.beaconsdk.core.message.BeaconMessage

@InternalBeaconApi
public interface V2BeaconMessageBlockchainCreator {
    public fun from(senderId: String, message: BeaconMessage): Result<V2BeaconMessage>
}