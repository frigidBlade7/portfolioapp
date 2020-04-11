package com.codedevtech.portfolioapp.adapters.pagination;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.interfaces.listeners.FeedListener;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.service_implementations.GlideApp;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.storage.FirebaseStorage;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FireStoreFeedDocumentPagingAdapter extends FirestorePagingAdapter<FeedPost, FireStoreFeedDocumentPagingAdapter.FeedPostViewHolder> {


    private static final String TAG = "FireStoreFeedDocumentPa";
    private String userId;
    private Context context;
    private PrettyTime prettyTime;
    private FeedListener feedListener;
    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public FireStoreFeedDocumentPagingAdapter(@NonNull FirestorePagingOptions<FeedPost> options, Fragment fragment, String userId) {
        super(options);
        this.context = fragment.getContext();
        this.prettyTime = new PrettyTime();
        this.userId = userId;
        if(fragment instanceof FeedListener)
            this.feedListener = (FeedListener)fragment;
    }

    @Override
    protected void onBindViewHolder(@NonNull final FeedPostViewHolder holder, int position, @NonNull FeedPost model) {

        if(model.getUserId().equals(userId))
            holder.name.setText(R.string.you);
        else
            holder.name.setText(model.getDisplayName());

        holder.caption.setText(model.getCaption());
        GlideApp.with(context).load(model.getDisplayPhoto())
                .error(R.drawable.man).placeholder(R.drawable.man)
                .circleCrop().thumbnail(0.1f).into(holder.profilePhoto);

        String time = prettyTime.format(model.getCreatedAt());

        if(time.contains("month"))
            holder.timestamp.setText(SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).format(model.getCreatedAt()));
        else
            holder.timestamp.setText(time);

        //uses glide loader. must be replaced if bucket provider uses a different implementation
        if(model.getPostImageId()!=null) {
            GlideApp.with(context).load(FirebaseStorage.getInstance().getReference("posts").child(model.getUserId()+".JPG"))
                    .placeholder(R.color.my_app_field_backdrop)
                    .transform(new CenterCrop(), new RoundedCorners(16)) //maybe use fitcentre?
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            holder.shimmer.stopShimmer();
                            holder.shimmer.hideShimmer();
                            return false;
                        }
                    })
                    .into(holder.postImage);
        }else{
            holder.postImage.setVisibility(View.GONE);
        }
        //Glide.with(context).load()

    }

    @NonNull
    @Override
    public FeedPostViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        final FeedPostViewHolder feedPostViewHolder = new FeedPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));

        feedPostViewHolder.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedListener!=null){
                    FeedPost feedPost = getCurrentList().get(feedPostViewHolder.getAdapterPosition()).toObject(FeedPost.class);
                    feedListener.onFeedProfilePhotoTapped(feedPost);

/*                    if(feedPost.getUserId().equals(userId))
                        //Navigatio
                        //Toast.makeText(context, "gotoprofile", Toast.LENGTH_SHORT).show();
                    else{
                        feedListener.onFeedImageClicked(feedPost);
                    }*/

                }
            }
        });

        feedPostViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedListener!=null) {
                    FeedPost feedPost = getCurrentList().get(feedPostViewHolder.getAdapterPosition()).toObject(FeedPost.class);
                    feedListener.onFeedPostShareTapped(feedPost);
                }
            }
        });

        return feedPostViewHolder;

    }

    public class FeedPostViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePhoto, postImage, like,comment,share;
        private TextView name, caption, timestamp;
        private ShimmerFrameLayout shimmer;


        public FeedPostViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePhoto = itemView.findViewById(R.id.profilePhoto);
            postImage = itemView.findViewById(R.id.postImage);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);
            name = itemView.findViewById(R.id.name);
            caption = itemView.findViewById(R.id.caption);
            shimmer = itemView.findViewById(R.id.postImageShimmer);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }
}
