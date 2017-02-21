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
import com.pokkt.ad.demo.R;
import com.pokkt.demo.utility.Storage;

public class PokktVideoFragment extends BaseFragment implements PokktAds.VideoAd.VideoAdDelegate {

    // ui
    private TextView txtAdType, txtScreenName;
    private EditText edtScreenName;
    private ProgressBar progressCacheRewarded, progressShowRewarded, progressCacheNonRewarded, progressShowNonRewarded;
    private Button btnCacheRewarded, btnShowRewarded, btnCacheNonRewarded, btnShowNonRewarded;
    private TextView txtEarnedPoints;

    public PokktVideoFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_pokkt_video, container, false);

        // ad type heading
        txtAdType = (TextView) findView(rootView, R.id.txt_ad_type);

        // screen name
        txtScreenName = (TextView) findView(rootView, R.id.txt_screen_name);
        edtScreenName = (EditText) findView(rootView, R.id.edt_screen_name);

        // earned points
        txtEarnedPoints = (TextView) findView(rootView, R.id.txt_earned_points);

        // cache rewarded
        progressCacheRewarded = (ProgressBar) findView(findView(rootView, R.id.btn_cache_rewarded), R.id.progressBar);
        btnCacheRewarded = (Button) findView(findView(rootView, R.id.btn_cache_rewarded), R.id.button);

        // show rewarded
        progressShowRewarded = (ProgressBar) findView(findView(rootView, R.id.btn_show_rewarded), R.id.progressBar);
        btnShowRewarded = (Button) findView(findView(rootView, R.id.btn_show_rewarded), R.id.button);

        // cache non rewarded
        progressCacheNonRewarded = (ProgressBar) findView(findView(rootView, R.id.btn_cache_non_rewarded), R.id.progressBar);
        btnCacheNonRewarded = (Button) findView(findView(rootView, R.id.btn_cache_non_rewarded), R.id.button);

        // show non rewarded
        progressShowNonRewarded = (ProgressBar) findView(findView(rootView, R.id.btn_show_non_rewarded), R.id.progressBar);
        btnShowNonRewarded = (Button) findView(findView(rootView, R.id.btn_show_non_rewarded), R.id.button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup data

        // ad Type
        setFont(txtAdType);

        // screen name
        //setFont(edtScreenName);
        setFont(txtScreenName);

        // earned points
        txtEarnedPoints.setText(String.format(getString(R.string.txt_earned_points), String.valueOf(Storage.getVideoPoints(getActivity()))));
        setFont(txtEarnedPoints);

        // cache rewarded
        setFont(btnCacheRewarded);
        setProgressbarColor(progressCacheRewarded);
        btnCacheRewarded.setText(getString(R.string.txt_btn_cache_rewarded));
        btnCacheRewarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressCacheRewarded.setVisibility(View.VISIBLE);
                if (isScreenNameSpecified()) {
                    PokktAds.VideoAd.cacheRewarded(edtScreenName.getText().toString().trim());
                }
            }
        });

        // show rewarded
        setFont(btnShowRewarded);
        setProgressbarColor(progressShowRewarded);
        btnShowRewarded.setText(getString(R.string.txt_btn_show_rewarded));
        btnShowRewarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressShowRewarded.setVisibility(View.VISIBLE);
                if (isScreenNameSpecified()) {
                    PokktAds.VideoAd.showRewarded(edtScreenName.getText().toString().trim());
                }
            }
        });

        // cache non rewarded
        setFont(btnCacheNonRewarded);
        setProgressbarColor(progressCacheNonRewarded);
        btnCacheNonRewarded.setText(getString(R.string.txt_btn_cache_non_rewarded));
        btnCacheNonRewarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressCacheNonRewarded.setVisibility(View.VISIBLE);
                if (isScreenNameSpecified()) {
                    PokktAds.VideoAd.cacheNonRewarded(edtScreenName.getText().toString().trim());
                }
            }
        });

        // show non rewarded
        setFont(btnShowNonRewarded);
        setProgressbarColor(progressShowNonRewarded);
        btnShowNonRewarded.setText(getString(R.string.txt_btn_show_non_rewarded));
        btnShowNonRewarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressShowNonRewarded.setVisibility(View.VISIBLE);
                if (isScreenNameSpecified()) {
                    PokktAds.VideoAd.showNonRewarded(edtScreenName.getText().toString().trim());
                }
            }
        });

        // OPTIONAL but we SUGGEST you to implement callbacks as it will help you to determine the status of your request
        PokktAds.VideoAd.setDelegate(this);
    }


    private boolean isScreenNameSpecified() {
        if (TextUtils.isEmpty(edtScreenName.getText())) {
            Toast.makeText(getActivity(), "Please Enter Screen Name or Zone ID", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void hideProgress() {
        progressCacheRewarded.setVisibility(View.GONE);
        progressShowRewarded.setVisibility(View.GONE);
        progressCacheNonRewarded.setVisibility(View.GONE);
        progressShowNonRewarded.setVisibility(View.GONE);
    }

    // Video Ad CallBacks

    @Override
    public void videoAdCachingCompleted(final String screenName, boolean isRewarded, final double reward) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Video Ad Caching Completed ! Ad vc is: " + reward, Toast.LENGTH_LONG).show();
                if (edtScreenName.getText().toString().trim().equals(screenName)) { // screen name check as our SDK supports multiple ad-requests,
                    // it will help in identifying response to which request has arrived
                    hideProgress();
                }
            }
        });
    }

    @Override
    public void videoAdCachingFailed(final String screenName, boolean isRewarded, final String errorMessage) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Video Ad Download Error " + errorMessage, Toast.LENGTH_LONG).show();
                if (edtScreenName.getText().toString().trim().equals(screenName)) {
                    hideProgress();
                }
            }
        });
    }

    @Override
    public void videoAdDisplayed(String screenName, boolean isRewarded) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Video Ad Displayed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void videoAdFailedToShow(String screenName, boolean isRewarded, final String errorMessage) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Video Ad Show Error " + errorMessage, Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    @Override
    public void videoAdClosed(String screenName, boolean isRewarded) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                Toast.makeText(getActivity(), "Video Ad Closed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void videoAdSkipped(String screenName, boolean isRewarded) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
        hideProgress();
        Toast.makeText(getActivity(), "Video Ad Skipped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void videoAdCompleted(String screenName, boolean isRewarded) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                Toast.makeText(getActivity(), "Video Ad Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void videoAdGratified(String screenName, boolean isRewarded, final double reward) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Points Earned " + reward, Toast.LENGTH_SHORT).show();
                Storage.storeVideoPoints(getActivity(), (float) reward);
                float val = Storage.getVideoPoints(getActivity());
                txtEarnedPoints.setText(String.format(getString(R.string.txt_earned_points), String.valueOf(val)));
            }
        });
    }

    @Override
    public void videoAdAvailabilityStatus(String screenName, boolean isRewarded, final boolean availability) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (availability) {
                    Toast.makeText(getActivity(), "Video Ad is Available", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Video Ad is not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
