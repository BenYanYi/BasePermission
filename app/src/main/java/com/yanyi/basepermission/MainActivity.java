package com.yanyi.basepermission;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.benyanyi.permission.kt.PermissionHelper;
import com.benyanyi.permission.kt.callback.PermissionAction;

/**
 * @author myLove
 * @date 2018-06-06 11:30
 * @email ben@yanyi.red
 * @overview
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        PermissionHelper.Companion.getInstance(this)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .request()
                .onPermissionSuccess(new PermissionAction() {
                    @Override
                    public void accept() {

                    }
                });
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
