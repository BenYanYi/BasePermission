package com.benyanyi.permissionlib;

import android.app.Activity;
import android.app.FragmentManager;

/**
 * @author YanYi
 * @date 2018-06-06 10:40
 * @email ben@yanyi.red
 * @overview 权限获取帮助类
 */
public class PermissionHelper implements PermissionConfig {
    private static PermissionConfig instance;
    private static Activity mActivity;

    private String tag = "PermissionHelper";

    private final PermissionFragment permissionFragment;

    private PermissionHelper() {
        permissionFragment = getFragment();
    }

    public static PermissionConfig getInstance(Activity activity) {
        if (instance == null) {
            mActivity = activity;
            instance = new PermissionHelper();
        }
        if (mActivity == null) {
            mActivity = activity;
            instance = new PermissionHelper();
        }
        if (activity != mActivity) {
            mActivity = activity;
            instance = new PermissionHelper();
        }
        return instance;
    }

    private PermissionFragment getFragment() {
//        PermissionFragment fragment = (PermissionFragment) this.mActivity.getFragmentManager()
//                .findFragmentByTag(tag);
        PermissionFragment fragment = (PermissionFragment) mActivity.getFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = mActivity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, tag)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public PermissionConfig setPermissions(String... permissions) {
        this.permissionFragment.setPermissions(permissions);
        return this;
    }

    public PermissionConfig setPermissionDialogInfo(PermissionDialogInfo info) {
        this.permissionFragment.setInfo(info);
        return this;
    }

    public PermissionConfig setPermissionCallBack(PermissionCallBack callBack) {
        this.permissionFragment.setCallBack(callBack);
        return this;
    }

    public void hasPermission(int permissionCode) {
        this.permissionFragment.startForPermissionResult(permissionCode);
    }

    public void hasPermission(int permissionCode, PermissionDialogInfo info, String... permissions) {
        this.setPermissions(permissions);
        this.setPermissionDialogInfo(info);
        this.permissionFragment.startForPermissionResult(permissionCode);
    }

    public void hasPermission(int permissionCode, PermissionCallBack callBack) {
        this.setPermissionCallBack(callBack);
        this.permissionFragment.startForPermissionResult(permissionCode);
    }

    public void hasPermission(int permissionCode, PermissionCallBack callBack, String... permissions) {
        this.setPermissions(permissions);
        this.setPermissionCallBack(callBack);
        this.permissionFragment.startForPermissionResult(permissionCode);
    }

    public void hasPermission(int permissionCode, PermissionDialogInfo info, PermissionCallBack callBack, String... permissions) {
        this.setPermissionDialogInfo(info);
        this.setPermissions(permissions);
        this.setPermissionCallBack(callBack);
        this.permissionFragment.startForPermissionResult(permissionCode);
    }

    public static void destroy() {
        instance = null;
    }
}
