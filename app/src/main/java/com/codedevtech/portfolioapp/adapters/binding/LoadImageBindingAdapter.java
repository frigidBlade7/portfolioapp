package com.codedevtech.portfolioapp.adapters.binding;

import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.service_implementations.GlideApp;
import com.google.firebase.storage.FirebaseStorage;

public class LoadImageBindingAdapter {

    private static final String TAG = "LoadImageBindingAdapter";

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String userId){
        Log.d(TAG, "loadImage: "+userId);
        GlideApp.with(imageView.getContext()).load(userId)
                .placeholder(R.color.my_app_field_backdrop).error(R.drawable.man).placeholder(R.drawable.man)
                .transform(new CenterCrop(),new RoundedCorners(16))
        .into(imageView);
    }
}
