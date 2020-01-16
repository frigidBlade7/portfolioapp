package com.codedevtech.portfolioapp.utilities;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.models.OnboardingModel;

import java.util.Arrays;
import java.util.List;

public class Utility {

    public static final int[] imageRes = new int[]{R.drawable.profile ,R.drawable.meet, R.drawable.communicate};

    public static final int[] descriptionRes = new int[]{R.string.profile_text, R.string.meet, R.string.communicate};


    public static final List<OnboardingModel> onboardingModelList = Arrays.asList(
            new OnboardingModel(descriptionRes[0], imageRes[0]),
            new OnboardingModel(descriptionRes[1], imageRes[1]),
            new OnboardingModel(descriptionRes[2], imageRes[2]));

    public static final String USER_AUTH_ID = "userAuthId";



}
