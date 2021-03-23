package com.benyanyi.permission.kt

import androidx.appcompat.app.AppCompatActivity
import com.benyanyi.permission.kt.info.BenYanYiPermissionDialogInfo
import com.benyanyi.permission.kt.info.PermissionDialogInfo

/**
 * @author YanYi
 * @date 2021/3/22 14:38
 * @email ben@yanyi.red
 * @overview
 */
class PermissionHelper : PermissionConfig {


    companion object {

        private var instance: PermissionConfig? = null
        private lateinit var mActivity: AppCompatActivity

        fun getInstance(activity: AppCompatActivity): PermissionConfig {
            if (instance == null) {
                mActivity = activity
                instance = PermissionHelper()
            }
            if (activity != mActivity) {
                mActivity = activity
                instance = PermissionHelper()
            }
            return instance!!
        }
    }

    private val tag = "PermissionHelper"

    private val fragment: PermissionFragment by lazy {
        val findFragmentByTag = mActivity.supportFragmentManager.findFragmentByTag(tag)
        if (findFragmentByTag == null || findFragmentByTag !is PermissionFragment) {
            val permissionFragment = PermissionFragment()
            val supportFragmentManager = mActivity.supportFragmentManager
            supportFragmentManager.beginTransaction()
                    .add(permissionFragment, tag)
                    .commitAllowingStateLoss()
            supportFragmentManager.executePendingTransactions()
            permissionFragment
        } else {
            findFragmentByTag
        }
    }

    private val request by lazy {
        PermissionRequest()
    }

    override fun setPermissions(vararg permissions: String): PermissionConfig {
        fragment.setPermissions(permissions)
        return this
    }

    override fun setPermissionDialogInfo(info: PermissionDialogInfo): PermissionConfig {
        fragment.setPermissionInfo(info)
        return this
    }

    override fun setDefaultPermissionDialogInfo(): PermissionConfig {
        fragment.setPermissionInfo(BenYanYiPermissionDialogInfo.getInfo(mActivity))
        return this
    }


    override fun request(): PermissionRequest {
        fragment.startForPermissionResult(request)
        return request
    }
}