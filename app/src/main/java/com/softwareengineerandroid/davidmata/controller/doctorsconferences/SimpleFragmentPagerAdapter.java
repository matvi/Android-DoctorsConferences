package com.softwareengineerandroid.davidmata.controller.doctorsconferences;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by davidmata on 15/10/2016.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ListMainFragment();
            case 1: return new InvitationConferenceFragment();
            case 2: return new SuggestedConferencesFragment();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Conferences";
            case 1:return "Invitation";
            case 2:return "Suggested Conferences";
            default : return super.getPageTitle(position);

        }
       // return super.getPageTitle(position);
    }
}
