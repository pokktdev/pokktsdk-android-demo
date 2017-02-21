package com.pokkt.demo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pokkt.PokktAds;
import com.pokkt.demo.utility.FragmentTransactionManager;
import com.pokkt.ad.demo.R;


public class PokktAdTypeFragment extends BaseFragment {

    // ui
    private TextView txtTestRelease,txtFrameworkName, txtSDKVersion;
    private Button btnAdTypeVideo, btnAdTypeInterstitial, btnAdTypeBanner, btnAdTypeMore;

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
        txtTestRelease = (TextView) findView(rootView, R.id.txt_test_release_version);
        txtFrameworkName = (TextView) findView(rootView, R.id.txt_framework_name);
        txtSDKVersion = (TextView) findView(rootView, R.id.txt_sdk_version);

        //video
        btnAdTypeVideo = (Button) findView(findView(rootView, R.id.btn_ad_type_video), R.id.button);

        //interstitial
        btnAdTypeInterstitial = (Button) findView(findView(rootView, R.id.btn_ad_type_interstitial), R.id.button);

        //banner
        btnAdTypeBanner = (Button) findView(findView(rootView, R.id.btn_ad_type_banner), R.id.button);

        //more == settings
        btnAdTypeMore = (Button) findView(findView(rootView, R.id.btn_ad_type_more), R.id.button);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup data

        // txtTestRelease
        if (getResources().getBoolean(R.bool.is_test_release)) {
            txtTestRelease.setVisibility(View.VISIBLE);
            txtTestRelease.setText(String.format(getString(R.string.txt_test_release_version), getString(R.string.test_release_version)));
            setFont(txtTestRelease);

            txtFrameworkName.setVisibility(View.VISIBLE);
            txtFrameworkName.setText(getString(R.string.framework_name));
            setFont(txtFrameworkName);
        }

        // txtSDKVersion
        txtSDKVersion.setText(String.format(getString(R.string.txt_sdk_version), PokktAds.getSDKVersion()));
        setFont(txtSDKVersion);

        // btnAdTypeVideo
        btnAdTypeVideo.setText(getString(R.string.txt_btn_ad_type_video));
        setFont(btnAdTypeVideo);
        btnAdTypeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideoAdsShowcase();
            }
        });
        // btnAdTypeInterstitial
        btnAdTypeInterstitial.setText(getString(R.string.txt_btn_ad_type_interstitial));
        setFont(btnAdTypeInterstitial);
        btnAdTypeInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInterstitialAdsShowcase();
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
        // btnAdTypeMore
        btnAdTypeMore.setText(getString(R.string.txt_btn_more));
        setFont(btnAdTypeMore);
        btnAdTypeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoreSettings();
            }
        });


    }

    private void openVideoAdsShowcase() {
        PokktVideoFragment fragment = new PokktVideoFragment();
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktVideoFragment.class.getSimpleName());
    }

    private void openInterstitialAdsShowcase() {
        PokktInterstitialFragment fragment = new PokktInterstitialFragment();
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktInterstitialFragment.class.getSimpleName());
    }

    private void openBannerAdsShowcase() {
        PokktBannerFragment fragment = new PokktBannerFragment();
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktBannerFragment.class.getSimpleName());
    }

    private void openMoreSettings() {
        PokktSettingsFragment fragment = new PokktSettingsFragment();
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktSettingsFragment.class.getSimpleName());
    }

    @Override
    public void onDestroy() {
        getActivity().finish();
        super.onDestroy();
    }
}
