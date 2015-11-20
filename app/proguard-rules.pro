# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /opt/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-keep class org.apache.http.** { *; }
#-dontwarn org.apache.http.**
#
#-keep class android.net.http.** { *; }
#-dontwarn android.net.http.**
#
#-keep class android.support.v7.** { *; }
#-keep class android.support.v4.** { *; }
#
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

-keep class com.androidquery.** { *; }
-dontwarn com.androidquery.**

# Butter Knife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}