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

import com.example.socialmediaapp_1.Adapter.ActivityAdapter;
import com.example.socialmediaapp_1.Models.ActivityModel;
import com.example.socialmediaapp_1.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment {

    private ActivityAdapter activityAdapter;
    private List<ActivityModel> activityList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        setActivityRecyclerView(view);
        setActivityAdapter();  //TODO: change put real thing into activity Adapter

        return view;
    }

    private void setActivityRecyclerView(View view) {
        RecyclerView activityRecyclerView = view.findViewById(R.id.activity_rv);
        activityRecyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false)
        );
        activityRecyclerView.setHasFixedSize(true);

        activityAdapter = new ActivityAdapter(view.getContext());
        activityRecyclerView.setAdapter(activityAdapter);
    }

    private void setActivityAdapter() {
        activityList = new ArrayList<>();

        activityList.add(new ActivityModel("Abizer1", R.drawable.placeholder_profile, 1656481447, R.drawable.img1, 1, null));
        activityList.add(new ActivityModel("Abizer2", R.drawable.placeholder_profile, 1656396113, R.drawable.img2, 2, null));
        activityList.add(new ActivityModel("Abizer3", R.drawable.placeholder_profile, 1655791313, null, 3, null));
        activityList.add(new ActivityModel("Abizer4", R.drawable.placeholder_profile, 1655445713, R.drawable.img1, 4, "Freaking comment"));
        activityList.add(new ActivityModel("Abizer5", R.drawable.placeholder_profile, 1654840913, null, 5, null));
        activityList.add(new ActivityModel("Abizer1", R.drawable.placeholder_profile, 1652162513, R.drawable.img1, 1, null));
        activityList.add(new ActivityModel("Abizer2", R.drawable.placeholder_profile, 1620626513, R.drawable.img2, 2, null));

        activityList.add(new ActivityModel("Abizer4", R.drawable.placeholder_profile, 1654236113, R.drawable.img1, 4,
                "Freaking comment sdfds fdsa sdfjkds fdskf jds fsd hfkdsh kfhsd fjdfk sdjf sd fdsafds"));

        activityAdapter.setActivityList(activityList);
    }

}
