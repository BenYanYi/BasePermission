package com.benyanyi.permission.kt.info

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import java.util.*

/**
 * @author YanYi
 * @date 2021/3/23 10:05
 * @email ben@yanyi.red
 * @overview
 */
object BenYanYiPermissionDialogInfo {
    fun getInfo(activity: Activity): PermissionDialogInfo {
        val sdk = Build.VERSION.SDK_INT// SDK号
        val model = Build.MODEL// 手机型号
        val release = Build.VERSION.RELEASE // android系统版本号
        val brand = Build.BRAND//手机厂商
        val permissionInfo = PermissionDialogInfo()
        permissionInfo.isShow = true
        permissionInfo.message = "权限不足，功能将无法正常使用"
        permissionInfo.title = "权限不足"
        permissionInfo.negativeText = "取消"
        permissionInfo.negativeClick = object : PermissionDialogInfo.NegativeClick {
            override fun onClick(dialog: DialogInterface, failurePermissions: Array<String>) {
                dialog.dismiss()
            }
        }
        permissionInfo.positiveText = "设置权限"
        permissionInfo.positiveClick = object : PermissionDialogInfo.PositiveClick {
            override fun onClick(dialog: DialogInterface, failurePermissions: Array<String>) {
                if (brand.toLowerCase(Locale.ROOT) == "redmi" || brand.toLowerCase(Locale.ROOT) == "xiaomi") {
                    gotoXMPermission(activity)
                } else if (brand.toLowerCase(Locale.ROOT) == "meizu") {
                    gotoMZPermission(activity)
                } else if (brand.toLowerCase(Locale.ROOT) == "huawei" || brand.toLowerCase(Locale.ROOT) == "honor") {
                    gotoHWPermission(activity)
                } else {
                    activity.startActivity(getAppDetailSettingIntent(activity))
                }
                dialog.dismiss()
            }
        }
        return permissionInfo
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private fun gotoXMPermission(activity: Activity) {
        try {
            val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
            localIntent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity"
            )
            localIntent.putExtra("extra_pkgname", activity.applicationContext.packageName)
            activity.startActivity(localIntent)
        } catch (e: Exception) {
            try {
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
                )
                localIntent.putExtra("extra_pkgname", activity.applicationContext.packageName)
                activity.startActivity(localIntent)
            } catch (e: Exception) {
                e.fillInStackTrace()
                activity.startActivity(getAppDetailSettingIntent(activity))
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private fun gotoMZPermission(activity: Activity) {
        try {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("packageName", activity.applicationContext.packageName)
            activity.startActivity(intent)
        } catch (e: Exception) {
            e.fillInStackTrace()
            activity.startActivity(getAppDetailSettingIntent(activity))
        }
    }

    /**
     * 华为的权限管理页面
     */
    private fun gotoHWPermission(activity: Activity) {
        try {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val comp = ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity"
            )//华为权限管理
            intent.component = comp
            activity.startActivity(intent)
        } catch (e: Exception) {
            e.fillInStackTrace()
            activity.startActivity(getAppDetailSettingIntent(activity))
        }
    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     */
    private fun getAppDetailSettingIntent(activity: Activity): Intent {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", getPackageName(activity), null)
        } else {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName(
                    "com.android.settings",
                    "com.android.settings.InstalledAppDetails"
            )
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName(activity))
        }
        return localIntent
    }

    /**
     * 获取包名
     *
     * @return
     */
    fun getPackageName(activity: Activity): String {
        return activity.packageName ?: ""
    }
}