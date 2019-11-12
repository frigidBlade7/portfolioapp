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
        //Check if password has text in it

/*        Log.d(TAG, "setPasswordToggleEnabled: "+ password.getValue());
        if(password.getValue()== null)
            return;

        if(password.getValue().length()>0) {
            view.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            view.setEndIconDrawable(R.drawable.visibility_selector);
        }
        else
            view.setEndIconMode(TextInputLayout.END_ICON_NONE);*/

        Log.d(TAG, "setPasswordToggleEnabled: "+ visibilityToggle);


        if(visibilityToggle) {
            view.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            view.setEndIconDrawable(R.drawable.visibility_selector);
        }
        else
            view.setEndIconMode(TextInputLayout.END_ICON_NONE);

    }
}
