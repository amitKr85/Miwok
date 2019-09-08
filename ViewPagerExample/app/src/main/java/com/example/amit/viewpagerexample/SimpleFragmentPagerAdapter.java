package com.example.amit.viewpagerexample;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by AMIT on 21-03-2018.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    Context context;
    public SimpleFragmentPagerAdapter(Context pcontext, android.support.v4.app.FragmentManager fm) {
        super(fm);
        context=pcontext;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new NumbersFragment();
            case 1: return new FamilyFragment();
            case 2: return new ColorsFragment();
            case 3: return new PhrasesFragment();
            default:    return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return context.getString(R.string.category_numbers);
            case 1: return context.getString(R.string.category_family);
            case 2: return context.getString(R.string.category_colors);
            case 3: return context.getString(R.string.category_phrases);
            default:    return null;
        }
    }
}
