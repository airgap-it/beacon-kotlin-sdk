package it.airgap.beaconsdk.blockchain.substrate

import it.airgap.beaconsdk.blockchain.substrate.internal.di.ExtendedDependencyRegistry
import it.airgap.beaconsdk.blockchain.substrate.internal.di.extend
import it.airgap.beaconsdk.core.blockchain.Blockchain
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.di.DependencyRegistry

/**
 * Substrate implementation of the [Blockchain] interface.
 */
public class Substrate internal constructor(
    @InternalBeaconApi override val creator: Blockchain.Creator,
    @InternalBeaconApi override val serializer: Blockchain.Serializer,
) : Blockchain {
    @InternalBeaconApi
    override val identifier: String = IDENTIFIER

    /**
     * Factory for [Substrate].
     *
     * @constructor Creates a factory required for dynamic [Substrate] blockchain registration.
     */
    public class Factory : Blockchain.Factory<Substrate> {
        override val identifier: String = IDENTIFIER

        private var _extendedDependencyRegistry: ExtendedDependencyRegistry? = null
        private fun extendedDependencyRegistry(dependencyRegistry: DependencyRegistry): ExtendedDependencyRegistry =
            _extendedDependencyRegistry ?: dependencyRegistry.extend().also { _extendedDependencyRegistry = it }

        @InternalBeaconApi
        override fun create(dependencyRegistry: DependencyRegistry): Substrate =
            extendedDependencyRegistry(dependencyRegistry).substrate

    }

    public companion object {
        internal const val IDENTIFIER = "substrate"
    }
}

/**
 * Creates a new instance of [Substrate.Factory].
 */
public fun substrate(): Substrate.Factory = Substrate.Factory()