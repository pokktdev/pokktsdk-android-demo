package com.pokkt.demo.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.pokkt.ad.demo.R;
import com.pokkt.demo.utility.FragmentTransactionManager;

public class PokktNativeAdFragment extends BaseFragment {

    private Button btnAdTypeNativeScroll, btnAdTypeNativeList, btnAdTypeNativeWebView;
    public static boolean isAllowClick = true;
    private TextView txtScreenId;
    private EditText edtScreenId;
    public PokktNativeAdFragment() {
    }
    private String nativeVideoScreenId = "684ab1e66abeb060faa500136c4c6a74";
    private String nativeDisplayScreenId = "5e59028c8332c9583e742c183abbaafb";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isAllowClick = true;
        View rootView = inflater.inflate(R.layout.fragment_pokkt_native_ad_type, container, false);


        // screen name
        txtScreenId = (TextView) findView(rootView, R.id.txt_screen_id);
        edtScreenId = (EditText) findView(rootView, R.id.edt_screen_id);
        edtScreenId.setText(nativeDisplayScreenId);

        //Native Scroll
        btnAdTypeNativeScroll = (Button) findView(findView(rootView, R.id.btn_ad_type_scroll), R.id.button);

        //Native listview
        btnAdTypeNativeList = (Button) findView(findView(rootView, R.id.btn_ad_type_list), R.id.button);

        //OS webview
        btnAdTypeNativeWebView = (Button) findView(findView(rootView, R.id.btn_ad_type_webview), R.id.button);

        Toolbar toolbar = rootView.findViewById(R.id.pokkttoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
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

        setFont(txtScreenId);

        // setup data

        //OS Scroll
        btnAdTypeNativeScroll.setText(getString(R.string.txt_btn_ad_type_scroll));
        setFont(btnAdTypeNativeScroll);
        btnAdTypeNativeScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screenId = edtScreenId.getText().toString().trim();
                if(isScreenIdSpecified()) {
                    if (isAllowClick) {
                        isAllowClick = false;
                        openNativeScrollShowcase(screenId);
                    }
                }
            }
        });
        //OS listview
        btnAdTypeNativeList.setText(getString(R.string.txt_btn_ad_type_list));
        setFont(btnAdTypeNativeList);
        btnAdTypeNativeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screenId = edtScreenId.getText().toString().trim();
                if(isScreenIdSpecified()) {
                    if (isAllowClick) {
                        isAllowClick = false;
                        openNativeListViewShowcase(screenId);
                    }
                }
            }
        });
        //OS webview
        btnAdTypeNativeWebView.setText(getString(R.string.txt_btn_ad_type_webview));
        setFont(btnAdTypeNativeWebView);
        btnAdTypeNativeWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screenId = edtScreenId.getText().toString().trim();
                if(isScreenIdSpecified()) {
                    if (isAllowClick) {
                        isAllowClick = false;
                        openNativeWebViewShowcase(screenId);
                    }
                }
            }
        });
    }

    private boolean isScreenIdSpecified() {
        if (TextUtils.isEmpty(edtScreenId.getText())) {
            Toast.makeText(getActivity(), "Please Enter Screen ID", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void openNativeScrollShowcase(String screenId) {
        PokktNativeScrollFragment fragment = PokktNativeScrollFragment.newInstance(screenId);
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktNativeScrollFragment.class.getSimpleName());
    }

    private void openNativeWebViewShowcase(String screenId) {
        PokktNativeWebViewFragment fragment = PokktNativeWebViewFragment.newInstance(screenId);
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktNativeWebViewFragment.class.getSimpleName());
    }

    private void openNativeListViewShowcase(String screenId) {
        PokktNativeListViewFragment fragment = PokktNativeListViewFragment.newInstance(screenId);
        FragmentTransactionManager.addFragmentWithTag(getActivity(), R.id.container, fragment, PokktNativeListViewFragment.class.getSimpleName());
    }
}
