package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@InternalBeaconApi
public fun <T> Flow<Result<T>>.onEachSuccess(action: suspend (T) -> Unit): Flow<Result<T>> =
    onEach { result -> result.onSuccess { action(it) } }

@InternalBeaconApi
public fun <T> Flow<Result<T>>.onEachFailure(action: suspend (Throwable) -> Unit): Flow<Result<T>> =
    onEach { result -> result.onFailure { action(it) } }