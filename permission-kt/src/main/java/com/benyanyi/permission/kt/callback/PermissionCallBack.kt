package com.benyanyi.permission.kt.callback

import com.benyanyi.permission.kt.msg.FailureMsg

/**
 * @author YanYi
 * @date 2021/3/22 16:16
 * @email ben@yanyi.red
 * @overview
 */
interface PermissionCallBack {
    /**
     * 拥有所有权限
     */
    fun onPermissionSuccess()

    /**
     * 没有拥有所有权限
     */
    fun onPermissionFailure(permissions: Array<out String>)

    /**
     * 申请权限后执行该操作，不管有没有获取到所有权限
     */
    fun onPermissionComplete()
}