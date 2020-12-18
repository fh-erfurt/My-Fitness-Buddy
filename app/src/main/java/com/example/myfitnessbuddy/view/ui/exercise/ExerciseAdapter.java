package com.example.myfitnessbuddy.view.ui.exercise;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessbuddy.R;
import com.example.myfitnessbuddy.model.exercise.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    static int counter = 0;

    public interface ExerciseClickListener {
        void onClick(long exerciseId);
    }


    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseName;
        private long currentExerciseId = -1;

        private ExerciseViewHolder(View itemView, ExerciseClickListener exerciseClickListener) {
            super(itemView);

            this.exerciseName = itemView.findViewById(R.id.list_item_exercise_name);

            itemView.setOnClickListener( vE -> {
                exerciseClickListener.onClick( this.currentExerciseId );
            });
        }
    }

    private final LayoutInflater inflater;
    private List<Exercise> exerciseList;
    private final ExerciseClickListener exerciseClickListener;

    public ExerciseAdapter(Context context, ExerciseClickListener exerciseClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.exerciseClickListener = exerciseClickListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_exercise, parent, false);

        Log.i( "OnCreateViewHolder", "Count: " + ++ExerciseAdapter.counter);

        return new ExerciseViewHolder(itemView, this.exerciseClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        if (this.exerciseList != null && !this.exerciseList.isEmpty()) {
            Exercise current = this.exerciseList.get(position);
            holder.exerciseName.setText(String.format("%s", current.getDesignation()));

            holder.currentExerciseId = current.getId();


        }
        else {
            // Covers the case of data not being ready yet.
            holder.exerciseName.setText(R.string.text_empty_list);
        }
    }

    @Override
    public int getItemCount() {
        if( this.exerciseList != null && !this.exerciseList.isEmpty() )
            return this.exerciseList.size();
        else
            return 0;
    }

    public void setExercises(List<Exercise> exercises){
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }

}