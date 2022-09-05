package it.airgap.beaconsdk.client.dapp

import io.mockk.*
import mock.storage.MockDAppClientStorage
import it.airgap.beaconsdk.core.data.Connection
import it.airgap.beaconsdk.core.data.P2P
import it.airgap.beaconsdk.core.internal.BeaconSdk
import it.airgap.beaconsdk.core.blockchain.Blockchain
import it.airgap.beaconsdk.core.internal.BeaconConfiguration
import mock.blockchain.MockBlockchain
import it.airgap.beaconsdk.core.internal.crypto.data.KeyPair
import it.airgap.beaconsdk.core.internal.data.BeaconApplication
import it.airgap.beaconsdk.core.internal.di.DependencyRegistry
import mock.storage.MockSecureStorage
import it.airgap.beaconsdk.core.scope.BeaconScope
import it.airgap.beaconsdk.core.transport.p2p.P2pClient
import kotlinx.coroutines.runBlocking
import mock.mockBeaconSdk
import org.junit.Before
import org.junit.Test
import java.security.KeyStore
import kotlin.reflect.KClass
import kotlin.test.assertEquals

internal class BeaconDAppClientBuilderTest {

    private lateinit var beaconSdk: BeaconSdk
    private lateinit var dependencyRegistry: DependencyRegistry

    private val app: BeaconApplication = BeaconApplication(
        keyPair = KeyPair(byteArrayOf(0), byteArrayOf(0)),
        name = "mockApp"
    )

    private val blockchains: List<Blockchain.Factory<*>> = listOf(MockBlockchain.Factory())
    private val beaconId: String = "beaconId"

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        mockkStatic(KeyStore::class)
        every { KeyStore.getInstance(any()) } returns mockk(relaxed = true)

        dependencyRegistry = spyk(MockDependencyRegistry())
        beaconSdk = mockBeaconSdk(app = app, beaconId = beaconId, dependencyRegistry = dependencyRegistry)
    }

    @Test
    fun `builds BeaconWalletClient with default settings`() {
        runBlocking {
            val beaconScope = BeaconScope.Global
            every { dependencyRegistry.beaconScope } returns beaconScope

            val beaconWalletClient = BeaconDAppClient.Builder(app.name, MockDAppClientStorage(), MockSecureStorage()).apply {
                support(*blockchains.toTypedArray())
            }.build()
            val defaultConnections = emptyList<Connection>()

            assertEquals(app, beaconWalletClient.app)
            assertEquals(beaconId, beaconWalletClient.beaconId)
            assertEquals(beaconScope, beaconWalletClient.beaconScope)

            coVerify(exactly = 1) {
                beaconSdk.add(
                    beaconScope,
                    BeaconApplication.Partial(app.name, null, null),
                    BeaconConfiguration(ignoreUnsupportedBlockchains = false),
                    blockchains,
                    ofType(MockDAppClientStorage::class),
                    ofType(MockSecureStorage::class)
                )
            }
            verify(exactly = 1) { dependencyRegistry.connectionController(defaultConnections) }
        }
    }

    @Test
    fun `builds BeaconWalletClient with custom connections`() {
        runBlocking {
            val beaconScope = BeaconScope.Global
            every { dependencyRegistry.beaconScope } returns beaconScope

            val mockP2pFactory = mockkClass(P2pClient.Factory::class)
            val customConnections = listOf(P2P(mockP2pFactory))

            val beaconWalletClient = BeaconDAppClient.Builder(app.name, MockDAppClientStorage(), MockSecureStorage()).apply {
                support(*blockchains.toTypedArray())
                use(*customConnections.toTypedArray())
            }.build()

            assertEquals(app, beaconWalletClient.app)
            assertEquals(beaconId, beaconWalletClient.beaconId)
            assertEquals(beaconScope, beaconWalletClient.beaconScope)

            coVerify(exactly = 1) {
                beaconSdk.add(
                    beaconScope,
                    BeaconApplication.Partial(app.name, null, null),
                    BeaconConfiguration(ignoreUnsupportedBlockchains = false),
                    blockchains,
                    ofType(MockDAppClientStorage::class),
                    ofType(MockSecureStorage::class)
                )
            }
            verify(exactly = 1) { dependencyRegistry.connectionController(customConnections) }
        }
    }

    @Test
    fun `builds BeaconWalletClient with default settings when used as builder function`() {
        runBlocking {
            val beaconScope = BeaconScope.Global
            every { dependencyRegistry.beaconScope } returns beaconScope

            val beaconWalletClient = BeaconDAppClient(app.name, MockDAppClientStorage(), MockSecureStorage()) {
                support(*blockchains.toTypedArray())
            }
            val defaultConnections = emptyList<Connection>()

            assertEquals(app, beaconWalletClient.app)
            assertEquals(beaconId, beaconWalletClient.beaconId)
            assertEquals(beaconScope, beaconWalletClient.beaconScope)

            coVerify(exactly = 1) {
                beaconSdk.add(
                    beaconScope,
                    BeaconApplication.Partial(app.name, null, null),
                    BeaconConfiguration(ignoreUnsupportedBlockchains = false),
                    blockchains,
                    ofType(MockDAppClientStorage::class),
                    ofType(MockSecureStorage::class)
                )
            }
            verify(exactly = 1) { dependencyRegistry.connectionController(defaultConnections) }
        }
    }

    @Test
    fun `builds BeaconWalletClient with custom connections as builder function`() {
        runBlocking {
            val beaconScope = BeaconScope.Global
            every { dependencyRegistry.beaconScope } returns beaconScope

            val mockP2pFactory = mockkClass(P2pClient.Factory::class)
            val customConnections = listOf(P2P(mockP2pFactory))

            val beaconWalletClient = BeaconDAppClient(app.name, MockDAppClientStorage(), MockSecureStorage()) {
                support(*blockchains.toTypedArray())
                use(*customConnections.toTypedArray())
            }

            assertEquals(app, beaconWalletClient.app)
            assertEquals(beaconId, beaconWalletClient.beaconId)
            assertEquals(beaconScope, beaconWalletClient.beaconScope)

            coVerify(exactly = 1) {
                beaconSdk.add(
                    beaconScope,
                    BeaconApplication.Partial(app.name, null, null),
                    BeaconConfiguration(ignoreUnsupportedBlockchains = false),
                    blockchains,
                    ofType(MockDAppClientStorage::class),
                    ofType(MockSecureStorage::class)
                )
            }
            verify(exactly = 1) { dependencyRegistry.connectionController(customConnections) }
        }
    }

    private class MockDependencyRegistry : DependencyRegistry by mockk(relaxed = true) {
        override val extended: MutableMap<String, DependencyRegistry> = mutableMapOf()

        override fun addExtended(extended: DependencyRegistry) {
            val key = extended::class.simpleName ?: return
            this.extended[key] = extended
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : DependencyRegistry> findExtended(targetClass: KClass<T>): T? {
            val key = targetClass.simpleName ?: return null
            return this.extended[key] as T?
        }
    }
}