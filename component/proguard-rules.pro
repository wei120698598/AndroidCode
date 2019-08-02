# Add project specific ProGuard rules here.

-optimizationpasses 5

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontwarn
#不要优化；将会会关闭优化，导致日志语句不会被优化掉。所以不能有这个配置
#-dontoptimize
-dontpreverify

-flattenpackagehierarchy
-allowaccessmodification
-printmapping map.txt
-keepattributes Exceptions,InnerClasses
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

#注解
-keepattributes *Annotation*
#避免混淆泛型
-keepattributes Singature

#许可证书
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# jni
-keepclasseswithmembernames class * {
    native <methods>;
}

# 自定义控件
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#activity
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}

#枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#R文件静态变量
-keepclassmembers class **.R$* {
    public static <fields>;
}

#序列化
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#    public <fields>;
#}

#webView
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keep class com.lianzi.component.widget.webview.CustomWebView$MyWebChromeClient {*;}


# 忽略警告
 -ignorewarnings

 #过滤日志
 #-assumenosideeffects class android.util.Log {
 #public static boolean isLoggable(java.lang.String, int);
 #public static int v(...);
 #public static int i(...);
 #public static int w(...);
 #public static int d(...);
 #public static int e(...);
 #}

 #（可选）避免Log打印输出
# -assumenosideeffects class android.util.Log {
#    public static *** v(...);
#    public static *** d(...);
#    public static *** i(...);
#    public static *** w(...);
#  }

# keep annotated by NotProguard
-keep @com.lianzi.component.annotation.NotProguard class * {*;}
-keep class * {
    @com.lianzi.component.annotation.NotProguard <fields>;
}
-keepclassmembers class * {
    @com.lianzi.component.annotation.NotProguard <methods>;
}
####################################################################自定义############################################################################################

-keepclasseswithmembers  public class * extends java.lang.RuntimeException{*;}
-keepclasseswithmembers  public class * implements java.io.Serializable{*;}
-keepclasseswithmembers  public class * implements com.lianzi.route.routeapi.PayRouteService{*;}

####################################################################第三方############################################################################################


#glide结合okhttp使用
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }


# 环信
 -keep class com.hyphenate.** {*;}
 -dontwarn  com.hyphenate.**

# 友盟分享
-dontshrink
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keep public class com.umeng.socialize.* {*;}

-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
 *;
}
-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.com.umeng.soexample.R$*{
public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
public static final int *;
    }
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}
-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keepattributes Signature

# RxJava RxAndroid 2.x
-dontwarn sun.misc.**
#-keep class io.reactivex.** { *;}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *;}
-keepattributes Signature
-keepattributes Exceptions


# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *;}


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

#greendao
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao { *; }
-keep class **$Properties

#GreenDaoUpgradeHelper
-keep class com.github.yuweiguocn.**{*;}

#fastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

#不混淆apache
-keep class org.apache.http.** { *; }
#腾讯x5
-keep class com.tencent.smtt.** { *; }
-keep class com.tencent.tbs.** { *; }

#crosswalk
-keep class org.xwalk.core.** { *;}
-keep class org.chromium.** { *;}
-keep class  junit.framework.**{*;}

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}


# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

 #Arouter
 -keep class com.alibaba.android.arouter.routes.**{*;}
 -keep class com.lianzi.component.arouter.**{*;}
 -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
 -keep class * extends com.alibaba.android.arouter.facade.template.IProvider{*;}
 -keep class * implements com.alibaba.android.arouter.facade.template.IProvider{*;}

 #基线包使用，生成mapping.txt
 -printmapping mapping.txt
 #生成的mapping.txt在app/buidl/outputs/mapping/release路径下，移动到/app路径下
 #修复后的项目使用，保证混淆结果一致
# -applymapping /mappings/mapping.txt
 #hotfix
 -keep class com.taobao.sophix.**{*;}
 -keep class com.ta.utdid2.device.**{*;}
 #防止inline
 -dontoptimize

# 阿里云oos
 -keep class com.alibaba.sdk.android.oss.** { *; }
 -dontwarn okio.**
 -dontwarn org.apache.commons.codec.binary.**

# 视频播放
 -keep class tv.danmaku.ijk.** { *; }
 -dontwarn tv.danmaku.ijk.**
 -keep class com.shuyu.gsyvideoplayer.** { *; }
 -dontwarn com.shuyu.gsyvideoplayer.**

 #PictureSelector 2.0
 -keep class com.luck.picture.lib.** { *; }

 -dontwarn com.yalantis.ucrop**
 -keep class com.yalantis.ucrop** { *; }
 -keep interface com.yalantis.ucrop** { *; }

# 视频处理
-keep class com.coremedia.iso.**{*;}
-keep class com.googlecode.mp4parser.**{*;}
-keep class org.aspectj.**{*;}
-keep class org.jdesktop.**{*;}
################################################################ IM ####################################################################

#mqtt & paho
-keep class org.eclipse.paho.client.mqttv3.** {*;}
-keep class org.eclipse.paho.client.mqttv3.*$* { *; }
-keep class org.eclipse.paho.client.mqttv3.logging.JSR47Logger {
    *;
}
#小米推送
#这里com.xiaomi.mipushdemo.DemoMessageRreceiver改成app中定义的完整类名
-keep class com.xiaomi.mipush.sdk.DemoMessageReceiver {*;}
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**

#友盟推送
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}

