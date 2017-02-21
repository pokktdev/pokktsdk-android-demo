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

import com.pokkt.PokktAds;
import com.pokkt.demo.utility.Storage;
import com.pokkt.ad.demo.R;

public class PokktSettingsFragment extends BaseFragment {

    // ui
    // edt_security_key
    //btn_export_log btn_export_log_to_cloud
    private TextView txtIPAddress;
    private EditText edtIPAddress, edtApplicationID, edtSecurityKey;
    private Button btnExportLog, btnExportLogToCloud;

    public PokktSettingsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // ipAddress
        txtIPAddress = (TextView) findView(rootView, R.id.txt_ip_address);
        edtIPAddress = (EditText) findView(rootView, R.id.edt_ip_address);

        // application id
        edtApplicationID = (EditText) findView(rootView, R.id.edt_application_id);

        // security key
        edtSecurityKey = (EditText) findView(rootView, R.id.edt_security_key);

        // export log
        btnExportLog = (Button) findView(findView(rootView, R.id.btn_export_log), R.id.button);

        // export log to cloud
        btnExportLogToCloud = (Button) findView(findView(rootView, R.id.btn_export_log_to_cloud), R.id.button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup data

        // ipAddress
        if(getResources().getBoolean(R.bool.is_test_release)) {
            setFont(edtIPAddress);
            txtIPAddress.setVisibility(View.VISIBLE); // it is used for our testing purpose please igonre it
            edtIPAddress.setVisibility(View.VISIBLE);
        }

        // application id
        setFont(edtApplicationID);
        edtApplicationID.setText(Storage.getAppId(getActivity()));

        // security key
        setFont(edtSecurityKey);
        edtSecurityKey.setText(Storage.getSecurityKey(getActivity()));

        // export log
        setFont(btnExportLog);
        btnExportLog.setText(getString(R.string.txt_export_log));
        btnExportLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PokktAds.Debugging.exportLog(getActivity()); // if you want to export log to some specific location
            }
        });

        // export log to cloud
        setFont(btnExportLogToCloud);
        btnExportLogToCloud.setText(getString(R.string.txt_export_to_cloud));
        btnExportLogToCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PokktAds.Debugging.exportLogToCloud(getActivity()); // if you want to export log to cloud
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (TextUtils.isEmpty(edtApplicationID.getText())) {
            Toast.makeText(getActivity(), "Please Enter Application Id", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(edtSecurityKey.getText())) {
            Toast.makeText(getActivity(), "Please Enter Security Key", Toast.LENGTH_SHORT).show();
            return;
        }

        // saving applicationID and SecurityKey for SDK locally
        String applicationId = edtApplicationID.getText().toString().trim();
        String securityKey = edtSecurityKey.getText().toString().trim();
        Storage.setAppId(getActivity(), applicationId);
        Storage.setSecurityKey(getActivity(), securityKey);

        // Updating PokktConfig in PokktSDk in case it is changed from this screen.
        PokktAds.setPokktConfig(applicationId, securityKey, getActivity());
    }
}
