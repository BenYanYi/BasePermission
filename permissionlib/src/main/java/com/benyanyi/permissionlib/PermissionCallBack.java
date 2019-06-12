package com.benyanyi.permissionlib;

import com.benyanyi.permissionlib.msg.FailureMsg;

/**
 * @author YanYi
 * @date 2019/5/23 09:48
 * @email ben@yanyi.red
 * @overview
 */
public interface PermissionCallBack {
    /**
     * 拥有所有权限
     * @param permissionCode
     */
    void onPermissionSuccess(int permissionCode);

    /**
     * 没有拥有所有权限
     * @param failureMsg
     */
    void onPermissionFailure(FailureMsg failureMsg);

    /**
     * 申请权限后执行该操作，不管有没有获取到所有权限
     *
     * @param permissionCode
     */
    void onPermissionComplete(int permissionCode);
}
