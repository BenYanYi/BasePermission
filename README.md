# BasePermission
权限工具类

## 使用方法

### 根目录下build.gradle添加Maven地址
~~~
repositories {
        maven {
            url "http://maven.benyanyi.com:8081/nexus/content/repositories/mylove/"
        }
    }
~~~
### module 下添加
~~~
implementation 'com.yanyi.benyanyi:PermissionHelper:1.1.0'
~~~

或者
~~~
<dependency>
  <groupId>com.yanyi.benyanyi</groupId>
  <artifactId>PermissionHelper</artifactId>
  <version>1.1.0</version>
  <type>aar</type>
</dependency>
~~~
具体使用查看[wiki](http://yanyis.space/android/basepermission/wikis)

### 版本更新
* 2020-05-18更新(1.1.0) 将android support转成androidx支持,并将Maven库存放到个人服务器上
* 2019-07-18更新(1.0.9) 优化拒绝权限通知弹窗，添加注解配置弹窗方法，优化注解方法，去除多余配置
* 2019-06-26更新(1.0.8) 删除重复方法
* 2019-06-12更新(1.0.7) 优化方法，规范方法，修改包名，以便所有开源库统一包名
* 2019-05-30更新(1.0.6) 优化注解调用方法
* 2019-05-24更新(1.0.5) 添加注解调用申请权限
* 2019-01-21更新(1.0.2) 修复自定义弹窗信息，弹窗设置不显示状态时没回调
* 2018-06-09更新(1.0.1) 新增弹窗配置信息修改(PermissionDialogInfo)
* 2018-06-08创建(1.0.0) 创建