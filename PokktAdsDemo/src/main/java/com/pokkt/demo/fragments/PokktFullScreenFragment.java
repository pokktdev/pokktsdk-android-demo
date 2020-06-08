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

public class PokktFullScreenFragment extends BaseFragment implements PokktAds.PokktAdDelegate {

    // ui
    private TextView txtScreenId;
    private TextView txtMessageId;
    private EditText edtScreenId;
    private ProgressBar progressCache, progressShow;
    private Button btnCache, btnShow;

    public PokktFullScreenFragment() {
        // Required empty public constructor
    }

    private String rewardedVideoScreenId = "684ab1e66abeb060faa500136c4c6a74";
    private String interstitialScreenId = "5e59028c8332c9583e742c183abbaafb";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getArguments here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokkt_fullscreen, container, false);

        // screen name
        txtScreenId = (TextView) findView(rootView, R.id.txt_screen_id);
        txtMessageId = (TextView) findView(rootView, R.id.txt_message_id);
        edtScreenId = (EditText) findView(rootView, R.id.edt_screen_id);
        edtScreenId.setText(rewardedVideoScreenId);

        // cache rewarded
        progressCache = (ProgressBar) findView(findView(rootView, R.id.btn_cache), R.id.progressBar);
        btnCache = (Button) findView(findView(rootView, R.id.btn_cache), R.id.button);

        // show rewarded
        progressShow = (ProgressBar) findView(findView(rootView, R.id.btn_show), R.id.progressBar);
        btnShow = (Button) findView(findView(rootView, R.id.btn_show), R.id.button);

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

        // cache rewarded
        setFont(btnCache);
        setProgressbarColor(progressCache);
        btnCache.setText(getString(R.string.txt_btn_cache));
        btnCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screenId = edtScreenId.getText().toString().trim();
                if (!PokktAds.isAdCached(screenId)) {
                    progressCache.setVisibility(View.VISIBLE);
                    if (isScreenIdSpecified()) {
                        PokktAds.cacheAd(screenId, PokktFullScreenFragment.this);
                    }
                } else {
                    Toast.makeText(getActivity(), "Ad already cached", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // show rewarded
        setFont(btnShow);
        setProgressbarColor(progressShow);
        btnShow.setText(getString(R.string.txt_btn_show));
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressShow.setVisibility(View.VISIBLE);
                if (isScreenIdSpecified()) {
                    String screenId = edtScreenId.getText().toString().trim();
                    if (PokktAds.isAdCached(screenId)) {
                        Toast.makeText(getActivity(), "Ad is cached, Showing", Toast.LENGTH_SHORT).show();
                    }
                    PokktAds.showAd(screenId, PokktFullScreenFragment.this, null);
                }
            }
        });
    }


    private boolean isScreenIdSpecified() {
        if (TextUtils.isEmpty(edtScreenId.getText())) {
            Toast.makeText(getActivity(), "Please Enter Screen ID", Toast.LENGTH_LONG).show();
            hideProgress();
            return false;
        }
        return true;
    }

    private void hideProgress() {
        progressCache.setVisibility(View.GONE);
        progressShow.setVisibility(View.GONE);
    }

    // Video Ad CallBacks

    @Override
    public void adCachingResult(final String screenId, final boolean isSuccess, final double reward, final String errorMessage) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess) {
                    txtMessageId.setText("Ad Caching Completed ! Ad vc is: " + reward);
                    Toast.makeText(getActivity(), "Ad Caching Completed ! Ad vc is: " + reward, Toast.LENGTH_SHORT).show();
                } else {
                    txtMessageId.setText("Ad Download Error " + errorMessage);
                    Toast.makeText(getActivity(), "Ad Download Error " + errorMessage, Toast.LENGTH_LONG).show();
                }
                hideProgress();
            }
        });
    }

    @Override
    public void adDisplayedResult(String screenId, final boolean isSuccess, final String errorMessage) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                if (isSuccess) {
                    txtMessageId.setText("Ad Displayed");
                    Toast.makeText(getActivity(), "Ad Displayed", Toast.LENGTH_SHORT).show();
                } else {
                    txtMessageId.setText("Ad Show Error " + errorMessage);
                    Toast.makeText(getActivity(), "Ad Show Error " + errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void adClosed(String screenId, final boolean isCompleted) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                if (isCompleted) {
                    txtMessageId.setText("Ad Completed");
                    Toast.makeText(getActivity(), "Ad Completed", Toast.LENGTH_SHORT).show();
                } else {
                    txtMessageId.setText("Ad Skipped");
                    Toast.makeText(getActivity(), "Ad Skipped", Toast.LENGTH_SHORT).show();
                }
                txtMessageId.setText("Ad Closed");
                Toast.makeText(getActivity(), "Ad Closed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void adGratified(String screenId, double reward) {
        if (getActivity() == null) {
            return;
        }
        txtMessageId.setText("Points Earned " + reward);
        Toast.makeText(getActivity(), "Points Earned " + reward, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void adClicked(String screenId) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtMessageId.setText("Ad Clicked");
                Toast.makeText(getActivity(), "Ad Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
