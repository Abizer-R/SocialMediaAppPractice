package com.example.socialmediaapp_1.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialmediaapp_1.Adapter.SearchAdapter;
import com.example.socialmediaapp_1.Models.UserModel;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.SearchRVInterface;
import com.example.socialmediaapp_1.databinding.FragmentSearchBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SearchFragment extends Fragment implements SearchRVInterface {

    private SearchAdapter searchAdapter;
    private List<UserModel> userList;

    FirebaseAuth auth;
    DatabaseReference dbRef;
    FragmentSearchBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        userList = new ArrayList<>();
        searchAdapter = new SearchAdapter(getContext(), this);
        searchAdapter.setUserList(userList);
        binding.searchRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchRv.setAdapter(searchAdapter);

        binding.searchEv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return binding.getRoot();
    }

    private void searchUsers(String str) {
        Query query = dbRef.orderByChild("Info/username")
                .startAt(str)
                .endAt(str + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot snap: snapshot.getChildren()) {
                    UserModel currModel = snap.child("Info").getValue(UserModel.class);

                    if(!str.isEmpty() && !auth.getUid().equals(currModel.getUid())) {
                        userList.add(currModel);
                    }
                }
                searchAdapter.setUserList(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(String userID) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        FollowUserFragment followUserFragment = new FollowUserFragment();

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.follow_user_bundle_key), userID);
        followUserFragment.setArguments(bundle);
        //By calling addToBackStack(), the replaced fragment is saved to back stack so that the user can
        // reverse the transaction and bring back the previous fragment by pressing back button
        transaction.addToBackStack(null);
        transaction.replace(R.id.bottom_nav_fragment_container, followUserFragment);
        transaction.commit();
    }
}
