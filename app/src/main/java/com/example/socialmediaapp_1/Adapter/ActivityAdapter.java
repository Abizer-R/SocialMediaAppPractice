package com.example.socialmediaapp_1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp_1.Models.ActivityModel;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.Utils.GeneralUtils;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<ActivityModel> activityList;
    private Context mcontext;

    public ActivityAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_rv_item, parent, false);
        ActivityViewHolder activityViewHolder = new ActivityViewHolder(view);
        return activityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {

        ActivityModel currActivity = activityList.get(position);

        int activityType = currActivity.getActivityType();

        hideViews(holder, currActivity.getActivityType());
        setViews(holder, currActivity);
    }

    public void hideViews(ActivityViewHolder holder, int activityType) {

        if(activityType != 5) {
            holder.confirmBtn.setVisibility(View.GONE);
            holder.deleteBtn.setVisibility(View.GONE);
        }
        switch (activityType) {

            case 3:
                holder.confirmBtn.setVisibility(View.GONE);
                holder.deleteBtn.setVisibility(View.GONE);
                holder.activityImage.setVisibility(View.GONE);
                break;

            case 4:
                holder.confirmBtn.setVisibility(View.GONE);
                holder.deleteBtn.setVisibility(View.GONE);
                break;

            case 5:
                holder.activityImage.setVisibility(View.GONE);
                break;

            case 6:
                holder.profileImage.setVisibility(View.GONE);
                holder.activityImage.setVisibility(View.GONE);
                holder.confirmBtn.setVisibility(View.GONE);
                holder.deleteBtn.setVisibility(View.GONE);
                break;

            // For cases 1 and 2
            default:
                holder.confirmBtn.setVisibility(View.GONE);
                holder.deleteBtn.setVisibility(View.GONE);
                break;

        }
    }

    public void setViews(ActivityViewHolder holder, ActivityModel currActivity) {
        if(currActivity.getActivityType() == 6) {
            // SetTextView according to time
            holder.activityText.setText(mcontext.getString(R.string.tv_today));
            return;
        }

        if(holder.profileImage.getVisibility() != View.GONE)
            holder.profileImage.setImageResource(currActivity.getProfileImage());

        if(holder.activityImage.getVisibility() != View.GONE)
            holder.activityImage.setImageResource(currActivity.getStoryPostImage());

        String profileName = currActivity.getProfileName();
        String timestamp = GeneralUtils.mapTimestamp(currActivity.getTimestamp());

        StringBuilder stringBuilder = new StringBuilder("");
        switch (currActivity.getActivityType()) {
            case 1:
                stringBuilder.append(mcontext.getString(R.string.notification_liked_story));
                break;
            case 2:
                stringBuilder.append(mcontext.getString(R.string.notification_liked_post));
                break;
            case 3:
                stringBuilder.append(mcontext.getString(R.string.notification_started_following));
                break;
            case 4:
                stringBuilder.append(mcontext.getString(R.string.notification_commented))
                    .append(": ")
                    .append(currActivity.getActivityText());
                break;
            case 5:
                stringBuilder.append(mcontext.getString(R.string.notification_follow_request));
                break;
        }

        holder.activityText.setText(GeneralUtils.getCaption(profileName, stringBuilder.toString(), timestamp));
    }


    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void setActivityList(List<ActivityModel> activityList) {
        this.activityList = activityList;
        notifyDataSetChanged();  // TODO: change this to something better
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView activityText;
        private ImageView activityImage;
        private Button confirmBtn;
        private Button deleteBtn;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.activity_rv_profile_image);
            activityText = itemView.findViewById(R.id.activity_rv_textview);
            activityImage = itemView.findViewById(R.id.activity_rv_imageview);
            confirmBtn = itemView.findViewById(R.id.activity_rv_confirm_btn);
            deleteBtn = itemView.findViewById(R.id.activity_rv_delete_btn);
        }
    }
}
