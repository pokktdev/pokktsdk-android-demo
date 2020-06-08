package com.pokkt.demo.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pokkt.ad.demo.R;
import com.pokkt.sdk.pokktnativead.PokktNativeAd;

/**
 * Created by abhin on 20-01-2017 at 14:15.
 */

public class BaseFragment extends Fragment{
    protected PokktNativeAd pokktNativeAd;

    public BaseFragment() {
    }

    /**
     * Utility method used by child fragments to set custom font to UI elements
     * @param view
     */
    protected void setFont(View view) {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "BankGothic Bold.ttf");
        if (view instanceof Button) {
            ((Button) view).setTypeface(typeface);
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(typeface);
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(typeface);
        }
    }

    /**
     * Utility used by child fragments to find view from  view group
     * @param rootView
     * @param viewID
     * @return
     */
    protected View findView(View rootView, int viewID) {
        return rootView.findViewById(viewID);
    }

    protected void setProgressbarColor(ProgressBar progressbar){

        progressbar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.clr_blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}
