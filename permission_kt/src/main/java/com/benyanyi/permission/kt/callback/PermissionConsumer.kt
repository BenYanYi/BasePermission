package com.benyanyi.permission.kt.callback

/**
 * @author YanYi
 * @date 2021/3/22 16:14
 * @email ben@yanyi.red
 * @overview
 */
interface PermissionConsumer<T> {
    fun accept(t: T)
}