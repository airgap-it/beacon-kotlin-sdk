package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.data.Connection
import it.airgap.beaconsdk.core.exception.BlockchainNotFoundException
import it.airgap.beaconsdk.core.exception.InternalException
import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.message.BeaconMessage
import it.airgap.beaconsdk.core.scope.BeaconScope
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KClass

@InternalBeaconApi
public fun failWith(message: String? = null, cause: Throwable? = null): Nothing =
    if (message == null && cause != null) throw cause
    else throw InternalException(message, cause)

@InternalBeaconApi
public fun failWithUninitialized(name: String): Nothing =
    throw IllegalStateException("$name uninitialized")

@InternalBeaconApi
public fun failWithUninitialized(beaconScope: BeaconScope): Nothing {
    val name = when (beaconScope) {
        is BeaconScope.Global -> "global"
        is BeaconScope.Instance -> beaconScope.id
    }

    failWithUninitialized("Scope $name")
}

@InternalBeaconApi
public fun failWithIllegalState(message: String? = null): Nothing =
    throw IllegalStateException(message)

@InternalBeaconApi
public fun failWithIllegalArgument(message: String? = null): Nothing =
    throw IllegalArgumentException(message)

@InternalBeaconApi
public fun failWithBlockchainNotFound(identifier: String): Nothing =
    throw BlockchainNotFoundException(identifier)

public fun failWithTransportNotSupported(type: Connection.Type? = null): Nothing =
    failWithIllegalState("Transport ($type) is not supported.")

@InternalBeaconApi
public fun failWithActiveAccountNotSet(): Nothing =
    failWithIllegalState("Active Account has not been set.")

@InternalBeaconApi
public fun failWithAccountNetworkNotFound(accountId: String): Nothing =
    failWithIllegalState("Account ($accountId) network not found.")

@InternalBeaconApi
public fun failWithExpectedJsonDecoder(actual: KClass<out Decoder>): Nothing =
    throw SerializationException("Expected Json decoder, got $actual")

@InternalBeaconApi
public fun failWithExpectedJsonEncoder(actual: KClass<out Encoder>): Nothing =
    throw SerializationException("Expected Json encoder, got $actual")

@InternalBeaconApi
public fun failWithUnexpectedJsonType(type: KClass<out JsonElement>): Nothing =
    throw SerializationException("Could not deserialize, unexpected JSON type $type")

@InternalBeaconApi
public fun failWithMissingField(name: String): Nothing =
    throw SerializationException("Could not deserialize, `$name` field is missing")

@InternalBeaconApi
public fun failWithUnsupportedMessage(message: BeaconMessage, version: String): Nothing =
    throw IllegalArgumentException("Message $message is not supported in version $version")

public fun failWithUnsupportedMessageVersion(version: String, blockchainIdentifier: String): Nothing =
    throw IllegalArgumentException("Message version $version is not supported for $blockchainIdentifier")
