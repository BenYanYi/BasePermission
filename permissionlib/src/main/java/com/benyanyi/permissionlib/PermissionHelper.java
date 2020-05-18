package com.benyanyi.permissionlib;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * @author YanYi
 * @date 2018-06-06 10:40
 * @email ben@yanyi.red
 * @overview 权限获取帮助类
 */
public class PermissionHelper implements PermissionConfig {
    private static PermissionConfig instance;
    private FragmentActivity mActivity;

    private String tag = "PermissionHelper";

    private PermissionFragment permissionFragment;

    private PermissionHelper(FragmentActivity fragmentActivity) {
        this.mActivity = fragmentActivity;
        permissionFragment = getFragment();
    }

    public static PermissionConfig getInstance(FragmentActivity fragmentActivity) {
        if (instance == null) {
            instance = new PermissionHelper(fragmentActivity);
        }
        return instance;
    }

    private PermissionFragment getFragment() {
//        PermissionFragment fragment = (PermissionFragment) this.mActivity.getFragmentManager()
//                .findFragmentByTag(tag);
        PermissionFragment fragment = (PermissionFragment) this.mActivity.getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = this.mActivity.getSupportFragmentManager();
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
}
