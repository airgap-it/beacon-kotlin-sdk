package it.airgap.beaconsdk.core.internal.blockchain.serializer

import it.airgap.beaconsdk.core.data.AppMetadata
import it.airgap.beaconsdk.core.data.BeaconError
import it.airgap.beaconsdk.core.data.Network
import it.airgap.beaconsdk.core.data.Permission
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlinx.serialization.KSerializer

@InternalBeaconApi
public interface DataBlockchainSerializer {
    public val network: KSerializer<Network>
    public val permission: KSerializer<Permission>
    public val appMetadata: KSerializer<AppMetadata>
    public val error: KSerializer<BeaconError>
}