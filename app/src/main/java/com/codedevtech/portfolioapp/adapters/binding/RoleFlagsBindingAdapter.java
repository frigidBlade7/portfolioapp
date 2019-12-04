package com.codedevtech.portfolioapp.adapters.binding;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.util.StringUtil;

import com.codedevtech.portfolioapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class RoleFlagsBindingAdapter {


    private static final String TAG = "RoleFlagsBindingAdapter";

    @BindingAdapter("app:roleFlags")
    public static void setRoleFlags(TextView view, List<String> roleFlags) {

        Log.d(TAG, "setRoleFlags: "+roleFlags);

        if(roleFlags==null)
            return;
        view.setText(TextUtils.join(" | ", roleFlags.toArray(new String[0])));
    }
}
