package com.example.socialmediaapp_1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.databinding.FragmentFollowUserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FollowUserFragment extends Fragment {

    DatabaseReference dbRef;
    FragmentFollowUserBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFollowUserBinding.inflate(inflater, container, false);

        binding.followUserFragmentToolbar.setTitle("");
        binding.followUserFragmentToolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);
        binding.followUserFragmentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Bundle bundle = getArguments();
        String userID = bundle.getString(getString(R.string.follow_user_bundle_key));

        setUserInfo(userID);

        return binding.getRoot();
    }

    private void setUserInfo(String userID) {
        dbRef.child(userID).child("Info")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String imgurl = snapshot.child("imgurl").getValue(String.class);

                            Glide.with(getContext())
                                    .load(imgurl)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_profile)
                                    .into(binding.followUserFragmentProfileImage);

                            if(snapshot.child("name").exists()) {
                                binding.followUserFragmentName.setText(snapshot.child("name").getValue(String.class));
                            }
                            if(snapshot.child("username").exists()) {
                                binding.followUserFragmentUserName.setText(snapshot.child("username").getValue(String.class));
                            }
                            if(snapshot.child("bio").exists()) {
                                binding.followUserFragmentUserBio.setText(snapshot.child("bio").getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
