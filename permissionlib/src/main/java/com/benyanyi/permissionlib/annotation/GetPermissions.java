package com.benyanyi.permissionlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YanYi
 * @date 2019/5/24 11:23
 * @email ben@yanyi.red
 * @overview 申明需要获取的权限，及获取权限时申明的permissionCode
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetPermissions {
    int permissionCode() default 0;

    String[] value() default {""};
}
