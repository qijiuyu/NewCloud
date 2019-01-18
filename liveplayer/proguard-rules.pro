# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#微吼直播混淆
-dontwarn com.vhall.** -keep class com.vhall.** {*;}

-dontwarn com.vhall.business.** -keep class com.vhall.business.** {*;}

-dontwarn com.vhall.uilibs.** -keep class com.vhall.uilibs.** {*;}

-dontwarn com.vhall.vhalllive.** -keep class com.vhall.vhalllive.** {*;}