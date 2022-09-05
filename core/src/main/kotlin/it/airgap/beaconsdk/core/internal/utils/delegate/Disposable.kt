package it.airgap.beaconsdk.core.internal.utils.delegate

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@InternalBeaconApi
public class Disposable<V : Any> : ReadWriteProperty<Any?, V?> {
    private var value: V? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): V? =
        value?.also { value = null }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V?) {
        this.value = value
    }
}

@InternalBeaconApi
public fun <T: Any> disposable(): Disposable<T> = Disposable()