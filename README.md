##Pokkt Android SDK Integration Guide

###Overview
Thank you for choosing Pokkt SDK for Android . This document contains all the information required to set up the SDK with your project. We also support mediation for various third party networks. To know the supported third party networks and their integration process go to
mediation section.

Before implementing plugins it is mandatory to go through project configuration and implementation steps , as these sections contain mandatory steps for basic SDK integration and are followed by every plugin. You can download our SDK from wiki.pokkt.com .

### Dependency jars:
Please add google play services <br>
minSdkVersion supported is 11 .<br>

**ScreenId**: This one parameter is accepted by almost all API’s of Pokkt SDK. This controls the placement of ads and can be created on Pokkt Dashboard.

###Project Configuration
####Dependencies
● Add PokktSDK_v8.0.0.jar or PokktSDK_v8.0.0.aar to your project.<br>
● We expect Google play services integrated in project, although it;s optional but we recommend you to integrate it, as it’s required to fetch AdvertisingID for device,which is useful to deliver targeted advertising to Android users.

####Manifest Permissions Declarations

Add the following permissions to your project manifest

1. Mandatory permissions.
`<uses-permission android:name=“android.permission.INTERNET” />`
`<uses-permission android:name=“android.permission.ACCESS_NETWORK_STATE” />`

● **android.permission.INTERNET** = Required for SDK communication with server.<br>
● **android.permission.ACCESS_NETWORK_STATE** =Required to detect changes in network, like if WIFI is available or not.

2. Optional permissions.
`<uses-permission android:name=“android.permission.ACCESS_WIFI_STATE” />`<br>
`<uses-permission android:name=“android.permission.CHANGE_WIFI_STATE” />`<br>
`<uses-permission android:name=“android.permission.WAKE_LOCK” />`<br>
`<uses-permission android:name=“android.permission.WRITE_EXTERNAL_STORAGE” />`<br>
`<uses-permission android:name=“android.permission.WRITE_CALENDAR” />`<br>
`<uses-permission android:name=“android.permission.ACCESS_COARSE_LOCATION” />`<br>
`<uses-permission android:name=“android.permission.ACCESS_FINE_LOCATION” />`<br>
`<uses-permission android:name=“android.permission.CALL_PHONE” />`<br>
`<uses-permission android:name=“android.permission.SEND_SMS” />`<br>

● **android.permission.ACCESS_WIFI_STATE** = Required to detect changes in network, like if WIFI is available or not.<br>
● **android.permission.CHANGE_WIFI_STATE** = Required to detect changes in network, like if WIFI is available or not.<br>
● **android.permission.WAKE_LOCK** = Required to prevent device from going into the sleep mode during video play.<br>
● **android.permission.WRITE_EXTERNAL_STORAGE** = Required to store media files related to ads in external SD card, if not provided we will use app cache folder to store media files, which will result in unnecessary increase in application’s size. It is recommended to ask for this permission as low end devices generally have less internally memory available.<br>
● **android.permission.WRITE_CALENDAR** = Some Ads create events in calendar.<br>
● **android.permission.ACCESS_COARSE_LOCATION** = Some Ads show content based on user’s location. <br>
● **android.permission.ACCESS_FINE_LOCATION** = Some Ads show content based on user’s location.<br>
● **android.permission.CALL_PHONE** = Some Ads are interactive and they provide you a way to call directly from the content.<br>
● **android.permission.SEND_SMS** = Some Ads are interactive and they provide you a way to send message.<br>

###Activity Declaration
Add the following activity in your AndroidManifest for Pokkt SDK integration.
	`<activity
android:name=“com.pokkt.sdk.`<br>`PokktAdActivity”`<br>`
android:configChanges=“keyboard|keyboardHidden|navigation|`<br>
	`orientation|screenLayout|uiMode|`<br>
`screenSize|smallestScreenSize”`<br>
	`android:hardwareAccelerated=“true”`<br>
	`android:label=”Pokkt”`<br>
`android:screenOrientation=“landscape”`<br>
`android:windowSoftInputMode=“stateAlwaysHidden|adjustUnspecified” />` <br>

You can change the **android:screenOrientation=“landscape”** to of your choice, the way you want to display the ads.

###Implementation Steps
####SDK Configuration

1. Set Application Id and Security key in Pokkt SDK. You can get it from Pokkt dashboard from your account. These are unique per app registered.

	`PokktAds.setPokktConfig(“<Pokkt Application ID>”, `<br>`“<Pokkt Security Key>”, <Activity>);`
	
2. If you are using server to server integration with Pokkt, you can also set Third Party UserId in PokktAds.
`PokktAds.setThirdPartyUserId(“<Third party user Id>”);`

3. When your application is under development and if you want to see Pokkt logs and other informatory messages, you can enable it by setting shouldDebug to true . Make sure to disable debugging before release.
`PokktAds.Debugging.shouldDebug(<true>);`<br>

####Ad Types
#####FullScreen Ads

● FullScreen Ads are of two types : Video and Interstitial.
● FullScreen ads can be rewarded or non-rewarded.
● FullScreen properties can be configured from the Pokkt dashboard.
● You can either cache the ad in advance or directly call show for it.
● We suggest you cache the ad in advance so as to give seamless play behaviour, In other case it will stream the video which may lead to unnecessary buffering delays depending on the network connection.<br>

1. To cache FullScreen ad call:
 `PokktAds.cacheAd("<ScreenId>",<PokktAdDelegate>);`
2. To show FullScreen ad call:
`PokktAds.showAd("<ScreenId>",<PokktAdDelegate>,null);`

● You can check if ad is available or not before making cache or show request. <br>
`PokktAds.isAdCached("<ScreenId>");`

####Banner
● Add PokktBannerView to your layout, we use it as placeholder to populate banner ad into it. <br>
`<com.pokkt.sdk.banners.PokktBannerView`<br>
`android:id=“@+id/pokkt_banner_view_top”`<br>
`android:layout_width=“320dp”`<br>
`android:layout_height=“50dp”`<br>
`android:layout_centerHorizontal=“true”/>`<br>

● Load banner <br>
`PokktAds.showAd("<ScreenId>",<PokktAdDelegate>,<PokktBannerView>);`<br>
● You can remove Banner using:<br>
`PokktAds.destroyBanner(<pokktBannerView>);`<br>

#### Native Ads
A native ad may be served in feed or in between the developer content inside the app. Normally, FullScreen ads are delivered on call to action by the user. Native ads eliminate this limitation and show ads without any user request.Native ads are also non intrusive as they will be automatically paused when the ad is out of the view by scrolling. The PokktNativeAdLayout will be part of developer application parent components which may be ListView, Layout in ScrollView or WebView.<br>

● Add PokktNativeAdLayout to your Layout XML<br>
`...`<br>
`<com.pokkt.sdk.pokktnativead.PokktNativeAdLayout`<br>
    `android:id="@+id/pokkt_native_ad`<br>
    `android:layout_width="match_parent"`<br>
    `android:layout_height="300dp"`<br>
    `android:background="@android:color/white"`<br>
    `android:visibility="visible" />`<br>
`...`<br>

● Request for native Ad.
`PokktAds.requestNativeAd(“<ScreenId>”, <NativeAdsDelegate>);`<br>

● Once ​PokktNativeAd​ is received in the AdReady callback of ​NativeAdsDelegate​ implementation, pokktNativeAdLayout view should be set to pokktNativeAd.
`pokktNativeAd.setMediaView(findViewById(R.id.pokkt_native_ad),context);`<br>

● Developers will have to do cleanup of Native Ad in onDestroy of activity life cycle.
`pokktNativeAd.destroy();`<br>

####Ad Delegates
#####FullScreen Ad Delegates
Ad delegates are optional, but we suggest to implement them as it will help you to keep track of the status of your ad request. <br>

~~~
PokktAdDelegate pokktAdDelegate = new PokktAds.PokktAdDelegate() {
    @Override
    public void adCachingResult(screenId,isSuccess,reward,errorMessage) {
    }
    @Override
    public void adDisplayedResult(screenId,isSuccess,errorMessage) {
    }
    @Override
    public void adClosed(screenId,isComplete) {\
    }
    @Override
    public void adGratified(screenId,reward) {
    }
   @Override
    public void adClicked(screenId) {
    }
};
~~~

~~~
#### Native Ad Delegates
PokktAds.NativeAdsDelegate delegate = new PokktAds.NativeAdsDelegate()
    { @Override
    public void adReady(screenId,pokktNativeAd) {
    }
    @Override
    public void adFailed(screenId, String errorMessage) {
    }
    @Override
    public void adClosed(String screenId, boolean isComplete) {
    }
};
~~~~

####Banner Delegates
~~~
PokktAds.BannerAdDelegate adDelegate = new PokktAds.BannerAdDelegate() { @Override
   public void bannerExpanded(screenId) {
   }
   @Override
   public void bannerResized(screenId) {
   }
   @Override
   public void bannerCollapsed(screenId) {
   }
   @Override
   public void adCachingResult(screenId,isSuccess,reward,errorMessage) {
   }
   @Override
   public void adDisplayedResult(screenId,isSuccess,errorMessage) { //called when banner is loaded/failed to load
   }
   @Override
   public void adClosed(screenId,isComplete) {
   }
   @Override
   public void adGratified(screenId,reward) {
   }
   @Override
   public void adClicked(screenId) {
   //called when banner is clicked
   }
};
~~~
####Pokkt ad player configuration
Pokkt Ad player works the way App is configured at Pokkt dashboard, but we provide a way to override those settings using PokktAdPlayerViewConfig .

Application should prefer configuration provided through code by developer or what’s configured for the app in dashboard, can be controlled any time through the dashboard itself. If you want to
make changes to this configuration after your app distribution, you can contact Pokkt Team to do the same for your app through admin console.<br>

~~~
PokktAdPlayerViewConfig adPlayerViewConfig = new PokktAdPlayerViewConfig ();
// set properties values to adPlayerViewConfig
PokktAds.setAdPlayerViewConfig(adPlayerViewConfig );
~~~
Various setters for the properties that can be managed through this are:

1. **Back button**<br>
Defines if user is allowed to close the Advertisement by clicking on back button or not.<br>
Setter Name : setBackButtonDisabled(boolean backButtonDisabled)<br>

	*Values*:<br>
True = Back button is disabled and user cannot close the Ad.<br>
False = Back button is not disabled and user can close the Ad.<br>

2. **Default skip time**<br>
Defines the time after which user can skip the Ad.<br>
Setter name: setDefaultSkipTime(int defaultSkipTime)<br>

	*Values*: Any Integer value.<br>
Default value is 10 seconds .

3. **Should allow skip**<br>
Defines if user is allowed to skip the Ad or not.<br>
Setter name: setShouldAllowSkip(boolean shouldAllowSkip)<br>

	*Values*:<br>
True = User can skip Ad.<br>
False = User can’t skip Ad.<br>
4. **Should allow mute**
Defines if user is allowed to mute the Video Ad or not.<br>
Setter name: setShouldAllowMute(boolean shouldAllowMute)<br>
*Values*:<br>
True = User can mute video Ad.<br>
False = User can’t mute video Ad.<br>

5. **Should confirm skip**
Defines if confirmation dialog is to be shown before skipping the Ad.<br>
Setter name: ShouldConfirmSkip<br>
*Values*:<br>
True = Confirmation dialog will be shown before skipping the video.<br>
False = Confirmation dialog will not be shown before skipping the video.<br>

6. **Skip confirmation message**<br>
Defines what confirmation message to be shown in skip dialog.<br>
Setter name: setShouldSkipConfirm(boolean shouldSkipConfirm)<br>
*Values*:<br>
 Any String message.  Default value is “Skipping this video will earn you NO rewards. Are you sure?”.
 
7. **Affirmative label for skip dialog**<br>
Defines what should be the label for affirmative button in skip dialog.<br>
Setter name: setSkipConfirmYesLabel(String skipConfirmYesLabel)<br>
*Values*:<br>
Any String message.Default value is “Yes”.<br>

8. **Negative label for skip dialog**
Defines what should be the label for affirmative button in skip dialog.<br>
Setter name: setSkipConfirmNoLabel(String skipConfirmNoLabel)<br>
*Values*:<br>
Any String message. Default value is “No”.<br>

9. **Skip timer message**<br>
Defines message to be shown before enabling skip button. Don’t forget to add placeholder “ ## ” in your custom message. This placeholder is replaced by property “Default skip time” assigned above.<br>

	Setter name: setSkipTimerMessage(String skipTimerMessage)<br>
*Values*:<br>
Any String message. Default value is “You can skip video in ## seconds”<br>

10. **Incentive message**<br>
Defines message to be shown during video progress, that after what<br> time user will be incentivised.<br>

	Setter name: setIncentiveMessage(String incentiveMessage)<br>
*Values*:<br>
Any String message Default value is “more seconds only for your reward !”<br>

####User Details
For better targeting of ads you can also provide user details to our SDK using.

~~~
PokktUserDetails pokktUserDetails = new PokktUserDetails();
pokktUserDetails.setName(" ");
pokktUserDetails.setAge(" ");
pokktUserDetails.setSex(" ");
pokktUserDetails.setMobileNumber(" ");
pokktUserDetails.setEmailAddress(" ");
pokktUserDetails.setLocation(" ");
pokktUserDetails.setBirthday(" ");
pokktUserDetails.setMaritalStatus(" ");
pokktUserDetails.setFacebookId(" ");
pokktUserDetails.setTwitterHandle(" ");
pokktUserDetails.setEducation(" ");
pokktUserDetails.setNationality(" ");
pokktUserDetails.setEmployment(" ");
pokktUserDetails.setMaturityRating(" ");
PokktAds.setUserDetails(pokktUserDetails);
~~~
####Debugging

Other than enabling debugging for Pokkt SDK, it can also be used to:

1. **Export log**
Export your log to your desired location, we generally have it in root directory of SD card, if permission for external storage is provided and in cache folder otherwise.

	`PokktAds.Debugging.exportLog(getActivity());`<br>

2. **Export log to cloud**
You can also export log to cloud.
`PokktAds.Debugging.exportLogToCloud(getActivity());`<br>

~~~
####Proguard
If you are using proguard in your app, add following rules to your proguard file.

##### Pokkt SDK
-keep class com.pokkt.** { public *; }
-dontwarn com.pokkt.**
# moat
-keep class com.moat.** { *; }
-dontwarn com.moat.**
-keep class com.c.c.**{*;}
# OM
-keep class com.iab.omid.library.pokkt.**{*;}
-dontwarn com.iab.omid.**
# 360 if Pokktsdk360ext.jar or Pokktsdk360ext.aar is added
-keep class com.pokkt.sdk360ext.** { *; }
# For communication with Pokkt WebView
-keepclassmembers class * {
@android.webkit.JavascriptInterface <methods>;
}
