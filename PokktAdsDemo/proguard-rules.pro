# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android\sdk/tools/proguard/proguard-android.txt
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

##---------------Begin: proguard configuration common for all Android apps ----------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.view.View
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keepattributes Exceptions,Signature,Deprecated,EnclosingMethod,InnerClasses

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

## Support libraries

# support-v4
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-dontnote android.support.v4.**

# support-v7
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }
-dontnote android.support.v7.**

# support annotations
-dontwarn android.support.annotations.**
-keep class android.support.annotations.** { *; }

# Google play services
-keep class com.google.android.gms.** { *; }
-keepclassmembers class * {
@com.google.api.client.util.Key <fields>;
}
-dontnote com.google.android.gms.**
-dontwarn com.google.android.gms.**

                # Pokkt SDK
-keep class com.pokkt.** { public *; }
-dontwarn com.pokkt.**

# moat
-keep class com.moat.** { *; }
-dontwarn com.moat.**
# comscore
-keep class com.comscore.** { *; }
-dontwarn com.comscore.**
-dontnote com.comscore.**
#facebook
-keep class com.facebook.** {*;}
-dontwarn com.facebook.**
# fabric
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-keep class io.fabric.** { *; }
-dontwarn io.fabric.**
# flurry
-keep class com.flurry.** { *; }
-dontwarn com.flurry.**
-keepattributes *Annotation*,EnclosingMethod
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}
# MixPanel
-dontwarn com.mixpanel.**



