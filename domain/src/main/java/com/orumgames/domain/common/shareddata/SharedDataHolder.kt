package com.orumgames.domain.common.shareddata

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface SharedDataHolder {
    val sharedDataInterface: SharedDataInterface
}

inline fun <reified T : Any> sharedData(
    crossinline keyGenerator: () -> String = { T::class.java.canonicalName ?: T::class.java.name }
) = object : ReadWriteProperty<SharedDataHolder, T?> {

    override fun getValue(
        thisRef: SharedDataHolder, property: KProperty<*>
    ): T? = thisRef.sharedDataInterface[keyGenerator()]

    override fun setValue(thisRef: SharedDataHolder, property: KProperty<*>, value: T?) {
        thisRef.sharedDataInterface[keyGenerator()] = value
    }
}

inline fun <reified T : Any> SharedDataInterface.get(): T? = get(T::class.java.canonicalName ?: T::class.java.name)

inline fun <reified T: Any> SharedDataInterface.set(value: T?) {
    set(T::class.java.canonicalName ?: T::class.java.name, value)
}