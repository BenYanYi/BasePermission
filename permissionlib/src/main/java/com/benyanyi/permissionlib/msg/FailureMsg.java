package com.benyanyi.permissionlib.msg;

import java.util.Arrays;

/**
 * @author YanYi
 * @date 2019/5/29 16:56
 * @email ben@yanyi.red
 * @overview
 */
public class FailureMsg {

    private int permissionCode;

    private String[] failurePermissions;

    public int getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(int permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String[] getFailurePermissions() {
        return failurePermissions;
    }

    public void setFailurePermissions(String[] failurePermissions) {
        this.failurePermissions = failurePermissions;
    }

    @Override
    public String toString() {
        return "FailureMsg{" +
                "permissionCode=" + permissionCode +
                ", failurePermissions=" + Arrays.toString(failurePermissions) +
                '}';
    }
}
