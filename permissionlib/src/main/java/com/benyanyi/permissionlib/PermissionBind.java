package com.benyanyi.permissionlib;

import androidx.fragment.app.FragmentActivity;

import com.benyanyi.permissionlib.annotation.GetPermissionComplete;
import com.benyanyi.permissionlib.annotation.GetPermissionDialogInfo;
import com.benyanyi.permissionlib.annotation.GetPermissionFailure;
import com.benyanyi.permissionlib.annotation.GetPermissionSuccess;
import com.benyanyi.permissionlib.annotation.GetPermissions;
import com.benyanyi.permissionlib.msg.FailureMsg;

import java.lang.reflect.Method;

/**
 * @author YanYi
 * @date 2019/5/24 11:33
 * @email ben@yanyi.red
 * @overview
 */
public final class PermissionBind {
    private static PermissionBind instance;
    private PermissionConfig permissionConfig;
    private Object object;

    private PermissionBind(FragmentActivity activity, Object object) {
        this.object = object;
        this.permissionConfig = PermissionHelper.getInstance(activity);
        initPermission();
    }

    public static PermissionBind request(FragmentActivity activity, Object object) {
        if (instance == null) {
            instance = new PermissionBind(activity, object);
        }
        return instance;
    }

    /**
     * 需要申请的权限获取
     * 权限动态申请
     * 权限申请回调
     */
    private void initPermission() {
        Class<?> aClass = this.object.getClass();
        GetPermissions annotation = aClass.getAnnotation(GetPermissions.class);
        if (annotation != null) {
            String[] value = annotation.value();
            if (value.length > 0) {
                this.setPermissions(value);
                this.setPermissionDialogInfo(annotationDialogInfo());
                this.annotationCallBack();
                this.hasPermission();
            }
        }
    }

    /**
     * 设置权限dialog
     */
    private PermissionDialogInfo annotationDialogInfo() {
        PermissionDialogInfo dialogInfo = new PermissionDialogInfo();
        Method[] methods = this.object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            GetPermissionDialogInfo permissionDialogInfo = method.getAnnotation(GetPermissionDialogInfo.class);
            if (permissionDialogInfo != null && method.getReturnType().getSimpleName().equals("PermissionDialogInfo")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                String i1 = "PermissionDialogInfo";
                if (parameterTypes.length == 1) {
                    boolean boo1 = parameterTypes[0].getSimpleName().equals(i1);
                    try {
                        method.setAccessible(true);
                        if (boo1) {
                            Object invoke = method.invoke(this.object, dialogInfo);
                            if (invoke instanceof PermissionDialogInfo) {
                                return (PermissionDialogInfo) invoke;
                            } else {
                                dialogInfo.isShow = false;
                                return dialogInfo;
                            }
                        }
                    } catch (Exception e) {
                        dialogInfo.isShow = false;
                        return dialogInfo;
                    }
                }
            }
        }
        dialogInfo.isShow = false;
        return dialogInfo;
    }

    /**
     * 权限申请结果回调注解处理
     */
    private void annotationCallBack() {
        this.setPermissionCallBack(new PermissionCallBack() {
            @Override
            public void onPermissionSuccess(int permissionCode) {
                annotationSuccess();
            }

            @Override
            public void onPermissionFailure(FailureMsg failureMsg) {
                annotationFailure(failureMsg.getFailurePermissions());
            }

            @Override
            public void onPermissionComplete(int permissionCode) {
                annotationComplete();
            }
        });
    }

    /**
     * 权限获取成功处理注解
     */
    private void annotationSuccess() {
        Method[] methods = this.object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            GetPermissionSuccess permissionSuccess = method.getAnnotation(GetPermissionSuccess.class);
            if (permissionSuccess != null) {
                method.setAccessible(true);
                try {
                    method.invoke(this.object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 权限未全部获取成功注解处理
     */
    private void annotationFailure(String[] failurePermissions) {
        Method[] methods = this.object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            GetPermissionFailure permissionFailure = method.getAnnotation(GetPermissionFailure.class);
            if (permissionFailure != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                String str1 = "String[]";
                for (Class<?> aClass : parameterTypes) {
                    if (aClass.getSimpleName().equals(str1)) {
                        method.setAccessible(true);
                        try {
                            method.invoke(this.object, (Object) failurePermissions);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 权限请求完成后注解处理
     */
    private void annotationComplete() {
        Method[] methods = this.object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            GetPermissionComplete permissionComplete = method.getAnnotation(GetPermissionComplete.class);
            if (permissionComplete != null) {
                method.setAccessible(true);
                try {
                    method.invoke(this.object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setPermissions(String... permissions) {
        this.permissionConfig.setPermissions(permissions);
    }

    private void setPermissionDialogInfo(PermissionDialogInfo info) {
        this.permissionConfig.setPermissionDialogInfo(info);
    }

    private void setPermissionCallBack(PermissionCallBack callBack) {
        this.permissionConfig.setPermissionCallBack(callBack);
    }

    private void hasPermission() {
        this.permissionConfig.hasPermission(0x100);
    }

    public PermissionConfig getPermissionConfig() {
        return this.permissionConfig;
    }
}
