package com.example.dobra.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private List<Friend> mFriends = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, List<Friend> mFriends) {
        this.mFriends = mFriends;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_frienditem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext).asBitmap().load(mFriends.get(i).getFriendImage()).into(viewHolder.friendImage);

        //Picasso.with(mContext).load(mFriends.get(i).getFriendImage()).into(viewHolder.friendImage);

        viewHolder.friendName.setText(mFriends.get(i).getFriendName());
        viewHolder.friendLevel.setText(mFriends.get(i).getFriendLevel());
        viewHolder.friendProgress.setText(mFriends.get(i).getFriendProgress());
        viewHolder.friendActivity.setText(mFriends.get(i).getFriendActivity());
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "This is a work in progress! You will be able to remove users in a bit...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView friendImage;

        TextView friendName, friendLevel, friendProgress, friendActivity;

        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            friendImage = itemView.findViewById(R.id.friendImage);
            friendName = itemView.findViewById(R.id.friendName);
            friendLevel = itemView.findViewById(R.id.friendLevel);
            friendProgress = itemView.findViewById(R.id.friendProgress);
            friendActivity = itemView.findViewById(R.id.friendActivity);
            parentLayout = itemView.findViewById(R.id.friendParentLayout);

        }
    }
}
