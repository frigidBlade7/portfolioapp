package com.codedevtech.portfolioapp.adapters.state;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.codedevtech.portfolioapp.fragments.ExploreFragment;
import com.codedevtech.portfolioapp.fragments.FeedFragment;
import com.codedevtech.portfolioapp.fragments.MessagesFragment;
import com.codedevtech.portfolioapp.fragments.ProfileFragment;

public class DashboardAdapter extends FragmentStateAdapter {

    public DashboardAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:

                return new FeedFragment();

            case 1:

                return new ExploreFragment();

            case 2:

                return new MessagesFragment();


            case 3:

                return new ProfileFragment();


        }
        return new FeedFragment();

    }

    @Override
    public int getItemCount() {
        //return total number of pages
        return 4;
    }
}
