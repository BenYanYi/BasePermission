package com.yanyi.basepermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.benyanyi.permissionlib.PermissionBind;
import com.benyanyi.permissionlib.PermissionDialogInfo;
import com.benyanyi.permissionlib.annotation.GetPermissionComplete;
import com.benyanyi.permissionlib.annotation.GetPermissionDialogInfo;
import com.benyanyi.permissionlib.annotation.GetPermissionFailure;
import com.benyanyi.permissionlib.annotation.GetPermissionSuccess;
import com.benyanyi.permissionlib.annotation.GetPermissions;

/**
 * @author myLove
 * @date 2018-06-06 11:30
 * @email ben@yanyi.red
 * @overview
 */
@GetPermissions({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE})
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        PermissionBind.request(this, this);
    }

    @GetPermissionSuccess
    private void success() {
        log("成功");
    }

    @GetPermissionFailure
    private void failure(String[] failureMsg) {
        StringBuilder s = new StringBuilder();
        for (String str : failureMsg) {
            s.append(str);
        }
        log(s.toString());
    }

    @GetPermissionComplete
    private void complete() {
        log("aaa");
    }

    @GetPermissionDialogInfo
    private PermissionDialogInfo setPermissionDialogInfo(PermissionDialogInfo dialogInfo) {
        dialogInfo.isShow = true;
        dialogInfo.title = "哈哈哈";
        return dialogInfo;
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

    private PermissionDialogInfo dialogInfo() {
        PermissionDialogInfo dialogInfo = new PermissionDialogInfo();
        dialogInfo.isShow = false;
        return dialogInfo;
    }
}
