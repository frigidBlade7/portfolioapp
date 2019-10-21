package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.adapters.recycler_view_adapters.OnboardingViewpagerAdapter;
import com.codedevtech.portfolioapp.databinding.FragmentOnboardingBinding;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnboardingFragment extends Fragment {

    private static final String TAG = "OnboardingFragment";

    public OnboardingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final FragmentOnboardingBinding fragmentOnboardingBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_onboarding, container, false);

        fragmentOnboardingBinding.setLifecycleOwner(this);

        //set adapter for viewpager2
        fragmentOnboardingBinding.viewPager.setAdapter(new OnboardingViewpagerAdapter(Utility.onboardingModelList));

        //add a tablayoutmediator (just checking tab positions)
        new TabLayoutMediator(fragmentOnboardingBinding.tabLayout,
                fragmentOnboardingBinding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d(TAG, "onConfigureTab: tabPos"+position);
            }
        }).attach();

        //add a transformer animation to viewpager
        fragmentOnboardingBinding.viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                int pageWidth = page.getWidth();
                float MIN_ALPHA = 0.5f;
                float MIN_SCALE = 0.1f;

                float scaleFactor = Math.max(0.1f, 1 - Math.abs(position));

                TextView description = page.findViewById(R.id.titleText);
                ImageView illustration = page.findViewById(R.id.titleImage);

                if (position < -1) { // [-Infinity,-1)
                    // This page is off-screen (on your left! <Cap America voice>)
                    page.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    description.setTranslationX(position * (pageWidth * 1.5f)); //1.5x the normal speed
                    illustration.setScaleX(scaleFactor);
                    illustration.setScaleY(scaleFactor);
                    illustration.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                }else{
                    // This page is off-screen (to the right)
                    page.setAlpha(1);
                }
            }
        });


        return fragmentOnboardingBinding.getRoot();
    }

}
