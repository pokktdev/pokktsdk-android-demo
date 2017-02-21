package com;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by abhinav-rathore on 14-Jun-16 at 2:13 PM.
 */
public class PokktApplication extends MultiDexApplication{


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
