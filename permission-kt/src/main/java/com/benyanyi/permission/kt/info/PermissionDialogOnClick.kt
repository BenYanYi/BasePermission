package com.benyanyi.permission.kt.info

import android.content.DialogInterface

/**
 * @author YanYi
 * @date 2019/5/24 14:20
 * @email ben@yanyi.red
 * @overview
 */
interface PermissionDialogOnClick {
    /**
     * 点击
     *
     * @param failurePermissions
     */
    fun onClick(dialog: DialogInterface, failurePermissions: Array<String>)
}