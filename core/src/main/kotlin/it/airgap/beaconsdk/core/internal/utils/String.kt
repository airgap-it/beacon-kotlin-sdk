package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public fun String.padStartEven(padChar: Char): String {
    val nextEven = if (length % 2 == 0) length else length + 1
    return padStart(nextEven, padChar)
}

@InternalBeaconApi
public fun String.capitalized(): String = replaceFirstChar(Char::titlecase)