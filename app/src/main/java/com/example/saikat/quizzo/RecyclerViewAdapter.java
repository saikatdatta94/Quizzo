package com.example.saikat.quizzo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.ArrayList;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //TODO: delete if doesn't work
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    //    private ArrayList<String> imageURL = new ArrayList<>();
//    private ArrayList<String> head = new ArrayList<>();
//    private ArrayList<String> descriptionText = new ArrayList<>();
    private ArrayList<ListItem> listViewItemArray;
    private Context mContext;

    public RecyclerViewAdapter( Context mContext,ArrayList<ListItem> listViewItemArray) {
        this.listViewItemArray = listViewItemArray;
        this.mContext = mContext;
    }

//    Determine which layout to use


    @Override
    public int getItemViewType(int position) {

        ListItem item = listViewItemArray.get(position);
        if (item.getType()== ListItem.ListType.ONE){
            return TYPE_ONE;
        }else if (item.getType()== ListItem.ListType.TWO){
            return TYPE_TWO;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ONE){
            View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_list_item,parent,false);
            return new MyViewHolder(view);
        }else if (viewType == TYPE_TWO){
            View view = LayoutInflater.from(mContext).inflate(R.layout.category_vertical_list_item_layout,parent,false);
            return new MyViewHolderNew(view);
        }else {
//            TODO: Show a toast containing Error info
            throw new RuntimeException("The type has to be ONE or TWO");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_ONE:
                initLayoutOne((MyViewHolder)holder,position);
                break;
            case TYPE_TWO:
                initLayoutTwo((MyViewHolderNew)holder,position);
                break;
            default:
                break;
        }


        final ListItem subCategory = listViewItemArray.get(position);
//        Redirecting to new activity from RecyclerView
//      TODO:  This operation is costly follow Minch and refactor this code later
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beforeQuizPlayActivity = new Intent(view.getContext(),QuestionActivity.class);
                beforeQuizPlayActivity.putExtra("heading",subCategory.getHeading());
                beforeQuizPlayActivity.putExtra("description",subCategory.getDescriptionText());
                mContext.startActivity(beforeQuizPlayActivity);
            }
        });

    }

    private void initLayoutTwo(MyViewHolderNew holder, int position) {
        holder.headingForVerticalList.setText(listViewItemArray.get(position).getHeading());
        holder.descriptionForVerticalList.setText(listViewItemArray.get(position).getDescriptionText());
    }

    private void initLayoutOne(MyViewHolder holder, int position) {
        holder.headingTextView.setText(listViewItemArray.get(position).getHeading());
        holder.descriptionTextView.setText(listViewItemArray.get(position).getDescriptionText());
    }

    @Override
    public int getItemCount() {
        return listViewItemArray.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView thumbnailImageView;
        TextView headingTextView;
        TextView descriptionTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

//            thumbnailImageView = itemView.findViewById(R.id.thumbnail);

            headingTextView = itemView.findViewById(R.id.heading);
            descriptionTextView = itemView.findViewById(R.id.description);
        }
    }

    public class MyViewHolderNew extends RecyclerView.ViewHolder{
//        RoundedImageView riv;
        PorterShapeImageView thumbnailForVerticalList;
//        ImageView thumbnailForVerticalList;
        TextView headingForVerticalList;
        TextView descriptionForVerticalList;


        public MyViewHolderNew(View itemViewForVerticalList) {
            super(itemViewForVerticalList);
//            riv = itemView.findViewById(R.id.image_category_vertical_list);

            thumbnailForVerticalList = itemViewForVerticalList.findViewById(R.id.image_category_vertical_list);
            headingForVerticalList = itemViewForVerticalList.findViewById(R.id.heading_category_vertical_list);
            descriptionForVerticalList= itemViewForVerticalList.findViewById(R.id.description_category_vertical_list);

            thumbnailForVerticalList.setImageResource(R.drawable.thumb);
        }
    }




}
