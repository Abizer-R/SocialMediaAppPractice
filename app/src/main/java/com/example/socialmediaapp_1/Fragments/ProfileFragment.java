package com.example.socialmediaapp_1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    FirebaseAuth auth;
    DatabaseReference dbRef;

    FragmentProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        retrieveData();
//        toolbar = view.findViewById(R.id.profile_fragment_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(binding.profileFragmentToolbar);
        binding.profileFragmentToolbar.setTitle("");

        binding.profileFragmentEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.bottom_nav_fragment_container, editProfileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_fragment_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void retrieveData() {
        dbRef.child(auth.getUid())
                .child("Info")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String imgurl = snapshot.child("imgurl").getValue(String.class);

                            Glide.with(getContext())
                                    .load(imgurl)
                                    .centerCrop()
                                    .placeholder(R.drawable.placeholder_profile)
                                    .into(binding.profileFragmentProfileImage);

                            if(snapshot.child("name").exists()) {
                                binding.profileFragmentName.setText(snapshot.child("name").getValue(String.class));
                            }
                            if(snapshot.child("username").exists()) {
                                binding.profileFragmentUserName.setText(snapshot.child("username").getValue(String.class));
                            }
                            if(snapshot.child("bio").exists()) {
                                binding.profileFragmentUserBio.setText(snapshot.child("bio").getValue(String.class));
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
