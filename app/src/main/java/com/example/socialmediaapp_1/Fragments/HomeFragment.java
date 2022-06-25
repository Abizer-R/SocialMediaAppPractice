package com.example.socialmediaapp_1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmediaapp_1.Adapter.StoryAdapter;
import com.example.socialmediaapp_1.Models.Story;
import com.example.socialmediaapp_1.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private StoryAdapter storyAdapter;
    private List<Story> stories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setStoryRecyclerView(view);
        setStoryAdapter();  //TODO: change put real thing into story Adapter

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

        List<Story> storyList = new ArrayList<>();

        storyList.add(new Story("Abizer1", R.drawable.placeholder_profile, false));
        storyList.add(new Story("Abizer2", R.drawable.placeholder_profile, false));
        storyList.add(new Story("Abizer3", R.drawable.placeholder_profile, false));
        storyList.add(new Story("Abizer4", R.drawable.placeholder_profile, false));
        storyList.add(new Story("Abizer5", R.drawable.placeholder_profile, false));
        storyList.add(new Story("Abizer6", R.drawable.placeholder_profile, false));

        storyAdapter.setStories(storyList);

    }
}
