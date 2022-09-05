package it.airgap.beaconsdk.core.internal

import it.airgap.beaconsdk.core.blockchain.Blockchain
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.crypto.Crypto
import it.airgap.beaconsdk.core.internal.crypto.data.KeyPair
import it.airgap.beaconsdk.core.internal.data.BeaconApplication
import it.airgap.beaconsdk.core.internal.di.CoreDependencyRegistry
import it.airgap.beaconsdk.core.internal.di.DependencyRegistry
import it.airgap.beaconsdk.core.internal.storage.StorageManager
import it.airgap.beaconsdk.core.internal.utils.failWithUninitialized
import it.airgap.beaconsdk.core.internal.utils.toHexString
import it.airgap.beaconsdk.core.scope.BeaconScope
import it.airgap.beaconsdk.core.storage.SecureStorage
import it.airgap.beaconsdk.core.storage.Storage

@InternalBeaconApi
public class BeaconSdk {
    private val beaconContext: MutableMap<BeaconScope, BeaconContext> = mutableMapOf()

    public val beaconScopes: Set<BeaconScope>
        get() = beaconContext.keys

    public fun dependencyRegistry(beaconScope: BeaconScope): DependencyRegistry =
        beaconContext[beaconScope]?.dependencyRegistry ?: failWithUninitialized(beaconScope)

    public fun app(beaconScope: BeaconScope): BeaconApplication =
        beaconContext[beaconScope]?.app ?: failWithUninitialized(beaconScope)

    public fun beaconId(beaconScope: BeaconScope): String = app(beaconScope).keyPair.publicKey.toHexString().asString()

    public suspend fun add(
        beaconScope: BeaconScope,
        partialApp: BeaconApplication.Partial,
        configuration: BeaconConfiguration,
        blockchainFactories: List<Blockchain.Factory<*>>,
        storage: Storage,
        secureStorage: SecureStorage,
    ) {
        if (beaconContext.containsKey(beaconScope)) return

        val dependencyRegistry = CoreDependencyRegistry(beaconScope, blockchainFactories, storage, secureStorage, configuration)

        val storageManager = dependencyRegistry.storageManager
        val crypto = dependencyRegistry.crypto

        setSdkVersion(storageManager)

        val app = partialApp.toFinal(loadOrGenerateKeyPair(storageManager, crypto))

        beaconContext[beaconScope] = BeaconContext(app, dependencyRegistry)
    }

    private suspend fun setSdkVersion(storageManager: StorageManager) {
        storageManager.setSdkVersion(BeaconConfiguration.SDK_VERSION)
    }

    private suspend fun loadOrGenerateKeyPair(storageManager: StorageManager, crypto: Crypto): KeyPair {
        val seed = storageManager.getSdkSecretSeed()
            ?: crypto.guid().getOrThrow().also { storageManager.setSdkSecretSeed(it) }

        return crypto.getKeyPairFromSeed(seed).getOrThrow()
    }

    public companion object {
        internal const val TAG = "BeaconSdk"

        public val instance: BeaconSdk by lazy { BeaconSdk() }
    }
}