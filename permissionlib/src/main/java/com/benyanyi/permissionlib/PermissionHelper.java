package com.benyanyi.permissionlib;

import android.app.Activity;
import android.app.FragmentManager;

/**
 * @author myLove
 * @date 2018-06-06 10:40
 * @email ben@yanyi.red
 * @overview
 */
public class PermissionHelper {
    private static PermissionHelper instance;
    private Activity mActivity;

    private String tag = "PermissionHelper";

    private PermissionFragment permissionFragment;

    private PermissionHelper(Activity activity) {
        this.mActivity = activity;
        permissionFragment = getFragment();
    }

    public static PermissionHelper getInstance(Activity activity) {
//        if (instance == null) {
        instance = new PermissionHelper(activity);
//        }
        return instance;
    }

    private PermissionFragment getFragment() {
        PermissionFragment fragment = (PermissionFragment) this.mActivity.getFragmentManager()
                .findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = this.mActivity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, tag)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public PermissionHelper setPermissions(String... permissions) {
        this.permissionFragment.setPermissions(permissions);
        return this;
    }

    public PermissionHelper setPermissionDialogInfo(PermissionDialogInfo info) {
        this.permissionFragment.setInfo(info);
        return this;
    }

    public void setPermissionCallBack(PermissionCallBack callBack) {
        this.permissionFragment.setCallBack(callBack);
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
