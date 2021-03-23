package com.benyanyi.permission.kt

import com.benyanyi.permission.kt.info.PermissionDialogInfo

/**
 * @author YanYi
 * @date 2019/5/24 11:47
 * @email ben@yanyi.red
 * @overview
 */
interface PermissionConfig {
    /**
     * 添加权限
     *
     * @param permissions 权限
     * @return
     */
    fun setPermissions(vararg permissions: String): PermissionConfig

    /**
     * 设置权限弹窗
     */
    fun setPermissionDialogInfo(info: PermissionDialogInfo): PermissionConfig

    /**
     * 设置默认权限弹窗
     */
    fun setDefaultPermissionDialogInfo(): PermissionConfig

    fun request(): PermissionRequest

}