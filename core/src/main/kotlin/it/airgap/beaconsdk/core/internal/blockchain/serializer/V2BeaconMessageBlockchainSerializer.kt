package it.airgap.beaconsdk.core.internal.blockchain.serializer

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.message.v2.V2BeaconMessage
import kotlinx.serialization.KSerializer

@InternalBeaconApi
public interface V2BeaconMessageBlockchainSerializer {
    public val message: KSerializer<V2BeaconMessage>
}