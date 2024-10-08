package com.example.tawfiqthefooddonationapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPagerMessengerAdapter extends FragmentPagerAdapter {


    public ViewPagerMessengerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new MediaFragment();
            case 2:
                return new CharityFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3; // Number of tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Media";
            case 2:
                return "Charities";
            default:
                return null;
        }
    }
}
