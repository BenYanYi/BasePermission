package com.benyanyi.permissionlib;

import android.content.DialogInterface;

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
    void onClick(DialogInterface dialog, String[] failurePermissions);
}
