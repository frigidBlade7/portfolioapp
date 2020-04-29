package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
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
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.codedevtech.portfolioapp.viewmodels.OnboardingFragmentViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnboardingFragment extends Fragment implements Injectable {

    private static final String TAG = "OnboardingFragment";

    @Inject
    ViewModelProvider.Factory viewmodelFactory;

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

        final OnboardingFragmentViewModel onboardingFragmentViewModel = new ViewModelProvider(this, viewmodelFactory)
                .get(OnboardingFragmentViewModel.class);

        fragmentOnboardingBinding.setOnboardingFragmentViewModel(onboardingFragmentViewModel);

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

        onboardingFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(OnboardingFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(OnboardingFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(OnboardingFragment.this).navigate(navigationId);

                }
            }
        }));


        return fragmentOnboardingBinding.getRoot();
    }

}
