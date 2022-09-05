package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

private const val GLOBAL_TAG = "[Beacon SDK]"

@InternalBeaconApi
public fun logInfo(tag: String, message: String) {
    println("I/$GLOBAL_TAG $tag ${message.capitalized()}")
}

@InternalBeaconApi
public fun logDebug(tag: String, message: String) {
    println("D/$GLOBAL_TAG $tag ${message.capitalized()}")
}

@InternalBeaconApi
public fun logError(tag: String, error: Throwable) {
    println("E/$GLOBAL_TAG $tag ${error.message?.capitalized()}")
    error.printStackTrace()
}