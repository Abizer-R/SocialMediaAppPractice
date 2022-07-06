package com.example.socialmediaapp_1.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.databinding.FragmentEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {

    public static final int IMAGE_REQUEST_ID = 212;

    FragmentEditProfileBinding binding;

    FirebaseAuth auth;
    StorageReference storageRef;
    DatabaseReference dbRef;

    ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            dialogBuilder.setTitle("Loading");
            dialogBuilder.setMessage("Please wait");

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();

            // Uploading profile photo on firebase storage
            storageRef.child(auth.getUid()).putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Getting download url of profile photo and adding it to user info in firebase database
                    storageRef.child(auth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dbRef.child(auth.getUid()).child("Info")
                                    .child("imgurl")
                                    .setValue(uri.toString());


                            dialog.cancel();
                            Toast.makeText(getActivity(), "Profile Image updated Successfully", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    });
                }
            });
        }
    });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference().child("profile_photo");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        retrieveData();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(binding.editProfileToolbar);

        binding.editProfileToolbar.setTitle("");
        binding.editProfileToolbar.setNavigationIcon(R.drawable.ic_baseline_close_36);
        binding.editProfileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        binding.editProfilePhotoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });

        binding.editProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setTitle("Updating");
                dialogBuilder.setMessage("Please wait");

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                String name = binding.editProfileName.getText().toString();
                String username = binding.editProfileUsername.getText().toString();
                String bio = binding.editProfileBio.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("username", username);
                map.put("bio", bio);

                dbRef.child(auth.getUid()).child("Info")
                        .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.cancel();
                                Toast.makeText(getActivity(), "Profile updated.", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }
                        });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrieveData();
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
                                    .into(binding.editProfileImage);

                            if(snapshot.child("name").exists()) {
                                binding.editProfileName.setText(snapshot.child("name").getValue(String.class));
                            }
                            if(snapshot.child("username").exists()) {
                                binding.editProfileUsername.setText(snapshot.child("username").getValue(String.class));
                            }
                            if(snapshot.child("bio").exists()) {
                                binding.editProfileBio.setText(snapshot.child("bio").getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
