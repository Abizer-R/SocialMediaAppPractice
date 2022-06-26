package com.example.socialmediaapp_1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp_1.Adapter.FeedAdapter;
import com.example.socialmediaapp_1.Adapter.StoryAdapter;
import com.example.socialmediaapp_1.Models.FeedPost;
import com.example.socialmediaapp_1.Models.Story;
import com.example.socialmediaapp_1.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private StoryAdapter storyAdapter;
    private List<Story> stories;

    private FeedAdapter feedAdapter;
    private List<FeedPost> feedPosts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setStoryRecyclerView(view);
        setStoryAdapter();  //TODO: change put real thing into story Adapter

        HorizontalScrollView storyPanelSV = view.findViewById(R.id.horizontal_sv);
        storyPanelSV.scrollTo(0, 0);

        setfeedRecyclerView(view);
        setFeedAdapter();  //TODO: change put real thing into story Adapter

        return view;
    }

    private void setStoryRecyclerView(View view) {
        RecyclerView storyRecyclerView = view.findViewById(R.id.story_rv);
        storyRecyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        storyRecyclerView.setHasFixedSize(true);

        storyAdapter = new StoryAdapter();
        storyRecyclerView.setAdapter(storyAdapter);
    }

    private void setStoryAdapter() {

        stories = new ArrayList<>();

        stories.add(new Story("Abizer1", R.drawable.placeholder_profile, false));
        stories.add(new Story("Abizer2", R.drawable.placeholder_profile, false));
        stories.add(new Story("Abizer3", R.drawable.placeholder_profile, false));
        stories.add(new Story("Abizer4", R.drawable.placeholder_profile, false));
        stories.add(new Story("Abizer5", R.drawable.placeholder_profile, false));
        stories.add(new Story("Abizer6", R.drawable.placeholder_profile, false));

        storyAdapter.setStories(stories);

    }

    private void setfeedRecyclerView(View view) {
        RecyclerView feedRecyclerView = view.findViewById(R.id.feed_rv);
        feedRecyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        feedRecyclerView.setHasFixedSize(true);

        feedAdapter = new FeedAdapter(view.getContext());
        feedRecyclerView.setAdapter(feedAdapter);

    }

    private void setFeedAdapter() {

        feedPosts = new ArrayList<>();

        feedPosts.add(new FeedPost("Abizer1", "yo boiii", R.drawable.placeholder_profile, R.drawable.img1, 23, 0, false, true));
        feedPosts.add(new FeedPost("Abizer2", "222222", R.drawable.placeholder_profile, R.drawable.img2, 0, 3, true, false));
        feedPosts.add(new FeedPost("Abizer3", "333333", R.drawable.placeholder_profile, R.drawable.img2, 43, 4, true, true));
        feedPosts.add(new FeedPost("Abizer4", "444444", R.drawable.placeholder_profile, R.drawable.img2, 2333, 23, true, false));
        feedPosts.add(new FeedPost("Abizer5", "555555", R.drawable.placeholder_profile, R.drawable.img2, 2, 43, true, true));
        feedPosts.add(new FeedPost("Abizer6", "666666", R.drawable.placeholder_profile, R.drawable.img1, 3, 0, false, false));

        feedAdapter.setFeedPosts(feedPosts);
    }
}
