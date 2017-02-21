package com.pokkt.demo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.pokkt.PokktAds;
import com.pokkt.demo.utility.FragmentTransactionManager;
import com.pokkt.demo.utility.Storage;
import com.pokkt.demo.fragments.PokktAdTypeFragment;
import com.pokkt.ad.demo.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPokktSDK();

        PokktAdTypeFragment pokktAdTypeFragment = new PokktAdTypeFragment();
        FragmentTransactionManager.addFragmentWithTag(this, R.id.container, pokktAdTypeFragment, PokktAdTypeFragment.class.getSimpleName());
    }

    /**
     * In PokktAds.setPokktConfig()
     * Replace "AppID" and "SecurityKey"  with the one assigned to you by Pokkt.
     */
    private void initPokktSDK() {
        PokktAds.setThirdPartyUserId("123456"); // optional
        PokktAds.Debugging.shouldDebug(this, true); // optional, set it to true if you want to enable logs for PokktSDK
        PokktAds.setPokktConfig(Storage.getAppId(this), Storage.getSecurityKey(this), this); // required
    }

}
