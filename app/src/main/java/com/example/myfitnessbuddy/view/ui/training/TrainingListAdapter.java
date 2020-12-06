package com.example.myfitnessbuddy.view.ui.training;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessbuddy.R;
import com.example.myfitnessbuddy.model.Training.Training;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingViewHolder> {
    static int counter = 0;

    static class TrainingViewHolder extends RecyclerView.ViewHolder {
        private final TextView trainingName;
        private final ImageView trainingImage;

        private TrainingViewHolder(View itemView) {
            super(itemView);
            this.trainingName = itemView.findViewById(R.id.list_item_training_name);
            this.trainingImage = itemView.findViewById(R.id.list_item_training_image);
        }
    }

    private final LayoutInflater inflater;
    private List<Training> trainingList; // Cached Copy of Contacts

    public TrainingListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_training, parent, false);

        Log.i( "OnCreateViewHolder", "Count: " + ++TrainingListAdapter.counter);

        return new TrainingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        if (this.trainingList != null && !this.trainingList.isEmpty()) {
            Training current = this.trainingList.get(position);
            holder.trainingName.setText(String.format("%s", current.getDesignation()));

            Picasso p = Picasso.get();
            p.setIndicatorsEnabled(true);
            p.load(current.getProfileImageUrl())
                    .placeholder(R.drawable.icon_profile_placeholder)
                    .error(R.drawable.icon_error)
                    .resize(80, 80)
                    .rotate(-15.0f)
                    .centerCrop()
                    .into( holder.trainingImage);
        }
        else {
            // Covers the case of data not being ready yet.
            holder.trainingName.setText(R.string.text_empty_list);
        }
    }

    @Override
    public int getItemCount() {
        if( this.trainingList != null && !this.trainingList.isEmpty() )
            return this.trainingList.size();
        else
            return 0;
    }

    public void setTrainings(List<Training> trainingList){
        this.trainingList = trainingList;
        notifyDataSetChanged();
    }

}
