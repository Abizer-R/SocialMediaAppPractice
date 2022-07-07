package com.example.socialmediaapp_1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialmediaapp_1.Models.UserModel;
import com.example.socialmediaapp_1.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    List<UserModel> userList;
    Context mcontext;

    public SearchAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_rv_item, parent, false);
        return new SearchViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView name, username;
        ImageView profileImage;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.search_rv_name);
            username = itemView.findViewById(R.id.search_rv_username);
            profileImage = itemView.findViewById(R.id.search_rv_profile_image);
        }
    }
}
