package com.example.saikat.quizzo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class VerticalCategoryListAdapter extends FirestoreRecyclerAdapter<FollowingCategoryItemClass, VerticalCategoryListAdapter.NoteHolder> {
    private OnItemClickListener listener;     //  Global for the listener below

    public VerticalCategoryListAdapter(@NonNull FirestoreRecyclerOptions<FollowingCategoryItemClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull FollowingCategoryItemClass model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
//        holder.textViewPriority.setText(String.valueOf(model.getPriority()));
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_vertical_list_item_layout,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.heading_category_vertical_list);
            textViewDescription = itemView.findViewById(R.id.description_category_vertical_list);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);


//       TODO     It is better to catch click inside the itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                  To distinguish each itemView
                    int position = getAdapterPosition();

//                    TODO************* Take a document snapshot get its value from the database and send it to a new activity if needed
//                    TODO********************* To implement this better create an Interface*********************

                      if (position != RecyclerView.NO_POSITION && listener != null){  //Prevented: If in item is clicked while deleting app will crash.
                          listener.onItemClick(getSnapshots().getSnapshot(position),position);//Pass the snapshot amd position
                      }

                }
            });
        }
    }

    public interface OnItemClickListener{

//        TODO:     ****************** Call this method from an activity
//        TODO:     ****************** From the passed DocumentSnapshot create a Object from it and
//        TODO:      ***************** retrieve the values and even its id and pass the int position of the listItem
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
