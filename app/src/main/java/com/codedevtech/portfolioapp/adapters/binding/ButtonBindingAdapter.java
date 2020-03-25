package com.codedevtech.portfolioapp.adapters.binding;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.service_implementations.GlideApp;
import com.google.android.material.button.MaterialButton;

public class ButtonBindingAdapter {

    private static final String TAG = "ButtonBindingAdapter";

    @BindingAdapter("buttonStyle")
    public static void updateStyle(MaterialButton button, boolean isFollowing){
        Log.d(TAG, "updateStyle: "+isFollowing);
    }
}
