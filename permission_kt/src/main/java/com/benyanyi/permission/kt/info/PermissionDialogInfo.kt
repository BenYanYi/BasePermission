package com.benyanyi.permission.kt.info

import android.content.DialogInterface

/**
 * @author myLove
 * @date 2018-06-09 10:02
 * @email ben@yanyi.red
 * @overview
 */
class PermissionDialogInfo {
    var title: String? = null
    var message: String? = null
    var positiveText: String? = null
    var negativeText: String? = null
    var isShow = true
    var positiveClick: PositiveClick? = null
    var negativeClick: NegativeClick? = null

    interface PositiveClick : PermissionDialogOnClick {
        /**
         * 点击
         *
         * @param failurePermissions
         */
        override fun onClick(dialog: DialogInterface, failurePermissions: Array<String>)
    }

    interface NegativeClick : PermissionDialogOnClick {
        /**
         * 点击
         *
         * @param failurePermissions
         */
        override fun onClick(dialog: DialogInterface, failurePermissions: Array<String>)
    }
}