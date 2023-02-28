# SheetCourse
移动端
还存在的bug：
  方案一：增添课程还是修改课程，只能有一门课程，若冲突，则不能添加课程；
  方案二：同一时间段，可以有不同课程，不过，需要实现页面自动渲染；


# 智能课程表开发学习记录
codesheep编程之路：[Road 2 Coding (r2coding.com)](https://www.r2coding.com/#/README)\
java后端学习路线：[(318条消息) Java后端学习路线总结_wangyue_msn_86的博客-CSDN博客_后端学习](https://blog.csdn.net/qq_40460454/article/details/122312645)\
[Java 后端自学之路 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/33716688)\
数据建模：[(318条消息) 数据建模(Data Modeling)是什么？_hustqb的博客-CSDN博客_data modeling](https://blog.csdn.net/hustqb/article/details/90488446)\
[详解数据建模的方法、模型、规范、流程、架构、分层和工具！ - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/482220646)
### 代码逻辑：
- LoginActivity：正则手机号密码校验；文本监听，满足条件则隐藏键盘；将本地账号密码通过retrofit网络请求库传至后台，若可以成功登录，retrofit回调函数接收后台传回结果数据。
    - 网络请求一般是在子线程，所以千万注意时间与同步异步问题。
- RegisterActivity：先校验账号密码，将本地账号密码传至服务端，服务端进行响应注册结果，验证码是后台sms传回本地验证码与本地输入的验证码作比较。
- 记住密码：勾选，则将账密存放本地room数据库；下次登陆时，从room数据库读取账密。
- 忘记密码：同注册逻辑一样，先看验证码，然后服务端更改密码返回结果。
- **接下来是多activity多fragment模式：参考simple项目，比较简单；设计封装模式，参考SimpleApp项目。**
- MainActivity：viewpaper+adapter（GetSupportFragmentAdapter）
- SheetFragment：课程表控件；
- excel导入：sheetfragment跳转外部存储，onactivityresult在父activity中调用（自身跳转自身，方能把excel转化的数据传递给oncreate阶段从而传递给sheetfragment解析课表页面的数据）；
- 游标点击跳转页面；将schedule列表传递给界面；
- 不论是课程详情页面还是课程编辑页面，都存在weekofterm问题，其中两大块是靠编程解决的：通过dialog内的weekofterm获取上课周数的字符串函数穿插生成weeklist；课程表周数根据excel中的字符串编程为weeklist；
- clickbuttonactivity增添课程：通过id值启动fragment，当增添课程完毕后，携带增添对象传递至mainactivity，这个时候遇到一个问题，mianactivity获取到的数据怎么传递给sheetfragment呢？有办法：通过间接类indirectclass获取一个activity的上下文，这样在另一个类（也就是项目中通过FragmentAdapter转fragment的FragmentFactory工厂类）中获得activity中的数据，从而传递给sheetfragment。
    
    




### 开源项目：
1. [Potato-DiGua/Timetable at server (github.com)](https://github.com/Potato-DiGua/Timetable/tree/server)
2. [liulongling/CourseTable: 高仿超级课程表周视图 (github.com)](https://github.com/liulongling/CourseTable)(带日程表时间)
3. https://[gitee](https://so.csdn.net/so/search?q=gitee&spm=1001.2101.3001.7020).com/yujun59/android-development-team.git（[(69条消息) 基于安卓开发的课程类APP_余＃的博客-CSDN博客](https://blog.csdn.net/weixin_44062783/article/details/114409631)）
4. 仿照他： [Android课程表的实现 - 贺墨于 - 博客园 (cnblogs.com)](https://www.cnblogs.com/hemou/p/12641871.html#android%E8%AF%BE%E7%A8%8B%E8%A1%A8%E7%9A%84%E5%AE%9E%E7%8E%B0)
5. [(89条消息) android 项目实战——打造超级课程表一键提取课表功能_sbsujjbcy的博客-CSDN博客](https://blog.csdn.net/sbsujjbcy/article/details/44004595)
6. 用Android内置的SQLite数据库储存课程数据：[(89条消息) Android课程表App完整版(附详细代码)_～有梦想的人的博客-CSDN博客_android移动开发课程表代码](https://blog.csdn.net/weixin_43268636/article/details/110098174)
7. [luojie1024/WeiFur: 超级课程表 课程格子 教务系统抓取 SQLite Android Studio HttpWatch Jsoup解析网页 (github.com)](https://github.com/luojie1024/WeiFur)
8. TimeTable：[github.com](https://github.com/Potato-DiGua/Timetable)
9. [(93条消息) 单Activity多Fragment模式快速构建一个App_shan_yao的博客-CSDN博客](https://blog.csdn.net/shan_yao/article/details/52152804)
10. [(93条消息) Android课程表控件v2.0.6用法大集合_lzhuangfei的博客-CSDN博客](https://blog.csdn.net/lzhuangfei/article/details/82500947)
11. [概述 (yuque.com)](https://www.yuque.com/zhuangfei/timetableview/summary)


app+java后端：[(69条消息) 【实验室培训】大学生的Java后端开发学习之路（从App开发讲起）_Dreamchaser追梦的博客-CSDN博客_后端开发培训](https://blog.csdn.net/qq_46101869/article/details/110286514)

### **移动端：**
1. [(74条消息) 16进制颜色码对照表_旧城以西^的博客-CSDN博客_16进制颜色](https://blog.csdn.net/TommyXu8023/article/details/89279180)
2. [Android官网  |  Android Gradle 插件版本说明  |  Android 开发者  |  Android Developers (google.cn)](https://developer.android.google.cn/studio/releases/gradle-plugin?hl=zh-cn)
3. [图标库](https://www.iconfont.cn/search/index)
4. [网络请求框架OkHttp3全解系列（一）：OkHttp的基本使用 - 腾讯云开发者社区-腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1667338)
5. [(84条消息) RxAndroid的使用_陈如水的博客-CSDN博客_android rx 怎么用](https://blog.csdn.net/chenrushui/article/details/71565837)
6. [Rxjava基本使用方法 - 掘金 (juejin.cn)](https://juejin.cn/post/6844903986294849549)
7. [(84条消息) RxJava Observer与Subscriber的关系_一叶飘舟的博客-CSDN博客](https://blog.csdn.net/jdsjlzx/article/details/51534504)
8. [(84条消息) Android 之 超详细 Broadcast_林一末的博客-CSDN博客_android broadcast](https://blog.csdn.net/weixin_39460667/article/details/82413819)
9. [(84条消息) Android中ProgressDialog的用法_淼森007的博客-CSDN博客_progressdialog](https://blog.csdn.net/weixin_38322371/article/details/115391865)
10. Flutter：[给 Android 开发者的 Flutter 指南 - Flutter 中文文档 - Flutter 中文开发者网站 - Flutter](https://flutter.cn/docs/get-started/flutter-for/android-devs#notifications)\
    [Flutter学习小计：Android原生项目引入Flutter - 简书 (jianshu.com)](https://www.jianshu.com/p/7b6522e3e8f1)
11. [单 Activity 多 Fragment 模式快速构建一个 App - 掘金 (juejin.cn)](https://juejin.cn/post/6844903441010016269)
12. [超级封装让你的项目搭建事半功倍 - 单 Activity 多 Fragment 模式 - 掘金 (juejin.cn)](https://juejin.cn/post/6844903441194549261)
13. [(91条消息) Android ActionBar完全解析，使用官方推荐的最佳导航栏(上)_guolin的博客-CSDN博客_android actionbar](https://blog.csdn.net/guolin_blog/article/details/18234477)
14. [Rxjava3文档级教程一： 介绍和基本使用 - 掘金 (juejin.cn)](https://juejin.cn/post/7020665574682263560)
    

### **后端：**
springboot网络内容：
- [ ] [(82条消息) Spring Boot程序 向其他API接口发送Http请求并接收返回结果_dying 搁浅的博客-CSDN博客_springboot 向其他api请求](https://blog.csdn.net/w903328615/article/details/82586614)
1. [(76条消息) SpringBoot——SpringBoot整合MyBatis(注解、XML配置)_white camel的博客-CSDN博客_springboot 整合 camel 官方文档 xml](https://blog.csdn.net/m0_37989980/article/details/105588234)


# 项目问题

### 课表堂移动端
1. [(71条消息) 解决Gradle报错Caused by: org.gradle.api.internal.plugins.PluginApplicationException: Failed to apply plu_甜心超人ww的博客-CSDN博客](https://blog.csdn.net/weixin_43789311/article/details/112902471)
2. [(73条消息) 彻底解决Android Studio Minimum supported Gradle version is X.Y.Z. Current version is x.y.z.问题_microhex的博客-CSDN博客_minimum supported gradle version is 7.4.](https://blog.csdn.net/u013762572/article/details/124542155)
3. <https://developer.android.google.cn/studio/releases/gradle-plugin.html>
4. [(73条消息) 【Android Studio】Gradle版本 Gradle 插件 版本 Java版本 Androd studio版本统一问题_m0_54352040的博客-CSDN博客](https://blog.csdn.net/m0_54352040/article/details/124905929)\
5. [(73条消息) AS升级3.1 编译报错：The SourceSet 'instrumentTest' is not recognized by the Android Gradle Plugin._winterXin的博客-CSDN博客](https://blog.csdn.net/weixin_36677647/article/details/79807105)
6. [(74条消息) 解决Gradle报错Caused by: org.gradle.api.internal.plugins.PluginApplicationException: Failed to apply plu_甜心超人ww的博客-CSDN博客](https://blog.csdn.net/weixin_43789311/article/details/112902471)
7.  2月1日出现的问题（关于retrofit[Retrofit2 实战（一、使用详解篇） - 掘金 (juejin.cn)](https://juejin.cn/post/6978777076073660429)，retrofit原理：[(83条消息) 从源码处理一理Retrofit的异步网络请求如何把结果切换到主线程_跑步_跑步的博客-CSDN博客_retrofit 线程](https://blog.csdn.net/anhenzhufeng/article/details/86677016)。从晚上6点困扰凌晨3点，2月2日又搞了4个小时）：***问题1：*** 掌握retrofit原理和用法之后，经过不断地跳坑，不知道用了多少小时，排查出第一个问题：本地电脑的springboot服务端tomcat服务器和Android studio中的模拟器不能localhost作为baseurl，需用电脑的ip地址。另外baseurl必须以http开头配置，同时，配置免SSL证书。[(79条消息) 解决使用retrofit在Android Studio模拟器上无法访问本地tomcat服务器的问题_湟同学你好的博客-CSDN博客](https://blog.csdn.net/weixin_42683077/article/details/94965046) &ensp;&ensp;&ensp;&ensp; ***问题2：*** 针对服务端的响应体，retrofit必须设置数据解析器Gson或Jackson。&ensp;&ensp;&ensp;&ensp;  ***问题3：*** retrofit同步与异步请求问题，因为我是在activity子线程中调用另一个类中的retrofit异步请求处理结果，至少我activity子线程要拿处理数据的时候，另一个类的异步请求处理结果还没有结构，致使我拿到的数据是空值，使程序异常。 百般权衡下（左右都是在子线程中执行的，不会造成NetworkOnMainThreadException 的异常错误），唯一的方法就是将异步请求改为同步请求。[(79条消息) Retrofit2.0-同步和异步请求_xdy1120的博客-CSDN博客_retrofit2 同步](https://blog.csdn.net/xdy1120/article/details/90127522)
8. 2月2日出现的问题（困扰4小时）：将sqlsite数据库转为room的时候，出现的***问题1：*** room依赖与targetSdk有关，我是将room依赖版本降低之后才解决的，还有java型的注解处理器，只需使用java自己的，kotlin还有其他room依赖可选择；   ***问题2：*** room依赖没有问题，room各个层次的代码仔细检查也没有问题，但是在activity中创建roomdatabase的时候，总会闪退。纠结了一个小时多，认认真真将下篇文章阅读之后，才解决问题（Room的操作都需要在子线程执行，如果需要在主线程执行需要设置allowMainThreadQueries()方法）。  我的登录逻辑代码本来就放在子线程里面，把room的创建与DAO操作放在主线程里面肯定会造成系统崩溃，app闪退。[(79条消息) Room数据库使用与踩坑（最新）_I'm an Android Dev的博客-CSDN博客_room数据库](https://blog.csdn.net/wumeixinjiazu/article/details/123382721)
9. 2月4、5日出现的问题：验证码（权限申请和登录逻辑）、retrofit @body注解，后端的数据为空，客户端是没有问题的。于是研究了线程之间的通信机制和网络参数交互原理。[Android消息机制1-Handler(Java层) - Gityuan博客 | 袁辉辉的技术博客](http://gityuan.com/2015/12/26/handler-message-framework/) &ensp;&ensp;&ensp;&ensp;[(83条消息) android Retrofit框架使用@body上传数据，服务端接收数据为空的解决办法_风-样的男人的博客-CSDN博客](https://blog.csdn.net/lhm386036578/article/details/77650901)
10. 2.23/2.24空指针异常问题：
    1. fragment中的getActivity()返回为空，原因是fragment会生成savedInstance实例，而activity销毁的完毕后，该fragment和activity并没有detach，因此等activity再一次激活会根据savedInstance恢复fragment，而依据代码逻辑会生成新的fragment。此时，若旧的（已恢复）fragment获取旧的getActivity()就会报空指针异常的错误；解决方法：一是复写onsavedInstance方法。让activity与fragment一起消失；二是把savedInstanceState中的bundle中的数据清空；三是在基类fragment中声明全局的activity。详解：[(91条消息) Android-Fragment 中使用 getActivity()为null的原因---剖析源码_哑巴湖小水怪的博客-CSDN博客_fragment getactivity为空](https://blog.csdn.net/changhuzichangchang/article/details/107255134)
    2. 发现这个问题走了很多弯路，以为是getactivity出了问题：关于`new Intent(SYApplication.getContext(), ClickButtonActivity.class);`这行代码，SYAapplication.getContext()空指针异常（SYAappliction是继承自application类），原因是我粗心大意没有把AndroidMainFest中的application name指定，导致intent运行的时候找不到SYAapplication.getContext()。详解：[(91条消息) Android Studio中AndroidManifest.xml文件中application标签_棉猴的博客-CSDN博客](https://blog.csdn.net/hou09tian/article/details/82991410)
    3. ButterKnife(`Unbinder unbinder;unbinder.unbinder.unbind();`)。在这里不解绑也可以。
    4. 普通的空指针异常：我的代码应用到了发布订阅者模式rx，其中有一个subscribles数组，现在里面还没有观察者，也就是说这个数组为空，在activity销毁的时候有关于subscribes数组的操作代码，结果就是结束activity的时候，空指针异常。解决方式就是很普遍的编程判空方式，若不为空，再执行相关代码。详解：[(91条消息) Android进阶学习RxJava(一)观察者与被观察者_Leogyy的博客-CSDN博客](https://blog.csdn.net/BurtHao/article/details/108996464)
11. [Gradle7.3配置Android-pickerView - 星锋 - 博客园 (cnblogs.com)](https://www.cnblogs.com/zsdblog/p/16542288.html)


### 课表堂后端
1. springboot整合mybatis时使用的是druid连接池，druid配置是在application.yml文件中配置的，配置格式一定要正确，否则出现```Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.```这样的报错，网上的解释都是数据源没有配置对，虽是相同错误，但与自己的毛病根本不对应。&ensp;&ensp;辛苦折腾了几个小时发现是application.yml配置格式不对（与mybatis整合hikariCP相比）。 &ensp;&ensp;**总结：** 在这个报错解决过程中，发现了hikariCP与Druid的区别。[数据库连接池选型 Druid vs HikariCP - 掘金 (juejin.cn)](https://juejin.cn/post/6885974851949953031)


### java知识补充
1. [(90条消息) java中Comparable讲解_bug_Cat的博客-CSDN博客_comparable java](https://blog.csdn.net/qq_41474648/article/details/105182845)


