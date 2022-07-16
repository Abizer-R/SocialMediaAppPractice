package com.example.socialmediaapp_1.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.databinding.FragmentFollowUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FollowUserFragment extends Fragment {

    DatabaseReference dbRef;
    FragmentFollowUserBinding binding;
    String userID;
    String currUserId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        currUserId = FirebaseAuth.getInstance().getUid();
        Bundle bundle = getArguments();
        userID = bundle.getString(getString(R.string.follow_user_bundle_key));
    }

    private void checkFollowingStatus() {
        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Follow_Requests").child(currUserId).exists()) {
                    changeFollowBtn(getString(R.string.USER_FOLLOW_STATUS_REQUESTED), "#efefef", "#FF000000");

                } else if(snapshot.child("Followers").child(currUserId).exists()) {
                    changeFollowBtn(getString(R.string.USER_FOLLOW_STATUS_FOLLOWING), "#efefef", "#FF000000");
                    getUserPosts();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        binding.progressbar.setVisibility(View.VISIBLE);
        checkFollowingStatus();
        setUserInfo(userID);

        binding.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Follow_Requests").child(currUserId).exists()) {

                            dbRef.child(userID).child("Follow_Requests").child(currUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //TODO: Remove this onCompleteListener
                                    if(task.isSuccessful()) {
                                        changeFollowBtn("Follow", "#0095f6", "#FFFFFFFF");
                                        Toast.makeText(getActivity(), "User requested cancelled", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(getActivity(), "Can't cancel request", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else if(snapshot.child("Followers").child(currUserId).exists()) {
                            unfollowUser(userID);

                        } else {
                            dbRef.child(userID).child("Follow_Requests").child(currUserId).setValue("Requested")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "User Request Sent", Toast.LENGTH_SHORT).show();
                                        changeFollowBtn(getString(R.string.USER_FOLLOW_STATUS_REQUESTED), "#efefef", "#FF000000");
                                    } else
                                        Toast.makeText(getActivity(), "Can't send follow request", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void unfollowUser(String userID) {

        //TODO: Make Dialog to confirm, if yes then do following
        changeFollowBtn("Follow", "#0095f6", "#FFFFFFFF");

        // Removing currUser from Followers of other user
        dbRef.child(userID).child("Followers").child(currUserId).removeValue();
        // Removing other user from Following of currUser
        dbRef.child(currUserId).child("Following").child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //TODO: Hide the posts
                Toast.makeText(getActivity(), "User Following removed", Toast.LENGTH_SHORT).show();
            }
        });
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
                            binding.followUserFragmentPostCount.setText("" + snapshot.child("postCount").getValue(Long.class));
                            binding.followUserFragmentFollowersCount.setText("" + snapshot.child("followerCount").getValue(Long.class));
                            binding.followUserFragmentFollowingCount.setText("" + snapshot.child("followingCount").getValue(Long.class));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.progressbar.setVisibility(View.GONE);
        binding.followUserFragmentSv.setVisibility(View.VISIBLE);
    }

    private void changeFollowBtn(String btnText, String bgColor, String textColor) {
        binding.followBtn.setText(btnText);
        binding.followBtn.setTextColor(Color.parseColor(textColor));

        GradientDrawable tvBackground = (GradientDrawable) binding.followBtn.getBackground();
        tvBackground.setTint(Color.parseColor(bgColor));

    }

    private void getUserPosts() {
        // TODO: setvisibility of post recyclerview and retreive posts.
    }


}
