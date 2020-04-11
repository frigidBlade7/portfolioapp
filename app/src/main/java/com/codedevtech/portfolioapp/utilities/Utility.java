package com.codedevtech.portfolioapp.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentActivity;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.models.OnboardingModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Utility {

    public static final int[] imageRes = new int[]{R.drawable.profile ,R.drawable.meet, R.drawable.communicate};

    public static final int[] descriptionRes = new int[]{R.string.profile_text, R.string.meet, R.string.communicate};


    public static final List<OnboardingModel> onboardingModelList = Arrays.asList(
            new OnboardingModel(descriptionRes[0], imageRes[0]),
            new OnboardingModel(descriptionRes[1], imageRes[1]),
            new OnboardingModel(descriptionRes[2], imageRes[2]));

    public static final String USER_AUTH_ID = "userAuthId";


    public static void showSoftKeyboard(View view, Activity activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null)
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void hideSoftKeyboard(View view, Activity activity) {

        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static String getShareProfileTitle(String firstName) {
        if (firstName.endsWith("s"))
            return String.format("%1$s' profile on folio.", firstName);
        else
            return String.format("%1$s's profile on folio.", firstName);
    }

    public static String getSharePostTitle(String displayName) {
        if (displayName.endsWith("s"))
            return String.format("%1$s' post on folio.", displayName);
        else
            return String.format("%1$s's post on folio.", displayName);
    }
}
