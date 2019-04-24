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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.ArrayList;

public class CategoryAdapter extends FirestoreRecyclerAdapter<ListItem, CategoryAdapter.HorizontalCategoryHolder> {


//    private ArrayList<ListItem> listViewItemArray;
//    private Context mContext;


    public CategoryAdapter(@NonNull FirestoreRecyclerOptions<ListItem> options) {
        super(options);

    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        Log.i("DATACHANE","DATA HAS changed");
    }

    @Override
    protected void onBindViewHolder(@NonNull HorizontalCategoryHolder holder, int position, @NonNull ListItem model) {
        holder.headingTextView.setText(model.getHeading());
        holder.descriptionTextView.setText(model.getDescriptionText());
    }

    @NonNull
    @Override
    public HorizontalCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_item,parent,false);
        return new HorizontalCategoryHolder(view);
    }




    class HorizontalCategoryHolder extends RecyclerView.ViewHolder {

        ImageView thumbnailImageView;
        TextView headingTextView;
        TextView descriptionTextView;


        public HorizontalCategoryHolder(@NonNull View itemView) {
            super(itemView);

//            headingTextView = itemView.findViewById(R.id.heading);
//            descriptionTextView = itemView.findViewById(R.id.description);
        }
    }


}



































































//    @Override
//    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ListItem model) {


//                initLayoutOne((HorizontalCategoryHolder) holder,position);





//        final ListItem subCategory = listViewItemArray.get(position);
////        Redirecting to new activity from RecyclerView
////      TODO:  This operation is costly follow Minch and refactor this code later ******************ONCLICK FUNCTION*********************
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent beforeQuizPlayActivity = new Intent(view.getContext(),QuestionActivity.class);
//                beforeQuizPlayActivity.putExtra("heading",subCategory.getHeading());
//                beforeQuizPlayActivity.putExtra("description",subCategory.getDescriptionText());
//                mContext.startActivity(beforeQuizPlayActivity);
//            }
//        });


//    }

//    private void initLayoutTwo(VerticalCategoryHolder holder, int position) {
//        holder.headingForVerticalList.setText(listViewItemArray.get(position).getHeading());
//        holder.descriptionForVerticalList.setText(listViewItemArray.get(position).getDescriptionText());
//    }

//    class VerticalCategoryHolder extends RecyclerView.ViewHolder{
//
//        PorterShapeImageView thumbnailForVerticalList;
//        //        ImageView thumbnailForVerticalList;
//        TextView headingForVerticalList;
//        TextView descriptionForVerticalList;
//
//
//        public VerticalCategoryHolder(@NonNull View itemView) {
//            super(itemView);
//
//            thumbnailForVerticalList = itemView.findViewById(R.id.image_category_vertical_list);
//            headingForVerticalList = itemView.findViewById(R.id.heading_category_vertical_list);
//            descriptionForVerticalList= itemView.findViewById(R.id.description_category_vertical_list);
//
//            thumbnailForVerticalList.setImageResource(R.drawable.thumb);
//        }
//    }











//    private void initLayoutOne(HorizontalCategoryHolder holder, int position) {
//        holder.headingTextView.setText(listViewItemArray.get(position).getHeading());
//        holder.descriptionTextView.setText(listViewItemArray.get(position).getDescriptionText());
//    }







//    @Override
//    public int getItemViewType(int position) {
//
//        ListItem item = listViewItemArray.get(position);
//        if (item.getType()== ListItem.ListType.ONE){
//            return TYPE_ONE;
//        }else if (item.getType()== ListItem.ListType.TWO){
//            return TYPE_TWO;
//        }
//        return -1;
//    }