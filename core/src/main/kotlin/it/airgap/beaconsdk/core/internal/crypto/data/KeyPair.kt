package it.airgap.beaconsdk.core.internal.crypto.data

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public class KeyPair(public val privateKey: ByteArray, public val publicKey: ByteArray)