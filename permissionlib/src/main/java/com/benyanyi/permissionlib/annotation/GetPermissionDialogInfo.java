package com.benyanyi.permissionlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YanYi
 * @date 2019/5/30 12:55
 * @email ben@yanyi.red
 * @overview
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetPermissionDialogInfo {
}
