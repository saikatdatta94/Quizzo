package com.example.saikat.quizzo;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class HorizontalCategoryListAdapter extends FirestoreRecyclerAdapter<CategoryItemClass, HorizontalCategoryListAdapter.NoteHolderHorizontal> {

    private OnHorizontalCategoryOnclickListener listener;

    public HorizontalCategoryListAdapter(@NonNull FirestoreRecyclerOptions<CategoryItemClass> options) {
        super(options);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        Log.i("Msg","Data has changed");
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolderHorizontal holder, int position, @NonNull CategoryItemClass model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getParentCategory());
        Picasso.get()
                .load(model.getPhotoURL())
                .placeholder(R.drawable.blank_category_item_thumb)
                .error(R.drawable.blank_category_item_thumb)
                .into(holder.categoryItemThumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
//                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
//        holder.textViewPriority.setText(String.valueOf(model.getPriority()));
    }

    @NonNull
    @Override
    public NoteHolderHorizontal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_item,
                parent, false);
        return new NoteHolderHorizontal(v);
    }

    class NoteHolderHorizontal extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView categoryItemThumbnail;
        TextView textViewPriority;

        public NoteHolderHorizontal(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.heading_horizontal_category_item);
            textViewDescription = itemView.findViewById(R.id.description_horizontal_category_item);
            categoryItemThumbnail = itemView.findViewById(R.id.thumbnail_horizontal_category_item);
//            textViewPriority = itemView.findViewById(R.id.text_view_priority);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onHorizontalItemCLick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnHorizontalCategoryOnclickListener{
        void onHorizontalItemCLick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnHorizontalCategoryOnclickListener(OnHorizontalCategoryOnclickListener listener){
        this.listener = listener;
    }
}
