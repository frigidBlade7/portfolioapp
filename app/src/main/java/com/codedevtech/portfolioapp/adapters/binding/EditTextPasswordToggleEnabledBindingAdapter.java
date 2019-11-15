package com.codedevtech.portfolioapp.adapters.binding;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class EditTextPasswordToggleEnabledBindingAdapter {

    private static final String TAG = "EditTextPasswordToggleE";

    @BindingAdapter("app:passwordToggleEnabled")
    public static void setPasswordToggleEnabled(TextInputLayout view, Boolean visibilityToggle) {

        Log.d(TAG, "setPasswordToggleEnabled: "+ visibilityToggle);



        //todo should use the setendiconmode instead of the deprecated toggle methods but aren't working as expected

/*        if(visibilityToggle) {
            view.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            view.setEndIconDrawable(R.drawable.visibility_selector);
        }
        else
            view.setEndIconMode(TextInputLayout.END_ICON_NONE);*/

        view.setPasswordVisibilityToggleEnabled(visibilityToggle);
        view.setPasswordVisibilityToggleDrawable(R.drawable.visibility_selector);
    }
}
