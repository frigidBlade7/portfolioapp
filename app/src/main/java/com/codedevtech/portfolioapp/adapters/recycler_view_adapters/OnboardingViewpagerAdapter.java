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

import java.util.List;

public class OnboardingViewpagerAdapter extends RecyclerView.Adapter<OnboardingViewpagerAdapter.OnboardingScreenViewHolder> {

    private List<Integer> imageReference;
    private  List<Integer> textReference;
    private Context context;

    @NonNull
    @Override
    public OnboardingScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new OnboardingScreenViewHolder(LayoutInflater.from(context).inflate(R.layout.onboarding_screen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingScreenViewHolder holder, int position) {

        holder.titleImage.setImageResource(imageReference.get(position));
        holder.titleText.setText(context.getText(textReference.get(position)));
    }

    @Override
    public int getItemCount() {
        return textReference.size();
    }


    class OnboardingScreenViewHolder extends RecyclerView.ViewHolder{

        ImageView titleImage;
        TextView titleText;

        public OnboardingScreenViewHolder(@NonNull View itemView) {
            super(itemView);

            titleImage = itemView.findViewById(R.id.titleImage);
            titleText = itemView.findViewById(R.id.titleText);

        }
    }
}
