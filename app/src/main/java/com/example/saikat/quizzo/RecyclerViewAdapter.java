package com.example.saikat.quizzo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{


    private ArrayList<String> imageURL = new ArrayList<>();
    private ArrayList<String> head = new ArrayList<>();
    private ArrayList<String> descriptionText = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter( Context mContext,ArrayList<String> imageURL, ArrayList<String> head, ArrayList<String> descriptionText) {
        this.imageURL = imageURL;
        this.head = head;
        this.descriptionText = descriptionText;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.descriptionTextView.setText(descriptionText.get(position));
        holder.headingTextView.setText(head.get(position));

    }

    @Override
    public int getItemCount() {
        return head.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnailImageView;
        TextView headingTextView;
        TextView descriptionTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            thumbnailImageView = itemView.findViewById(R.id.thumbnail);
            headingTextView = itemView.findViewById(R.id.heading);
            descriptionTextView = itemView.findViewById(R.id.description);
        }
    }
}
