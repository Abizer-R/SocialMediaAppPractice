package com.example.socialmediaapp_1.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp_1.Models.FeedPostModel;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.Utils.GeneralUtils;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<FeedPostModel> feedPosts;
    private Context mcontext;

    public FeedAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feed_rv_item, parent, false);
        FeedViewHolder feedViewHolder = new FeedViewHolder(view);
        return feedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        FeedPostModel currPost = feedPosts.get(position);

        holder.profileImage.setImageResource(currPost.getProfileImage());
        holder.profileName.setText(currPost.getProfileName());
        holder.postImage.setImageResource(currPost.getPostImage());
        holder.likeCount.setText("" + currPost.getLikeCount());
        holder.caption.setText(GeneralUtils.getCaption(currPost.getProfileName(), currPost.getCaption()));

        if(currPost.getCommentCount() == 0)
            holder.viewComments.setVisibility(View.GONE);
        else {
            String commentString = "View all " + currPost.getCommentCount() + " comments";
            holder.viewComments.setText(commentString);
        }
        if(currPost.isBookmarked())
            holder.bookmark.setImageDrawable(mcontext.getDrawable(R.drawable.ic_post_bookmarked));
        if(currPost.isLiked())
            holder.likeBtn.setImageDrawable(mcontext.getDrawable(R.drawable.ic_post_liked_24));

    }

    @Override
    public int getItemCount() {
        return feedPosts.size();
    }

    public void setFeedPosts(List<FeedPostModel> feedPosts) {
        this.feedPosts = feedPosts;
        notifyDataSetChanged();  // TODO: change this to something better
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private ImageView postImage;
        private ImageView bookmark;
        private ImageView likeBtn;
        private TextView profileName;
        private TextView likeCount;
        private TextView caption;
        private TextView viewComments;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.feed_rv_profile_image);
            profileName = itemView.findViewById(R.id.feed_rv_profile_name);
            postImage = itemView.findViewById(R.id.feed_rv_post_image);
            likeBtn = itemView.findViewById(R.id.feed_rv_like_btn);
            likeCount = itemView.findViewById(R.id.feed_rv_like_count);
            caption = itemView.findViewById(R.id.feed_rv_caption);
            viewComments = itemView.findViewById(R.id.feed_rv_view_all_comment);
            bookmark = itemView.findViewById(R.id.feed_rv_bookmark_btn);
        }
    }
}
