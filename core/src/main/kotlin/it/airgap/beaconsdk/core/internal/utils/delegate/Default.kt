package it.airgap.beaconsdk.core.internal.utils.delegate

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@InternalBeaconApi
public class Default<V : Any>(initValue: V? = null, private val defaultValue: () -> V) : ReadWriteProperty<Any?, V> {
    private var value: V? = initValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): V = value ?: defaultValue()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        this.value = value
    }
}

@InternalBeaconApi
public fun <V : Any> default(initValue: V? = null, defaultValue: () -> V): Default<V> = Default(initValue, defaultValue)