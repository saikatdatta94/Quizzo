package com.example.saikat.quizzo;

import android.net.Uri;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardListAdapter extends FirestoreRecyclerAdapter<LeaderBoardItem,LeaderBoardListAdapter.LeaderListHolder> {

    public LeaderBoardListAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull LeaderListHolder holder, int position, @NonNull LeaderBoardItem model) {

        Picasso.get().load(model.getPhotoURL()).into(holder.leaderBoardImage);
        holder.leaderBoardName.setText(model.getUserName());
        holder.leaderBoardScore.setText(String.valueOf(model.getScore()));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        Log.i("Msg","Data has changed");
    }

    @NonNull
    @Override
    public LeaderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_board_item,parent,false);
        return new LeaderListHolder(v);
    }


    class LeaderListHolder extends RecyclerView.ViewHolder{

        TextView number;
        CircleImageView leaderBoardImage;
        TextView leaderBoardName;
        TextView leaderBoardScore;
        public LeaderListHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.leader_board_number);
            leaderBoardImage = itemView.findViewById(R.id.leader_board_image);
            leaderBoardName = itemView.findViewById(R.id.leader_board_name);
            leaderBoardScore = itemView.findViewById(R.id.leader_board_score);
        }
    }
}
