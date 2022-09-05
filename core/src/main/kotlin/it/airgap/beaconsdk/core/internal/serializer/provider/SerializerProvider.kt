package it.airgap.beaconsdk.core.internal.serializer.provider

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlin.reflect.KClass

@InternalBeaconApi
public interface SerializerProvider {
    @Throws(Exception::class)
    public fun <T : Any> serialize(message: T, sourceClass: KClass<T>): String

    @Throws(Exception::class)
    public fun <T : Any> deserialize(message: String, targetClass: KClass<T>): T
}