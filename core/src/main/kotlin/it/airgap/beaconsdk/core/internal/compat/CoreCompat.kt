package it.airgap.beaconsdk.core.internal.compat

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.compat.v2_0_0.CompatWithV2_0_0
import it.airgap.beaconsdk.core.scope.BeaconScope

@InternalBeaconApi
public class CoreCompat(beaconScope: BeaconScope? = null) : Compat<VersionedCompat>() {
    init {
        register(CompatWithV2_0_0(beaconScope))
    }
}