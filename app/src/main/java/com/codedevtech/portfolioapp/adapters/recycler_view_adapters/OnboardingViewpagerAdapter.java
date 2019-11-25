package com.codedevtech.portfolioapp.adapters.recycler_view_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.models.OnboardingModel;
import com.codedevtech.portfolioapp.utilities.Utility;

import java.util.Arrays;
import java.util.List;

public class OnboardingViewpagerAdapter extends RecyclerView.Adapter<OnboardingViewpagerAdapter.OnboardingScreenViewHolder> {

    private List<OnboardingModel> onboardingModelList;
    private Context context;

    public OnboardingViewpagerAdapter(List<OnboardingModel> onboardingModelList) {
        this.onboardingModelList = onboardingModelList;
    }

    @NonNull
    @Override
    public OnboardingScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new OnboardingScreenViewHolder(LayoutInflater.from(context).inflate(R.layout.onboarding_screen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingScreenViewHolder holder, int position) {

        holder.titleImage.setImageResource(onboardingModelList.get(position).getImageId());
        holder.titleText.setText(context.getText(onboardingModelList.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return onboardingModelList.size();
    }


    class OnboardingScreenViewHolder extends RecyclerView.ViewHolder{

        ImageView titleImage;
        TextView titleText;

        private OnboardingScreenViewHolder(@NonNull View itemView) {
            super(itemView);

            titleImage = itemView.findViewById(R.id.titleImage);
            titleText = itemView.findViewById(R.id.titleText);

        }
    }
}
