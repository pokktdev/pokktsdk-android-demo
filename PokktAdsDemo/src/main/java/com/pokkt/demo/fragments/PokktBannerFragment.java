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

import androidx.appcompat.widget.Toolbar;

import com.pokkt.PokktAds;
import com.pokkt.ad.demo.R;
import com.pokkt.sdk.banners.PokktBannerView;

public class PokktBannerFragment extends BaseFragment implements PokktAds.BannerAdDelegate {

    // ui
    private TextView txtScreenId;
    private EditText edtScreenId;
    private Button btnLoadTopBanner, btnDestroyTopBanner;
    private ProgressBar progressLoadTopBanner;
    private PokktBannerView pokktBannerViewTop;

    public PokktBannerFragment() {
        // Required empty public constructor
    }

    private String bannerScreenId = "129cc53b4666f5ae1ebad6a9bc942764";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getArguments here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pokkt_banner, container, false);

        // screen name
        txtScreenId = (TextView) findView(rootView, R.id.txt_screen_id);
        edtScreenId = (EditText) findView(rootView, R.id.edt_screen_id);
        edtScreenId.setText(bannerScreenId);

        // load banner
        progressLoadTopBanner = (ProgressBar) findView(findView(rootView, R.id.btn_load_banner), R.id.progressBar);
        btnLoadTopBanner = (Button) findView(findView(rootView, R.id.btn_load_banner), R.id.button);

        // destroy banner
        btnDestroyTopBanner = (Button) findView(findView(rootView, R.id.btn_destroy_banner), R.id.button);

        // pokkt banner containers = space needs to be provided to show banner on your screen using placeholder provided by PokktSDK
        pokktBannerViewTop = (PokktBannerView) findView(rootView, R.id.pokkt_banner_view_top);
        Toolbar toolbar = rootView.findViewById(R.id.pokkttoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup data
        // screen name
        //setFont(edtScreenName);
        setFont(txtScreenId);

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

        // OPTIONAL but we SUGGEST you to implement callbacks as it will help you to determine the status of your request

    }

    private void loadBanner(PokktBannerView pokktBannerView) {
        String screenId = edtScreenId.getText().toString();
        if (TextUtils.isEmpty(screenId)) {
            Toast.makeText(getActivity(), "Please Enter Screen Id", Toast.LENGTH_SHORT).show();
            progressLoadTopBanner.setVisibility(View.GONE);
            return;
        }
        PokktAds.showAd(screenId, PokktBannerFragment.this, pokktBannerView);
    }

    /**
     * To destroy PokktBannerView is mandatory we recommend you to destroy all you PokktBannerView instances
     * on onDestroy of activity or fragment or any place of your choice.
     *
     * @param pokktBannerView
     */
    private void destroyBanner(PokktBannerView pokktBannerView) {
        PokktAds.destroyBanner(pokktBannerView);
    }

    @Override
    public void onDestroy() {
        destroyBanner(pokktBannerViewTop);
        super.onDestroy();
    }

    // Banner Ad Callbacks

    @Override
    public void adCachingResult(String screenId, boolean isSuccess, double reward, String errorMessage) {

    }

    @Override
    public void adDisplayedResult(String screenId, final boolean isSuccess, final String errorMessage) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressLoadTopBanner.setVisibility(View.GONE);
                if (isSuccess) {
                    Toast.makeText(getActivity(), "Banner Loaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void adClosed(String screenId, boolean isComplete) {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void adGratified(String screenId, double reward) {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bannerExpanded(String screenId) {

    }

    @Override
    public void bannerResized(String screenId) {

    }

    @Override
    public void bannerCollapsed(String screenId) {

    }

    @Override
    public void adClicked(String screenId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "AdClicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
