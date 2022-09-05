package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.internal.data.HexString
import java.math.BigInteger

private val hexRegex: Regex = Regex("^(${HexString.PREFIX})?([0-9a-fA-F]{2})+$")

@InternalBeaconApi
public fun String.isHex(): Boolean = this.matches(hexRegex)

@InternalBeaconApi
public fun String.asHexStringOrNull(): HexString? = if (isHex()) HexString(this) else null

@InternalBeaconApi
public fun String.asHexString(): HexString = asHexStringOrNull() ?: failWithInvalidHexString(this)

@InternalBeaconApi
public fun String.encodeToHexString(): HexString = encodeToByteArray().toHexString()

@InternalBeaconApi
public fun Byte.toHexString(): HexString =
    toUByte().toString(16).padStartEven('0').asHexString()

@InternalBeaconApi
public fun ByteArray.toHexString(): HexString =
    joinToString("") { it.toHexString().asString() }.asHexString()

@InternalBeaconApi
public fun ByteArray.toBigInteger(): BigInteger = BigInteger(this)

@InternalBeaconApi
public fun List<Byte>.toHexString(): HexString =
    joinToString("") { it.toHexString().asString() }.asHexString()

@InternalBeaconApi
public fun Int.toHexString(): HexString =
    toUInt().toString(16).padStartEven('0').asHexString()

public fun Int.toByteArray(trim: Boolean = true): ByteArray =
    ByteArray(4) { (toLong() shr (it * 8)).toByte() }
        .reversedArray()
        .let { if (trim) it.trimLeadingZeros() else it }

@InternalBeaconApi
public fun BigInteger.toHexString(): HexString =
    asHexStringOrNull() ?: failWithNegativeNumber(this)

@InternalBeaconApi
public fun BigInteger.asHexStringOrNull(): HexString? =
    if (this >= BigInteger.ZERO) toString(16).padStartEven('0').asHexString()
    else null

private fun failWithNegativeNumber(number: BigInteger): Nothing =
    throw IllegalArgumentException("cannot create HexString from a negative number $number")

private fun failWithInvalidHexString(string: String): Nothing =
    throw IllegalArgumentException("$string is not a valid hex string")