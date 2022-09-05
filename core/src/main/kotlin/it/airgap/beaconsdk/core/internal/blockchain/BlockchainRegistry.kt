package it.airgap.beaconsdk.core.internal.blockchain

import it.airgap.beaconsdk.core.blockchain.Blockchain
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.utils.failWithBlockchainNotFound
import it.airgap.beaconsdk.core.internal.utils.getAndDispose
import it.airgap.beaconsdk.core.internal.utils.getOrPutIfNotNull

@InternalBeaconApi
public class BlockchainRegistry internal constructor(factories: Map<String, () -> Blockchain>, blockchains: Map<String, Blockchain> = emptyMap()) {
    internal val factories: MutableMap<String, () -> Blockchain> = factories.toMutableMap()
    internal val blockchains: MutableMap<String, Blockchain> = blockchains.toMutableMap()

    public fun get(type: String): Blockchain = getOrNull(type) ?: failWithBlockchainNotFound(type)

    public fun getOrNull(type: String): Blockchain? = blockchains.getOrPutIfNotNull(type) {
        factories.getAndDispose(type)?.invoke()
    }
}