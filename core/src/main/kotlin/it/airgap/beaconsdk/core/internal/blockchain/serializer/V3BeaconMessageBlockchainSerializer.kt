package it.airgap.beaconsdk.core.internal.blockchain.serializer

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.message.v3.BlockchainV3BeaconRequestContent
import it.airgap.beaconsdk.core.internal.message.v3.BlockchainV3BeaconResponseContent
import it.airgap.beaconsdk.core.internal.message.v3.PermissionV3BeaconRequestContent
import it.airgap.beaconsdk.core.internal.message.v3.PermissionV3BeaconResponseContent
import kotlinx.serialization.KSerializer

@InternalBeaconApi
public interface V3BeaconMessageBlockchainSerializer {

    // -- request --

    public val permissionRequestData: KSerializer<PermissionV3BeaconRequestContent.BlockchainData>
    public val blockchainRequestData: KSerializer<BlockchainV3BeaconRequestContent.BlockchainData>

    // -- response --

    public val permissionResponseData: KSerializer<PermissionV3BeaconResponseContent.BlockchainData>
    public val blockchainResponseData: KSerializer<BlockchainV3BeaconResponseContent.BlockchainData>
}