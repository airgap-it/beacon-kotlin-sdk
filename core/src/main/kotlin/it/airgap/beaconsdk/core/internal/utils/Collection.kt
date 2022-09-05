package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@InternalBeaconApi
public inline fun <T> List<T>.splitAt(
    firstInclusive: Boolean = false,
    calculateIndex: (List<T>) -> Int,
): Pair<List<T>, List<T>> {
    val index = calculateIndex(this)
    return splitAt(index, firstInclusive)
}

@InternalBeaconApi
public fun <T> List<T>.splitAt(
    index: Int,
    firstInclusive: Boolean = false,
): Pair<List<T>, List<T>> {
    val splitIndex = if (firstInclusive) minOf(index + 1, size) else index

    val first = slice(0 until splitIndex)
    val second = slice(splitIndex until size)

    return Pair(first, second)
}

@InternalBeaconApi
public fun <T> List<T>.tail(): List<T> {
    val n = if (size > 0) size - 1 else 0
    return takeLast(n)
}

@InternalBeaconApi
public fun <T> List<T>.shiftedBy(offset: Int): List<T> {
    val offset = offset % size
    return subList(offset, size) + subList(0, offset)
}

@InternalBeaconApi
public suspend fun <T> List<T>.launchForEach(block: suspend (T) -> Unit) {
    coroutineScope {
        forEach {
            launch { block(it) }
        }
    }
}

@InternalBeaconApi
public suspend fun <T, R> List<T>.asyncMap(block: suspend (T) -> R): List<R> =
    coroutineScope {
        map {
            async { block(it) }
        }
    }.awaitAll()

@InternalBeaconApi
public fun <K, V> MutableMap<K, V>.get(key: K, remove: Boolean = false): V? =
    if (remove) remove(key) else get(key)

@InternalBeaconApi
public fun <K, V> MutableMap<K, V>.getOrPutIfNotNull(key: K, defaultValue: () -> V?): V? =
    get(key) ?: defaultValue()?.also { put(key, it) }

@InternalBeaconApi
public fun <K, V> MutableMap<K, V>.getAndDispose(key: K): V? = get(key)?.also { remove(key) }
