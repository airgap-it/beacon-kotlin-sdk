package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.exception.UnknownException
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public fun Result.Companion.success(): Result<Unit> = success(Unit)

@InternalBeaconApi
public fun <T> Result.Companion.failure(): Result<T> = failure(UnknownException())

@InternalBeaconApi
public inline fun <T, S> Result<T>.flatMap(transform: (T) -> Result<S>): Result<S> =
    runCatchingFlat { transform(getOrThrow()) }

@InternalBeaconApi
public inline fun <T> Result<T>.mapException(transform: (Throwable) -> Throwable): Result<T> =
    fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(transform(it)) },
    )
