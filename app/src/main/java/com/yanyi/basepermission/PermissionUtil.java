package com.yanyi.basepermission;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import com.benyanyi.permissionlib.PermissionBind;
import com.benyanyi.permissionlib.annotation.GetPermissions;
import com.benyanyi.permissionlib.annotation.GetPermissionComplete;
import com.benyanyi.permissionlib.annotation.GetPermissionFailure;
import com.benyanyi.permissionlib.annotation.GetPermissionSuccess;
import com.benyanyi.permissionlib.msg.FailureMsg;

/**
 * @author YanYi
 * @date 2019/5/30 11:05
 * @email ben@yanyi.red
 * @overview
 */
@GetPermissions({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE})
public class PermissionUtil {

    public void getPermission(Activity activity) {
        PermissionBind.request(activity, this);
    }

    @GetPermissionSuccess({0, 1, 2})
    private void success(int permissionCode) {
        log(permissionCode);
    }

    @GetPermissionFailure
    private void failure(FailureMsg failureMsg) {
        log(failureMsg);
    }

    @GetPermissionComplete
    private void complete(int permissionCode) {
        log(permissionCode);
    }

    private void log(Object object) {
        Log.d(defaultTag(), object.toString());
    }

    private String defaultTag() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement log = stackTrace[1];
        String tag = null;
        for (int i = 1; i < stackTrace.length; i++) {
            StackTraceElement e = stackTrace[i];
            if (!e.getClassName().equals(log.getClassName())
                    && !"defaultTag".equals(e.getMethodName())
                    && !"log".equals(e.getMethodName())) {
                tag = e.getClassName() + "." + e.getMethodName();
                break;
            }
        }
        if (tag == null) {
            tag = log.getClassName() + "." + log.getMethodName();

        }
        return tag;
    }

}
