package mock.data

import it.airgap.beaconsdk.core.data.Permission
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MockPermission(
    override val blockchainIdentifier: String,
    override val accountId: String,
    override val senderId: String,
    override val connectedAt: Long,
    val rest: Map<String, JsonElement> = emptyMap(),
) : Permission()