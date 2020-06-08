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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pokkt.PokktAds;
import com.pokkt.ad.demo.R;
import com.pokkt.sdk.pokktnativead.PokktNativeAd;

import java.util.ArrayList;
import java.util.List;

public class PokktNativeListViewFragment extends BaseFragment implements PokktAds.NativeAdsDelegate {

    private String screenID = "684ab1e66abeb060faa500136c4c6a74";
    private List<PokktNativeAd> nativeAds = new ArrayList<>();
    private ArrayList<Object> content = new ArrayList<>();
    private PokktNativeRecyclerViewAdapter adapter;

    public PokktNativeListViewFragment() {
    }

    private static final String ARG_SCREEN_ID = "screenId";

    static PokktNativeListViewFragment newInstance(String screenId) {
        PokktNativeListViewFragment fragment = new PokktNativeListViewFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_pokkt_native_list_ad_type, container, false);

        content = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            content.add("List Item Content : " + i);
        }
        RecyclerView recyclerView = rootView.findViewById(R.id.pokktoslist);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PokktNativeRecyclerViewAdapter(getActivity(), content);
        recyclerView.setAdapter(adapter);
        recyclerView.setContentDescription("test_listView");
        Toolbar toolbar = rootView.findViewById(R.id.pokkttoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PokktNativeListViewFragment.this.getActivity() != null) {
                    PokktNativeListViewFragment.this.getActivity().onBackPressed();
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
    public void adReady(String screenId, PokktNativeAd pokktNativeAd) {
        this.pokktNativeAd = pokktNativeAd;
        nativeAds.add(pokktNativeAd);
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PokktNativeListViewFragment.this.getActivity(), "Ad Ready ", Toast.LENGTH_SHORT).show();
            }
        });
        if (nativeAds.size() <= 0) {
            return;
        }

        int offset = (content.size() / nativeAds.size()) + 1;
        int index = 5;
        for (PokktNativeAd ad : nativeAds) {
            content.add(index, ad);
            index = index + offset;
        }
        adapter.notifyDataSetChanged();
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
                    Toast.makeText(PokktNativeListViewFragment.this.getActivity(), "Ad Displayed ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PokktNativeListViewFragment.this.getActivity(), "Ad Show Error " + errorMessage, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(PokktNativeListViewFragment.this.getActivity(), "Ad Completed ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PokktNativeListViewFragment.this.getActivity(), "Ad Skipped ", Toast.LENGTH_SHORT).show();
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