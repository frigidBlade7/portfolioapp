package com.codedevtech.portfolioapp.adapters.pagination;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedevtech.portfolioapp.models.FeedDocument;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class FireStoreFeedDocumentPagingAdapter extends FirestorePagingAdapter<FeedDocument, FireStoreFeedDocumentPagingAdapter.FeedPostViewHolder> {


    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public FireStoreFeedDocumentPagingAdapter(@NonNull FirestorePagingOptions<FeedDocument> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedPostViewHolder holder, int position, @NonNull FeedDocument model) {

    }

    @NonNull
    @Override
    public FeedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class FeedPostViewHolder extends RecyclerView.ViewHolder {



        public FeedPostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
