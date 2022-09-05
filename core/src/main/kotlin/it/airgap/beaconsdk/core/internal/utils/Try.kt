package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public inline fun <T> runCatchingFlat(block: () -> Result<T>): Result<T> =
    try {
        block()
    } catch (e: Exception) {
        Result.failure(e)
    }

@InternalBeaconApi
public inline fun <T> tryLog(tag: String, block: () -> T): T? =
    try {
        block()
    } catch (e: Exception) {
        logError(tag, e)
        null
    }

@InternalBeaconApi
public inline fun <T> runCatchingRepeat(times: Int, action: () -> T): Result<T> =
    runCatchingFlatRepeat(times) { runCatching(action) }

@InternalBeaconApi
public inline fun <T> runCatchingFlatRepeat(times: Int, action: () -> Result<T>): Result<T> {
    require(times > 0)

    var result: Result<T>
    var counter = times

    do {
        result = action()
        if (result.isSuccess) break
        if (result.isFailure) counter--
    } while (counter > 0)

    return result
}