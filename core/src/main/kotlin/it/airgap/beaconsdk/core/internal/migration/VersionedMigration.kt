package it.airgap.beaconsdk.core.internal.migration

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.utils.success

@InternalBeaconApi
public abstract class VersionedMigration {
    public abstract val fromVersion: String

    public fun migrationIdentifier(target: Migration.Target): String =
        "from_$fromVersion@${target.identifier}"

    public abstract fun targets(target: Migration.Target): Boolean

    public abstract suspend fun perform(target: Migration.Target): Result<Unit>

    protected fun skip(): Result<Unit> = Result.success()
}