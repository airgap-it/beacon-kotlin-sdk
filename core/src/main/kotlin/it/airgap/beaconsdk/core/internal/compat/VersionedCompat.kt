package it.airgap.beaconsdk.core.internal.compat

import it.airgap.beaconsdk.core.blockchain.Blockchain
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public interface VersionedCompat {
    public val withVersion: String
    public val blockchain: Blockchain
}