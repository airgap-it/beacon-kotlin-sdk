package it.airgap.beaconsdk.core.scope

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

public sealed interface BeaconScope {
    public object Global : BeaconScope
    public data class Instance(public val id: String) : BeaconScope
}

@InternalBeaconApi
public fun BeaconScope(id: String?): BeaconScope =
    id?.let { BeaconScope.Instance(it) } ?: BeaconScope.Global