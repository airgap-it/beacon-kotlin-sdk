package mock.data

import it.airgap.beaconsdk.core.data.Network
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import mock.blockchain.MockBlockchain

@Serializable
internal data class MockNetwork(
    override val name: String? = null,
    override val rpcUrl: String? = null,
) : Network() {
    @Required
    override val blockchainIdentifier: String = MockBlockchain.IDENTIFIER
    override val identifier: String
        get() = mutableListOf(TYPE).apply {
            name?.let { add("name:$it") }
            rpcUrl?.let { add("rpc:$it") }
        }.joinToString("-")


    companion object {
        const val TYPE: String = "mock"
    }
}