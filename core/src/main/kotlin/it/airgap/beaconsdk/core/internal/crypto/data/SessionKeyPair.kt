package it.airgap.beaconsdk.core.internal.crypto.data

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public class SessionKeyPair(public val rx: ByteArray, public val tx: ByteArray)