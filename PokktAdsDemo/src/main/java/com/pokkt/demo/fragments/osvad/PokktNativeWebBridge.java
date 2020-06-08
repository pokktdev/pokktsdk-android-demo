package com.pokkt.demo.fragments.osvad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;


public class PokktNativeWebBridge {

    private Activity activity;
    private static String TAG = "NativeAdWebView";
    private WebView webView;
    private PokktNativeAdDelegates osvadDelegates;

    @SuppressLint("SetJavaScriptEnabled")
    public PokktNativeWebBridge(Activity activity, WebView webView, PokktNativeAdDelegates osvadDelegates, boolean shouldDebug) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(shouldDebug);
        }

        this.activity = activity;
        this.webView = webView;
        this.osvadDelegates = osvadDelegates;

        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.addJavascriptInterface(this, "PokktOSVAD");
    }

    void removeAgent() {
        executeJS("removeAgent();");
    }

    public void setAgentSize(float width, float height) {
        executeJS("setAgentSize(" + width + ", " + height + ")");
    }

    public void setAgentBGColor(String value) {
        executeJS("setAgentBGColor(" + value + ")");
    }

    public void findLocationForAd() {
        executeJS("findLocationForAd()");
    }

    public WebView getWebView() {
        return webView;
    }

    @JavascriptInterface
    public void pokktOSVADInitDone() {
        assert osvadDelegates != null;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                osvadDelegates.pokktOSVADInitDone();
            }
        });
    }

    @JavascriptInterface
    public void setAgentVisible(final boolean isVisible) {
        assert osvadDelegates != null;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                osvadDelegates.setAgentVisible(isVisible);
            }
        });
    }

    @JavascriptInterface
    public void updateAgentBounds(final int posX, final int posY, final int width, final int height) {
        assert osvadDelegates != null;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                osvadDelegates.updateAgentBounds(posX, posY, width, height, webView.getScale());
            }
        });
    }

    private void executeJS(final String script) {
        assert activity != null;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript(script, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            //Log.i(TAG, "after evaluating string: " + s);
                        }
                    });
                } else {
                    webView.loadUrl("javascript:" + script);
                }
            }
        });
    }

    public void injectBridgeJS() {
        try {
            String outJS = Base64.encodeToString(NativeWebAdJs.nativewebad_js.getBytes(), Base64.NO_WRAP);
            executeJS("(function() {" +
                    "var script = document.getElementById('osvad_script');" +
                    "if (!script || script.nodeName != 'SCRIPT') { " +
                    "   script = document.createElement('script');" +
                    "   script.id = 'osvad_script';" +
                    "   script.type = 'text/javascript';" +
                    "}" +
                    "script.innerHTML = window.atob('" + outJS + "');" +
                    "var parent = document.getElementsByTagName('body').item(0);" +
                    "parent.appendChild(script)" +
                    "})()");
        } catch (Exception e) {
            Log.e(TAG,"Error",e);
        }
    }
}
