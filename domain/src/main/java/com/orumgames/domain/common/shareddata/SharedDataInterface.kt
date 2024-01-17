package com.orumgames.domain.common.shareddata

interface SharedDataInterface {
    operator fun <T : Any> get(key: String): T?
    operator fun <T : Any> set(key: String, any: T?)
}