package com.benyanyi.permissionlib;

import android.app.Activity;

import com.benyanyi.permissionlib.annotation.GetPermissionComplete;
import com.benyanyi.permissionlib.annotation.GetPermissionFailure;
import com.benyanyi.permissionlib.annotation.GetPermissions;
import com.benyanyi.permissionlib.annotation.GetPermissionSuccess;
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
    private PermissionHelper permissionHelper;
    private Object object;

    private PermissionBind(Activity activity, Object object) {
        this.object = object;
        this.permissionHelper = PermissionHelper.getInstance(activity);
        initPermission();
    }

    public static PermissionBind request(Activity activity, Object object) {
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
                PermissionDialogInfo info = new PermissionDialogInfo();
                info.isShow = false;
                this.setPermissionDialogInfo(info);
                this.annotationCallBack();
                this.hasPermission(annotation.permissionCode());
            }
        }
    }

    /**
     * 权限申请结果回调注解处理
     */
    private void annotationCallBack() {
        this.setPermissionCallBack(new PermissionCallBack() {
            @Override
            public void onPermissionSuccess(int permissionCode) {
                annotationSuccess(permissionCode);
            }

            @Override
            public void onPermissionFailure(FailureMsg failureMsg) {
                annotationFailure(failureMsg);
            }

            @Override
            public void onPermissionComplete(int permissionCode) {
                annotationComplete(permissionCode);
            }
        });
    }

    /**
     * 权限获取成功处理注解
     *
     * @param permissionCode
     */
    private void annotationSuccess(int permissionCode) {
        Method[] methods = this.object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            GetPermissionSuccess permissionSuccess = method.getAnnotation(GetPermissionSuccess.class);
            if (permissionSuccess != null) {
                int[] values = permissionSuccess.value();
                if (values.length > 0) {
                    for (int value : values) {
                        if (value == permissionCode) {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            String i1 = "Integer";
                            String i2 = "int";
                            boolean boo1 = parameterTypes != null && parameterTypes.length == 1
                                    && (parameterTypes[0].getSimpleName().equals(i1)
                                    || parameterTypes[0].getSimpleName().equals(i2));
                            if (boo1) {
                                method.setAccessible(true);
                                try {
                                    method.invoke(this.object, permissionCode);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 权限未全部获取成功注解处理
     *
     * @param failureMsg
     */
    private void annotationFailure(FailureMsg failureMsg) {
        Method[] methods = this.object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            GetPermissionFailure permissionFailure = method.getAnnotation(GetPermissionFailure.class);
            if (permissionFailure != null) {
                int[] values = permissionFailure.value();
                if (values.length > 0) {
                    for (int value : values) {
                        if (value == failureMsg.getPermissionCode()) {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            String str1 = "FailureMsg";
                            if (parameterTypes != null && parameterTypes.length == 1
                                    && parameterTypes[0].getSimpleName().equals(str1)) {
                                method.setAccessible(true);
                                try {
                                    method.invoke(this.object, failureMsg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * 权限请求完成后注解处理
     *
     * @param permissionCode
     */
    private void annotationComplete(int permissionCode) {
        Method[] methods = this.object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            GetPermissionComplete permissionComplete = method.getAnnotation(GetPermissionComplete.class);
            if (permissionComplete != null) {
                int[] values = permissionComplete.value();
                if (values.length > 0) {
                    for (int value : values) {
                        if (value == permissionCode) {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            String i1 = "Integer";
                            String i2 = "int";
                            boolean boo = parameterTypes != null && parameterTypes.length == 1
                                    && (parameterTypes[0].getSimpleName().equals(i1)
                                    || parameterTypes[0].getSimpleName().equals(i2));
                            if (boo) {
                                method.setAccessible(true);
                                try {
                                    method.invoke(this.object, permissionCode);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private void setPermissions(String... permissions) {
        this.permissionHelper.setPermissions(permissions);
    }

    private void setPermissionDialogInfo(PermissionDialogInfo info) {
        this.permissionHelper.setPermissionDialogInfo(info);
    }

    private void setPermissionCallBack(PermissionCallBack callBack) {
        this.permissionHelper.setPermissionCallBack(callBack);
    }

    private void hasPermission(int permissionCode) {
        this.permissionHelper.hasPermission(permissionCode);
    }

    public PermissionHelper getPermissionHelper() {
        return this.permissionHelper;
    }
}
