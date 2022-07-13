package com.example.socialmediaapp_1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp_1.Fragments.FollowUserFragment;
import com.example.socialmediaapp_1.Models.UserModel;
import com.example.socialmediaapp_1.R;
import com.example.socialmediaapp_1.SearchRVInterface;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private final SearchRVInterface searchRVInterface;

    List<UserModel> userList;
    Context mcontext;

    public SearchAdapter(Context mcontext, SearchRVInterface searchRVInterface) {
        this.mcontext = mcontext;
        this.searchRVInterface = searchRVInterface;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_rv_item, parent, false);
        return new SearchViewHolder(view, searchRVInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        UserModel currUser = userList.get(position);

        if(currUser.getName() == null) {
            holder.name.setVisibility(View.GONE);
        } else {
            holder.name.setText(currUser.getName());
        }
        holder.username.setText(currUser.getUsername());

        Glide.with(mcontext)
                .load(currUser.getImgurl())
                .placeholder(R.drawable.placeholder_profile)
                .into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchRVInterface != null) {
                    searchRVInterface.onItemClick(currUser.getUid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView name, username;
        ImageView profileImage;

        public SearchViewHolder(@NonNull View itemView, SearchRVInterface searchRVInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.search_rv_name);
            username = itemView.findViewById(R.id.search_rv_username);
            profileImage = itemView.findViewById(R.id.search_rv_profile_image);
        }
    }
}
