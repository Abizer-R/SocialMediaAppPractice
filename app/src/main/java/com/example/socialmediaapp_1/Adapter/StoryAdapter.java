package com.example.socialmediaapp_1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp_1.Models.Story;
import com.example.socialmediaapp_1.R;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder>{

    private List<Story> stories;

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.story_rv_item, parent, false);
        StoryViewHolder viewHolder = new StoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story currStory = stories.get(position);
        holder.profileImage.setImageResource(currStory.getProfileImage());
        holder.profileName.setText(currStory.getProfileName());
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
        notifyDataSetChanged(); // TODO: change this to something better
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView profileName;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.story_rv_profile_image);
            profileName = itemView.findViewById(R.id.story_rv_profile_name);
        }
    }
}
