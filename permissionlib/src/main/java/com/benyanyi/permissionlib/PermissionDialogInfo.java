package com.benyanyi.permissionlib;

/**
 * @author myLove
 * @date 2018-06-09 10:02
 * @email ben@yanyi.red
 * @overview
 */
public final class PermissionDialogInfo {
    public String title;
    public String message;
    public String positiveText;
    public String negativeText;

    public boolean isShow = true;

    public PositiveClick positiveClick;
    public NegativeClick negativeClick;

    public interface PositiveClick extends PermissionDialogOnClick {
        /**
         * 点击
         *
         * @param failurePermissions
         */
        @Override
        void onClick(String[] failurePermissions);
    }

    public interface NegativeClick extends PermissionDialogOnClick {
        /**
         * 点击
         *
         * @param failurePermissions
         */
        @Override
        void onClick(String[] failurePermissions);
    }

}
