package mock.data

import it.airgap.beaconsdk.core.data.AppMetadata
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import mock.blockchain.MockBlockchain

@Serializable
internal data class MockAppMetadata(
    override val senderId: String,
    override val name: String,
    override val icon: String? = null,
) : AppMetadata() {
    @Required
    override val blockchainIdentifier: String = MockBlockchain.IDENTIFIER
}