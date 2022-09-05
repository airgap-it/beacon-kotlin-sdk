package it.airgap.beaconsdk.core.internal.blockchain.creator

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.message.v1.V1BeaconMessage
import it.airgap.beaconsdk.core.message.BeaconMessage

@InternalBeaconApi
public interface V1BeaconMessageBlockchainCreator {
    public fun from(senderId: String, message: BeaconMessage): Result<V1BeaconMessage>
}