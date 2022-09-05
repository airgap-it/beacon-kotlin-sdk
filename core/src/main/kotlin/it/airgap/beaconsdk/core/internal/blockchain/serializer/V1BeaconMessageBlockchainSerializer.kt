package it.airgap.beaconsdk.core.internal.blockchain.serializer

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.message.v1.V1BeaconMessage
import kotlinx.serialization.KSerializer

@InternalBeaconApi
public interface V1BeaconMessageBlockchainSerializer {
    public val message: KSerializer<V1BeaconMessage>
}