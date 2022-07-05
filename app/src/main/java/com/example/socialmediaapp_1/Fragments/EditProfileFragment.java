package com.example.socialmediaapp_1.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.databinding.FragmentEditProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfileFragment extends Fragment {

    public static final int IMAGE_REQUEST_ID = 212;

    private Toolbar toolbar;
    private TextView changeProfileImageView;
    private ImageView profilePhoto;

    FirebaseAuth auth;
    StorageReference storageRef;
    DatabaseReference dbRef;

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

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        toolbar = view.findViewById(R.id.edit_profile_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

//        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_36);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_36);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        profilePhoto = view.findViewById(R.id.edit_profile_image);
        changeProfileImageView = view.findViewById(R.id.edit_profile_photo_tv);

        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                //TODO: start an updating dialog box
                profilePhoto.setImageURI(result);
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

                                // TODO: close the updating dialog box here
                                Toast.makeText(activity, "Profile Image updated Successfully", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }
                        });
                    }
                });
            }
        });

        changeProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
            }
        });


        return view;
    }
}
