package com.codedevtech.portfolioapp.adapters.pagination;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.models.FeedDocument;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireStoreFeedDocumentPagingAdapter extends FirestorePagingAdapter<FeedPost, FireStoreFeedDocumentPagingAdapter.FeedPostViewHolder> {


    private static final String TAG = "FireStoreFeedDocumentPa";
    private Context context;
    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public FireStoreFeedDocumentPagingAdapter(@NonNull FirestorePagingOptions<FeedPost> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FeedPostViewHolder holder, int position, @NonNull FeedPost model) {

        holder.name.setText(model.getDisplayName());
        holder.caption.setText(model.getCaption());

        //uses glide loader. must be replaced if bucket provider uses a different implementation
        Glide.with(context).load(FirebaseStorage.getInstance().getReference("posts").child(model.getPostImageId()))
                .into(holder.postImage);

        //Glide.with(context).load()

    }

    @NonNull
    @Override
    public FeedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
    }

    public class FeedPostViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePhoto, postImage, like,comment,share;
        private TextView name, caption;


        public FeedPostViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePhoto = itemView.findViewById(R.id.profilePhoto);
            postImage = itemView.findViewById(R.id.postImage);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);
            name = itemView.findViewById(R.id.name);
            caption = itemView.findViewById(R.id.caption);
        }
    }
}
