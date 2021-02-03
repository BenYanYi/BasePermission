package com.benyanyi.permissionlib;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.benyanyi.permissionlib.msg.FailureMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YanYi
 * @date 2019/5/23 11:02
 * @email ben@yanyi.red
 * @overview
 */
public final class PermissionFragment extends Fragment {

    private PermissionDialogInfo info;
    private int code1 = 0x101;
    private int code2 = 0x102;
    private int requestCode = 0x103;
    private int permissionCode;

    private List<String> oList = new ArrayList<>();

    private String[] permissions;
    private PermissionCallBack callBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    void setInfo(PermissionDialogInfo info) {
        this.info = info;
    }

    void setCallBack(PermissionCallBack callBack) {
        this.callBack = callBack;
    }

    void startForPermissionResult(int permissionCode) {
        this.permissionCode = permissionCode;
        if (checkPermission()) {
            if (this.callBack != null) {
                this.callBack.onPermissionSuccess(this.permissionCode);
                this.callBack.onPermissionComplete(this.permissionCode);
            }
        } else {
            requestPermission(code1);
        }
    }

    /**
     * 判断权限是否拥有
     *
     * @return 权限是否获取（获取true）
     */
    private boolean checkPermission() {
        oList.clear();
        if (permissions == null || permissions.length == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                    if (!oList.contains(permission)) {
                        oList.add(permission);
                    }
                }
            }
        } else {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                    if (!oList.contains(permission)) {
                        oList.add(permission);
                    }
                }
            }
        }
        return oList.isEmpty();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(int code) {
        requestPermissions(permissions, code);
    }

    /**
     * 权限请求拒绝后弹窗
     * Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
     * intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
     * getActivity().startActivityForResult(intent, requestCode);
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = info == null ? "权限不足" : (TextUtils.isEmpty(info.title) ? "权限不足" : info.title);
        String message = info == null ? "需要必须的权限才能正常使用本应用" :
                (TextUtils.isEmpty(info.message) ? "需要必须的权限才能正常使用本应用" : info.message);
        String positiveText = info == null ? "重新获取权限" : (TextUtils.isEmpty(info.positiveText) ? "重新获取权限" : info.positiveText);
        String negativeText = info == null ? "退出" : (TextUtils.isEmpty(info.negativeText) ? "退出" : info.negativeText);
        boolean isShow;
        if (info == null) {
            isShow = false;
        } else {
            isShow = info.isShow;
        }
        final PermissionDialogInfo.NegativeClick negativeClick = info == null ? null : info.negativeClick;
        final PermissionDialogInfo.PositiveClick positiveClick = info == null ? null : info.positiveClick;
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (positiveClick != null) {
                    positiveClick.onClick(dialog, PermissionFragment.this.oList.toArray(new String[0]));
                } else {
                    dialog.dismiss();
                    requestPermission(code2);
                }
            }
        });
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (negativeClick != null) {
                    negativeClick.onClick(dialog, PermissionFragment.this.oList.toArray(new String[0]));
                } else {
                    dialog.dismiss();
                    if (callBack != null) {
                        FailureMsg failureMsg = new FailureMsg();
                        failureMsg.setFailurePermissions(PermissionFragment.this.oList.toArray(new String[0]));
                        failureMsg.setPermissionCode(permissionCode);
                        callBack.onPermissionFailure(failureMsg);
                        callBack.onPermissionComplete(permissionCode);
                    }
                }
            }
        });
        if (isShow) {
            builder.show();
        } else {
            setFailure();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> list = hasAllPermissionsGranted(permissions, grantResults);
        boolean boo = list.isEmpty() && (this.code1 == requestCode || this.code2 == requestCode);
        if (boo) {
            if (this.callBack != null) {
                this.callBack.onPermissionSuccess(this.permissionCode);
                this.callBack.onPermissionComplete(this.permissionCode);
            }
        } else {
            oList = list;
            if (this.code1 == requestCode) {
                boolean isShow;
                if (info == null) {
                    isShow = false;
                } else {
                    isShow = info.isShow;
                }
                if (isShow) {
                    showDialog();
                } else {
                    setFailure();
                }
            } else if (this.code2 == requestCode) {
                setFailure();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode) {
            requestPermission(this.code2);
        }
    }

    private void setFailure() {
        if (this.callBack != null) {
            FailureMsg failureMsg = new FailureMsg();
            failureMsg.setPermissionCode(this.permissionCode);
            failureMsg.setFailurePermissions(this.oList.toArray(new String[0]));
            this.callBack.onPermissionFailure(failureMsg);
            this.callBack.onPermissionComplete(this.permissionCode);
        }
    }

    private List<String> hasAllPermissionsGranted(String[] permissions, int[] grantResults) {
        List<String> list = new ArrayList<>();
        if (grantResults.length <= 0) {
            return list;
        }
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                list.add(permissions[i]);
            }
        }
        return list;
    }
}
