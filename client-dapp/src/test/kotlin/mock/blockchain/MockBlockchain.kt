package mock.blockchain

import it.airgap.beaconsdk.core.blockchain.Blockchain
import it.airgap.beaconsdk.core.internal.di.DependencyRegistry

internal class MockBlockchain : Blockchain {
    override val identifier: String = IDENTIFIER
    override val creator: Blockchain.Creator = MockBlockchainCreator()
    override val serializer: Blockchain.Serializer = MockBlockchainSerializer()

    class Factory : Blockchain.Factory<MockBlockchain> {
        override val identifier: String = IDENTIFIER
        override fun create(dependencyRegistry: DependencyRegistry): MockBlockchain = MockBlockchain()
    }

    companion object {
        const val IDENTIFIER: String = "mockBlockchain"
    }
}
