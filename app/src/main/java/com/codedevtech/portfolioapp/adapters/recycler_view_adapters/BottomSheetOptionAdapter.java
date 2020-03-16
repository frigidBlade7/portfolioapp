package com.codedevtech.portfolioapp.adapters.recycler_view_adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.interfaces.listeners.BottomSheetOptionListener;

import java.util.List;


public class BottomSheetOptionAdapter extends RecyclerView.Adapter<BottomSheetOptionAdapter.ViewHolder> {

    private List<String> optionTextList;
    private List<Integer> optionDrawableList;
    private BottomSheetOptionListener listener;

    public BottomSheetOptionAdapter(List<String> optionTextList, List<Integer> optionDrawableList, BottomSheetOptionListener listener) {
        this.optionTextList = optionTextList;
        this.optionDrawableList = optionDrawableList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.options_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.onOptionClicked(viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.text.setText(optionTextList.get(position));
        holder.icon.setImageDrawable(holder.itemView.getContext().getDrawable(optionDrawableList.get(position)));
    }

    @Override
    public int getItemCount() {
        return optionTextList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView icon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}

