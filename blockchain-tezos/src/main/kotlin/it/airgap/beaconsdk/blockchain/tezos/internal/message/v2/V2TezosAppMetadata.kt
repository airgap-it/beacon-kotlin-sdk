package it.airgap.beaconsdk.blockchain.tezos.internal.message.v2

import it.airgap.beaconsdk.blockchain.tezos.data.TezosAppMetadata
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlinx.serialization.Serializable

@InternalBeaconApi
@Serializable
public data class V2TezosAppMetadata(
    val senderId: String,
    val name: String,
    val icon: String? = null,
) {
    public fun toAppMetadata(): TezosAppMetadata = TezosAppMetadata(senderId, name, icon)

    public companion object {
        public fun fromAppMetadata(appMetadata: TezosAppMetadata): V2TezosAppMetadata =
            with(appMetadata) { V2TezosAppMetadata(senderId, name, icon) }
    }
}