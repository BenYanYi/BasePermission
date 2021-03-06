package com.yanyi.basepermission;

/**
 * @author YanYi
 * @date 2019/5/30 11:05
 * @email ben@yanyi.red
 * @overview
 */
//@GetPermissions({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE})
//public class PermissionUtil {
//
//    public void getPermission(AppCompatActivity activity) {
//        PermissionBind.request(activity, this);
//    }
//
//    @GetPermissionSuccess()
//    private void success(int permissionCode) {
//        log(permissionCode);
//    }
//
//    @GetPermissionFailure
//    private void failure(FailureMsg failureMsg) {
//        log(failureMsg);
//    }
//
//    @GetPermissionComplete
//    private void complete(int permissionCode) {
//        log(permissionCode);
//    }
//
//    private void log(Object object) {
//        Log.d(defaultTag(), object.toString());
//    }
//
//    private String defaultTag() {
//        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        StackTraceElement log = stackTrace[1];
//        String tag = null;
//        for (int i = 1; i < stackTrace.length; i++) {
//            StackTraceElement e = stackTrace[i];
//            if (!e.getClassName().equals(log.getClassName())
//                    && !"defaultTag".equals(e.getMethodName())
//                    && !"log".equals(e.getMethodName())) {
//                tag = e.getClassName() + "." + e.getMethodName();
//                break;
//            }
//        }
//        if (tag == null) {
//            tag = log.getClassName() + "." + log.getMethodName();
//
//        }
//        return tag;
//    }
//
//}
