package com.pokkt.demo.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pokkt.PokktAds;
import com.pokkt.sdk.banners.PokktBannerView;
import com.pokkt.ad.demo.R;

public class PokktBannerFragment extends BaseFragment implements PokktAds.Banner.BannerAdDelegate {

    // ui
    private TextView txtAdType,txtScreenName;
    private EditText edtScreenName;
    private Button btnLoadTopBanner, btnDestroyTopBanner, btnLoadBottomBanner, btnDestroyBottomBanner;
    private ProgressBar progressLoadTopBanner, progressLoadBottomBanner;
    private PokktBannerView pokktBannerViewTop;
    private PokktBannerView pokktBannerViewBottom;

    public PokktBannerFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_pokkt_banner, container, false);

        // ad type heading
        txtAdType = (TextView) findView(rootView,R.id.txt_ad_type);

        // screen name
        txtScreenName = (TextView) findView(rootView,R.id.txt_screen_name);
        edtScreenName = (EditText) findView(rootView, R.id.edt_screen_name);

        // load banner
        progressLoadTopBanner = (ProgressBar) findView(findView(rootView, R.id.btn_load_banner), R.id.progressBar);
        btnLoadTopBanner = (Button) findView(findView(rootView, R.id.btn_load_banner), R.id.button);

        // destroy banner
        btnDestroyTopBanner = (Button) findView(findView(rootView, R.id.btn_destroy_banner), R.id.button);

        // load second banner
        progressLoadBottomBanner = (ProgressBar) findView(findView(rootView, R.id.btn_load_second_banner), R.id.progressBar);
        btnLoadBottomBanner = (Button) findView(findView(rootView, R.id.btn_load_second_banner), R.id.button);

        // destroy second banner
        btnDestroyBottomBanner = (Button) findView(findView(rootView, R.id.btn_destroy_second_banner), R.id.button);

        // pokkt banner containers = space needs to be provided to show banner on your screen using placeholder provided by PokktSDK
        pokktBannerViewTop = (PokktBannerView) findView(rootView, R.id.pokkt_banner_view_top);
        pokktBannerViewBottom = (PokktBannerView) findView(rootView, R.id.pokkt_banner_view_bottom);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup data

        /// ad Type
        setFont(txtAdType);

        // screen name
        //setFont(edtScreenName);
        setFont(txtScreenName);

        // load banner
        setFont(btnLoadTopBanner);
        setProgressbarColor(progressLoadTopBanner);
        btnLoadTopBanner.setText(getString(R.string.txt_btn_load_top_banner));
        btnLoadTopBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLoadTopBanner.setVisibility(View.VISIBLE);
                loadBanner(pokktBannerViewTop);
            }
        });

        // destroy banner
        setFont(btnDestroyTopBanner);
        btnDestroyTopBanner.setText(getString(R.string.txt_btn_destroy_top_banner));
        btnDestroyTopBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroyBanner(pokktBannerViewTop);
            }
        });

        // load second banner
        setFont(btnLoadBottomBanner);
        setProgressbarColor(progressLoadBottomBanner);
        btnLoadBottomBanner.setText(getString(R.string.txt_btn_load_bottom_banner));
        btnLoadBottomBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLoadBottomBanner.setVisibility(View.VISIBLE);
                loadBanner(pokktBannerViewBottom);
            }
        });

        // destroy second banner
        setFont(btnDestroyBottomBanner);
        btnDestroyBottomBanner.setText(getString(R.string.txt_btn_destroy_bottom_banner));
        btnDestroyBottomBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroyBanner(pokktBannerViewBottom);
            }
        });

        // OPTIONAL but we SUGGEST you to implement callbacks as it will help you to determine the status of your request
        PokktAds.Banner.setDelegate(this);

    }

    private void loadBanner(PokktBannerView pokktBannerView) {
        String screenName = edtScreenName.getText().toString();
        if (TextUtils.isEmpty(screenName)) {
            Toast.makeText(getActivity(), "Please Enter Screen Name", Toast.LENGTH_SHORT).show();
            return;
        }
        PokktAds.Banner.loadBanner(screenName, pokktBannerView);
    }

    /**
     * To destroy PokktBannerView is mandatory we recommend you to destroy all you PokktBannerView instances
     * on onDestroy of activity or fragment or any place of your choice.
     *
     * @param pokktBannerView
     */
    private void destroyBanner(PokktBannerView pokktBannerView) {
        PokktAds.Banner.destroyContainer(pokktBannerView);
    }

    @Override
    public void onDestroy() {
        destroyBanner(pokktBannerViewTop);
        destroyBanner(pokktBannerViewBottom);
        super.onDestroy();
    }

    // Banner Ad Callbacks

    @Override
    public void bannerLoaded(String screenName) {
        if(getActivity() == null){
            return;
        }
        progressLoadTopBanner.setVisibility(View.GONE);
        progressLoadBottomBanner.setVisibility(View.GONE);

        Toast.makeText(getActivity(), "Banner Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bannerLoadFailed(String screenName, String errorMessage) {
        if(getActivity() == null){
            return;
        }
        progressLoadTopBanner.setVisibility(View.GONE);
        progressLoadBottomBanner.setVisibility(View.GONE);

        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

}
