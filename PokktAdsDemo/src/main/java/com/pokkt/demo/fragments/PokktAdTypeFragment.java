package com.pokkt.demo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pokkt.PokktAds;
import com.pokkt.ad.demo.R;
import com.pokkt.demo.utility.FragmentTransactionManager;

public class PokktAdTypeFragment extends BaseFragment {

    // ui
    private Button btnAdTypeFullScreen, btnAdTypeBanner, btnAdTypeNative;
    private TextView txtSDKVersion;

    public PokktAdTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getArguments here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pokkt_ad_type, container, false);

        //version info
        ((TextView) findView(rootView, R.id.txt_sdk_version)).setText(PokktAds.getSDKVersion());

        //Fullscreen
        btnAdTypeFullScreen = (Button) findView(findView(rootView, R.id.btn_ad_type_fullscreen), R.id.button);

        //banner
        btnAdTypeBanner = (Button) findView(findView(rootView, R.id.btn_ad_type_banner), R.id.button);

        //native
        btnAdTypeNative = (Button) findView(findView(rootView, R.id.btn_ad_type_native), R.id.button);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup data

        // btnAdTypeFullScreen
        btnAdTypeFullScreen.setText(getString(R.string.txt_btn_ad_type_fullscreen));
        setFont(btnAdTypeFullScreen);
        btnAdTypeFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullScreenAdsShowcase();
            }
        });
        // btnAdTypeBanner
        btnAdTypeBanner.setText(getString(R.string.txt_btn_ad_type_banner));
        setFont(btnAdTypeBanner);
        btnAdTypeBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBannerAdsShowcase();
            }
        });
        // btnAdTypeOutStream
        btnAdTypeNative.setText(getString(R.string.txt_btn_ad_type_native));
        setFont(btnAdTypeNative);
        btnAdTypeNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNativeAdsShowcase();
            }
        });
    }

    private void openFullScreenAdsShowcase() {
        PokktFullScreenFragment fragment = new PokktFullScreenFragment();
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktFullScreenFragment.class.getSimpleName());
    }

    private void openBannerAdsShowcase() {
        PokktBannerFragment fragment = new PokktBannerFragment();
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktBannerFragment.class.getSimpleName());
    }

    private void openNativeAdsShowcase() {
        PokktNativeAdFragment fragment = new PokktNativeAdFragment();
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktNativeAdFragment.class.getSimpleName());
    }

    @Override
    public void onDestroy() {
        getActivity().finish();
        super.onDestroy();
    }
}
