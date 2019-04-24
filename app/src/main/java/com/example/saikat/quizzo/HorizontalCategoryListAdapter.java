package com.example.saikat.quizzo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HorizontalCategoryListAdapter extends FirestoreRecyclerAdapter<Note, HorizontalCategoryListAdapter.NoteHolderHorizontal> {

    public HorizontalCategoryListAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        Log.i("Msg","Data has changed");
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolderHorizontal holder, int position, @NonNull Note model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
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
        TextView textViewPriority;

        public NoteHolderHorizontal(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.heading_horizontal_category_item);
            textViewDescription = itemView.findViewById(R.id.description_horizontal_category_item);
//            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
