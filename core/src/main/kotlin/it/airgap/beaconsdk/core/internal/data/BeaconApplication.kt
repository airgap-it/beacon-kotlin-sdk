package it.airgap.beaconsdk.core.internal.data

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.crypto.data.KeyPair

@InternalBeaconApi
public data class BeaconApplication(
    val keyPair: KeyPair,
    val name: String,
    val icon: String? = null,
    val url: String? = null,
) {
    @InternalBeaconApi
    public data class Partial(
        val name: String,
        val icon: String? = null,
        val url: String? = null,
    ) {
        internal fun toFinal(keyPair: KeyPair): BeaconApplication = BeaconApplication(keyPair, name, icon, url)
    }
}
