package it.airgap.beaconsdk.core.internal.utils

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.*
import kotlin.reflect.KClass

@InternalBeaconApi
public fun <T : Any> Json.encodeToString(value: T, sourceClass: KClass<T>): String =
    encodeToString(serializerFor(sourceClass), value)

@InternalBeaconApi
@Suppress("UNCHECKED_CAST")
public fun <T : Any> Json.decodeFromString(string: String, targetClass: KClass<T>): T =
    decodeFromString(serializerFor(targetClass), string)

@InternalBeaconApi
public fun JsonObject.getString(key: String): String =
    get(key)?.jsonPrimitive?.content ?: failWithMissingField(key)

public fun JsonObject.getStringOrNull(key: String): String? =
    get(key)?.jsonPrimitive?.content

@InternalBeaconApi
public fun <T> JsonObject.getSerializable(key: String, jsonDecoder: JsonDecoder, deserializer: KSerializer<T>): T =
    get(key)?.let { jsonDecoder.json.decodeFromJsonElement(deserializer, it) } ?: failWithMissingField(key)

@InternalBeaconApi
public fun <T> JsonObject.getSerializableOrNull(key: String, jsonDecoder: JsonDecoder, deserializer: KSerializer<T>): T? =
    get(key)?.let {
        when (it) {
            is JsonNull -> null
            else -> jsonDecoder.json.decodeFromJsonElement(deserializer, it)
        }
    }