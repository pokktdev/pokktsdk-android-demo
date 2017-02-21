package com.pokkt.demo.utility;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

/**
 * Created by abhinav-rathore on 29-04-2015.
 */
public class FragmentTransactionManager {

    public static void addFragmentWithTag(Activity mAct, int containerID, Fragment frag, String fragmentTag) {
        FragmentManager fragmentManager = mAct.getFragmentManager();
        fragmentManager.beginTransaction()
                .add(containerID, frag, fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss();
    }

    public static void addFragmentWithoutTag(Activity mAct, int containerID, Fragment frag) {
        FragmentManager fragmentManager = mAct.getFragmentManager();
        fragmentManager.beginTransaction()
                .add(containerID, frag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public static void replaceFragmentWithTag(Activity mAct, int containerID, Fragment frag, String fragmentTag) {
        FragmentManager fragmentManager = mAct.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
        fragmentManager.beginTransaction()
                .replace(containerID, frag, fragmentTag)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss();
    }

    public static void replaceFragmentWithoutTag(Activity mAct, int containerID, Fragment frag) {
        FragmentManager fragmentManager = mAct.getFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(containerID, frag)
                .commitAllowingStateLoss();;
    }

    public static void popFragmentThroughId(Activity mAct, int containerID, Fragment frag) {
        FragmentManager fragmentManager = mAct.getFragmentManager();
        fragmentManager.popBackStack(containerID, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void popFragmentThroughTag(Activity mAct, String fragmentName, Fragment frag) {
        FragmentManager fragmentManager = mAct.getFragmentManager();
        fragmentManager.popBackStack(fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    public static Fragment getFragmentThroughTag(Activity mAct, String fragmentTag) {
        Fragment fragmentInstance = mAct.getFragmentManager().findFragmentByTag(fragmentTag);
        return fragmentInstance;
    }


    public static Fragment getActiveFragment(Context context, int containerID) {
        return ((Activity) context).getFragmentManager().findFragmentById(containerID);

    }

    public  static void removeAllFragments(Context context){
        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        int backStackCount =fragmentManager.getBackStackEntryCount();
        for(int i = backStackCount ; i > 0; i--){
            fragmentManager.popBackStackImmediate();
        }

    }
}
