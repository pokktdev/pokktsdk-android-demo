package com.pokkt.demo.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.pokkt.PokktAds;
import com.pokkt.ad.demo.R;
import com.pokkt.sdk.pokktnativead.PokktNativeAd;
import com.pokkt.sdk.pokktnativead.PokktNativeAdLayout;

public class PokktNativeScrollFragment extends BaseFragment implements PokktAds.NativeAdsDelegate {


    private String screenID = "684ab1e66abeb060faa500136c4c6a74";
    private View rootView;

    public PokktNativeScrollFragment() {
    }

    private static final String ARG_SCREEN_ID = "screenId";

    static PokktNativeScrollFragment newInstance(String screenId) {
        PokktNativeScrollFragment fragment = new PokktNativeScrollFragment();
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
        rootView = inflater.inflate(R.layout.fragment_pokkt_native_scroll_ad_type, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.pokkttoolbar);
        rootView.findViewById(R.id.scrollView).setContentDescription("test_scrollView");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PokktNativeScrollFragment.this.getActivity() != null) {
                    PokktNativeScrollFragment.this.getActivity().onBackPressed();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PokktAds.requestNativeAd(screenID, this); // step 1
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pokktNativeAd != null) {
            pokktNativeAd.destroy();
        }
        PokktNativeAdFragment.isAllowClick = true;
    }

    @Override
    public void adReady(String screenId, PokktNativeAd pokktNativeAd) {
        this.pokktNativeAd = pokktNativeAd;
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PokktNativeScrollFragment.this.getActivity(), "Ad Ready ", Toast.LENGTH_SHORT).show();
            }
        });
        pokktNativeAd.setMediaView((PokktNativeAdLayout) rootView.findViewById(R.id.pokkt_native_ad), getContext());
    }

    @Override
    public void adFailed(String screenId, final String errorMessage) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(errorMessage)) {
                    Toast.makeText(PokktNativeScrollFragment.this.getActivity(), "Ad Displayed ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PokktNativeScrollFragment.this.getActivity(), "Ad Show Error " + errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
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
                    Toast.makeText(PokktNativeScrollFragment.this.getActivity(), "Ad Completed ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PokktNativeScrollFragment.this.getActivity(), "Ad Skipped ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
