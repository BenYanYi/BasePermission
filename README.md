# BasePermission
权限工具类

## 使用方法

### 根目录下build.gradle添加Maven地址
~~~
repositories {
        maven {
            url "http://maven.benyanyi.com/nexus/content/repositories/mylove/"
        }
    }
~~~
### module 下添加

~~~
//java版本
implementation 'com.yanyi.benyanyi:PermissionHelper:1.1.3'
//kotlin版本
implementation 'com.yanyi.benyanyi:PermissionHelper-kt:1.1.3'
~~~

### 版本更新
* 2021-03-23更新(1.1.3) 添加kotlin版1.1.3版本
* 2021-03-12更新(1.1.3) 修复不同页面调用权限请求时，activity为空报错
* 2021-02-27更新(1.1.2) 添加销毁方法，防止请求未销毁造成的报错
* 2021-02-03更新(1.1.1) 将接收FragmentActivity更改为Activity
* 2020-05-18更新(1.1.0) 将android support转成androidx支持,并将Maven库存放到个人服务器上
* 2019-07-18更新(1.0.9) 优化拒绝权限通知弹窗，添加注解配置弹窗方法，优化注解方法，去除多余配置
* 2019-06-26更新(1.0.8) 删除重复方法
* 2019-06-12更新(1.0.7) 优化方法，规范方法，修改包名，以便所有开源库统一包名
* 2019-05-30更新(1.0.6) 优化注解调用方法
* 2019-05-24更新(1.0.5) 添加注解调用申请权限
* 2019-01-21更新(1.0.2) 修复自定义弹窗信息，弹窗设置不显示状态时没回调
* 2018-06-09更新(1.0.1) 新增弹窗配置信息修改(PermissionDialogInfo)
* 2018-06-08创建(1.0.0) 创建