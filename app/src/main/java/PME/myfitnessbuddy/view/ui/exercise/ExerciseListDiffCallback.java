package PME.myfitnessbuddy.view.ui.exercise;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;

/**
 * Exercise List Callback
 * for delete selected trainings
 * */

class ExerciseListDiffCallback extends DiffUtil.ItemCallback<ExerciseWithMuscleGroup> {

    @Override
    public boolean areItemsTheSame(@NonNull ExerciseWithMuscleGroup oldItem, @NonNull ExerciseWithMuscleGroup newItem) {
        return oldItem.getExercise().getExerciseId() == newItem.getExercise().getExerciseId();
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull ExerciseWithMuscleGroup oldItem, @NonNull ExerciseWithMuscleGroup newItem) {
        return oldItem.equals( newItem );
    }
}
