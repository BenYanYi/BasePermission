package com.benyanyi.permissionlib;

/**
 * @author YanYi
 * @date 2019/5/24 11:47
 * @email ben@yanyi.red
 * @overview
 */
public interface PermissionConfig {
    /**
     * 添加权限
     *
     * @param permissions 权限
     * @return
     */
    PermissionConfig setPermissions(String... permissions);

    /**
     * dialog配置
     *
     * @param info 弹窗信息
     * @return 当前
     */
    PermissionConfig setDialogInfo(PermissionDialogInfo info);

    /**
     * 判断权限是否需要请求
     */
    void hasPermission();

    /**
     * 判断权限是否需要请求
     *
     * @param callBack 回调
     */
    void hasPermission(PermissionCallBack callBack);

    /**
     * 判断权限是否需要请求
     *
     * @param callBack    回调
     * @param permissions 权限
     */
    void hasPermission(PermissionCallBack callBack, String... permissions);

    /**
     * 判断权限是否需要请求
     *
     * @param info        弹窗配置
     * @param callBack    回调
     * @param permissions 权限
     */
    void hasPermission(PermissionDialogInfo info, PermissionCallBack callBack, String... permissions);

}
