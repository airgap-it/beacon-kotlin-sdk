package it.airgap.beaconsdk.core.internal.blockchain.creator

import it.airgap.beaconsdk.core.data.Account
import it.airgap.beaconsdk.core.data.Connection
import it.airgap.beaconsdk.core.data.Permission
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.message.PermissionBeaconRequest
import it.airgap.beaconsdk.core.message.PermissionBeaconResponse

@InternalBeaconApi
public interface DataBlockchainCreator {
    public suspend fun extractIncomingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse, origin: Connection.Id): Result<List<Permission>>
    public suspend fun extractOutgoingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse): Result<List<Permission>>

    public fun extractAccounts(response: PermissionBeaconResponse): Result<List<Account>>
}