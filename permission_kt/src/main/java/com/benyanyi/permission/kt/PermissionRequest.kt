package com.benyanyi.permission.kt

import com.benyanyi.permission.kt.callback.PermissionAction
import com.benyanyi.permission.kt.callback.PermissionCallBack
import com.benyanyi.permission.kt.callback.PermissionConsumer

/**
 * @author YanYi
 * @date 2021/3/22 15:34
 * @email ben@yanyi.red
 * @overview
 */
class PermissionRequest {

    private val callBack by lazy {
        object : PermissionCallBack {
            override fun onPermissionSuccess() {
                successAction?.accept()
            }

            override fun onPermissionFailure(permissions: Array<out String>) {
                failureConsumer?.accept(permissions)
            }

            override fun onPermissionComplete() {
                completeAction?.accept()
            }
        }
    }
    private var successAction: PermissionAction? = null
    private var failureConsumer: PermissionConsumer<Array<out String>>? = null
    private var completeAction: PermissionAction? = null

    fun getCallBack(): PermissionCallBack {
        return callBack
    }

    fun onPermissionSuccess(action: PermissionAction): PermissionRequest {
        this.successAction = action
        return this
    }

    fun onPermissionFailure(consumer: PermissionConsumer<Array<out String>>): PermissionRequest {
        this.failureConsumer = consumer
        return this
    }

    fun onPermissionComplete(action: PermissionAction): PermissionRequest {
        this.completeAction = action
        return this
    }
}