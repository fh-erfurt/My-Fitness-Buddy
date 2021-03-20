package PME.myfitnessbuddy.view.ui.training;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import PME.myfitnessbuddy.model.training.TrainingWithExercise;

/**
 * Training List Callback
 * for delete selected trainings
 * */

public class TrainingListDiffCallback extends DiffUtil.ItemCallback<TrainingWithExercise>{

    @Override
    public boolean areItemsTheSame(@NonNull TrainingWithExercise oldItem, @NonNull TrainingWithExercise newItem) {
        return oldItem.getTraining().getTrainingId() == newItem.getTraining().getTrainingId();
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull TrainingWithExercise oldItem, @NonNull TrainingWithExercise newItem) {
        return oldItem.equals( newItem );
    }
}
