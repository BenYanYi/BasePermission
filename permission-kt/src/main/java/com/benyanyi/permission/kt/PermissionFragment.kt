package com.benyanyi.permission.kt

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.benyanyi.permission.kt.callback.PermissionCallBack
import com.benyanyi.permission.kt.info.PermissionDialogInfo

/**
 * @author YanYi
 * @date 2021/3/22 14:43
 * @email ben@yanyi.red
 * @overview
 */
class PermissionFragment : Fragment() {

    private val code1 = 0x101
    private val code2 = 0x102
    private val requestCode = 0x103

    private var permissions: Array<out String>? = null
    private val oList = arrayListOf<String>()
    private var callBack: PermissionCallBack? = null

    private var info: PermissionDialogInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun setPermissions(permissions: Array<out String>) {
        this.permissions = permissions
    }

    fun setPermissionInfo(permissionInfo: PermissionDialogInfo) {
        this.info = permissionInfo
    }

    fun startForPermissionResult(request: PermissionRequest) {
        this.callBack = request.getCallBack()
        if (checkPermission()) {
            callBack?.onPermissionSuccess()
            callBack?.onPermissionComplete()
        } else {
            requestPermission(code1)
        }
    }

    /**
     * 判断权限是否拥有
     *
     * @return 权限是否获取（获取true）
     */
    private fun checkPermission(): Boolean {
        oList.clear()
        if (permissions != null && permissions!!.isNotEmpty()) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions?.forEach {
                if (ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED) {
                    if (!oList.contains(it)) {
                        oList.add(it)
                    }
                }
            }
        } else {
            permissions?.forEach {
                if (ActivityCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED) {
                    if (!oList.contains(it)) {
                        oList.add(it)
                    }
                }
            }
        }
        return oList.isEmpty()
    }

    private fun requestPermission(code: Int) {
        requestPermissions(permissions!!, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (this.requestCode == requestCode) {
            requestPermission(code2)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val list = hasAllPermissionsGranted(permissions, grantResults)
        val boo = list.isEmpty() && (code1 == requestCode || code2 == requestCode)
        if (boo) {
            callBack?.onPermissionSuccess()
            callBack?.onPermissionComplete()
        } else {
            this.oList.clear()
            list.forEach {
                oList.add(it)
            }
            if (code1 == requestCode) {
                val isShow = info?.isShow ?: false
                if (isShow) {
                    showDialog()
                } else {
                    callBack?.onPermissionFailure(oList.toTypedArray())
                    callBack?.onPermissionComplete()
                }
            } else if (code2 == requestCode) {
                callBack?.onPermissionFailure(oList.toTypedArray())
                callBack?.onPermissionComplete()
            }
        }
    }

    private fun hasAllPermissionsGranted(permissions: Array<out String>, grantResults: IntArray): List<String> {
        val list = arrayListOf<String>()
        if (grantResults.isEmpty()) {
            return list
        }
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                list.add(permissions[i])
            }
        }
        return list
    }

    /**
     * 权限请求拒绝后弹窗
     * Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
     * intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
     * getActivity().startActivityForResult(intent, requestCode);
     */
    private fun showDialog() {
        val builder = AlertDialog.Builder(activity)
        val title = info?.title ?: "权限不足"
        val message = info?.message ?: "需要必须的权限才能正常使用本应用"
        val positiveText = info?.positiveText ?: "重新获取权限"
        val negativeText = info?.negativeText ?: "退出"
        val isShow = info?.isShow ?: false
        val negativeClick: PermissionDialogInfo.NegativeClick? = info?.negativeClick
        val positiveClick: PermissionDialogInfo.PositiveClick? = info?.positiveClick
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(positiveText) { dialog, _ ->
            if (positiveClick != null) {
                positiveClick.onClick(dialog, oList.toTypedArray())
            } else {
                dialog.dismiss()
                requestPermission(code2)
            }
        }
        builder.setNegativeButton(negativeText) { dialog, _ ->
            if (negativeClick != null) {
                negativeClick.onClick(dialog, oList.toTypedArray())
            } else {
                dialog.dismiss()
                callBack?.onPermissionFailure(oList.toTypedArray())
                callBack?.onPermissionComplete()
            }
        }
        if (isShow) {
            builder.show()
        } else {
            callBack?.onPermissionFailure(oList.toTypedArray())
            callBack?.onPermissionComplete()
        }
    }
}