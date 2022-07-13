package com.example.socialmediaapp_1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.databinding.FragmentFollowUserBinding;

public class FollowUserFragment extends Fragment {

    FragmentFollowUserBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFollowUserBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        String userID = bundle.getString(getString(R.string.follow_user_bundle_key));

        setUserInfo(userID);

        return binding.getRoot();
    }

    private void setUserInfo(String userID) {

    }
}
