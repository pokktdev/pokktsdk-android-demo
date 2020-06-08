package com.pokkt.demo.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.pokkt.PokktAds;
import com.pokkt.ad.demo.R;
import com.pokkt.demo.fragments.osvad.PokktNativeWebBridge;
import com.pokkt.demo.fragments.osvad.PokktNativeAdDelegates;
import com.pokkt.sdk.pokktnativead.PokktNativeAd;
import com.pokkt.sdk.pokktnativead.PokktNativeAdLayout;

public class PokktNativeWebViewFragment extends BaseFragment implements PokktAds.NativeAdsDelegate {

    private String screenID = "684ab1e66abeb060faa500136c4c6a74";
    private static String TAG = "NativeAdWebView";
    private WebView webView;
    private Button button;

    public PokktNativeWebViewFragment() {
    }

    private static final String ARG_SCREEN_ID = "screenId";

    static PokktNativeWebViewFragment newInstance(String screenId) {
        PokktNativeWebViewFragment fragment = new PokktNativeWebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SCREEN_ID, screenId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            screenID = getArguments().getString(ARG_SCREEN_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pokkt_native_webview_ad_type, container, false);

        webView = rootView.findViewById(R.id.pokktoswebview);
        webView.setContentDescription("test_webView");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                initAgent();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
        });

        button = rootView.findViewById(R.id.pokktoswebbutton);
        button.setText("Show Ad");
        button.setEnabled(false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM | RelativeLayout.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://en.wikipedia.org/wiki/Jon_Snow_(character)");
                //               webView.loadUrl("https://s3-ap-southeast-1.amazonaws.com/pokktdocumentation/windows/test.html");
                //webView.loadUrl("file:///android_asset/demo.html");
                button.setEnabled(false);
                setupBridge(pokktNativeAd);
            }
        });
        Toolbar toolbar = rootView.findViewById(R.id.pokkttoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PokktNativeWebViewFragment.this.getActivity() != null) {
                    PokktNativeWebViewFragment.this.getActivity().onBackPressed();
                }
            }
        });
        return rootView;
    }

    private void initAgent() {
        pokktOSVADBridge.injectBridgeJS();
    }

    private PokktNativeWebBridge pokktOSVADBridge;

    private void setupBridge(final PokktNativeAd pokktNativeAd) {
        pokktOSVADBridge = new PokktNativeWebBridge(getActivity(), webView, new PokktNativeAdDelegates() {
            @Override
            public void pokktOSVADInitDone() {
                Log.i(TAG,"osvad script init done!");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        setAgentSize(pokktOSVADBridge.getWebView().getScale());
                        View container = LayoutInflater.from(getContext()).inflate(R.layout.native_container, null, false);
                        pokktNativeAd.setMediaView((PokktNativeAdLayout) container.findViewById(R.id.pokkt_native_ad),getContext());
                        pokktOSVADBridge.getWebView().addView(container);
                    }
                });
            }

            @Override
            public void setAgentVisible(boolean isVisible) {
                Log.i(TAG,"is osvad agent visible on web page: " + isVisible);
            }

            @Override
            public void updateAgentBounds(int posX, int posY, int width, int height, final float scaleFactor) {
                Log.i(TAG,"WV Resized: " + " posX " + posX + " posY " + posY + " width " + width + " height " + height);
                final Rect rect = new Rect((int) (posX * scaleFactor), (int) (posY * scaleFactor), (int) (width * scaleFactor), (int) (height * scaleFactor));
                Log.i(TAG,"WV Resized:after scaling " + rect);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        resizeVideoView(rect);
//                        setAgentSize(scaleFactor);
                    }
                });
            }
        }, PokktAds.Debugging.isEnabled());
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PokktAds.requestNativeAd(screenID, this); // step 1
        //webView.loadUrl("file:///android_asset/demo.html");
//        webView.loadUrl("https://wiki.pokkt.com/Native_Android");
    }

    @Override
    public void adReady(String screenId, PokktNativeAd pokktNativeAd) {
        button.setEnabled(true);
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Ad Ready ", Toast.LENGTH_SHORT).show();
            }
        });
        this.pokktNativeAd = pokktNativeAd;
    }

    @Override
    public void adFailed(String screenId, String errorMessage) {

    }

    @Override
    public void adClosed(String screenId, final boolean isComplete) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isComplete) {
                    Toast.makeText(PokktNativeWebViewFragment.this.getActivity(), "Ad Completed ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PokktNativeWebViewFragment.this.getActivity(), "Ad Skipped ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pokktNativeAd != null) {
            pokktNativeAd.destroy();
        }
        PokktNativeAdFragment.isAllowClick = true;
    }
}
