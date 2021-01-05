package PME.myfitnessbuddy.view.ui.training;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.training.TrainingWithExercise;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingViewHolder> {
    static int counter = 0;

    public interface TrainingClickListener {
        void onClick(long trainingId);
    }


    static class TrainingViewHolder extends RecyclerView.ViewHolder {
        private final TextView trainingName;
        private final ImageView trainingImage;
        private final TextView countedExercises;
        private long currentTrainingId = -1;

        private TrainingViewHolder(View itemView, TrainingClickListener trainingClickListener) {
            super(itemView);

            this.trainingName = itemView.findViewById(R.id.list_item_training_name);
            this.trainingImage = itemView.findViewById(R.id.list_item_training_image);
            this.countedExercises =itemView.findViewById(R.id.list_item_counted_exercises);

            itemView.setOnClickListener( v -> {
                trainingClickListener.onClick( this.currentTrainingId );
            });
        }
    }

    private final LayoutInflater inflater;
    private List<TrainingWithExercise> trainingList; // Cached Copy of Contacts
    private final TrainingClickListener trainingClickListener;

    public TrainingListAdapter(Context context, TrainingClickListener trainingClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.trainingClickListener = trainingClickListener;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_training, parent, false);

        Log.i( "OnCreateViewHolder", "Count: " + ++TrainingListAdapter.counter);

        return new TrainingViewHolder(itemView, this.trainingClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        if (this.trainingList != null && !this.trainingList.isEmpty()) {
            TrainingWithExercise current = this.trainingList.get(position);
           holder.trainingName.setText(String.format("%s", current.getTraining().getDesignation()));
           holder.currentTrainingId = current.getTraining().getTrainingId();


           //Picasso p = Picasso.get();
           //p.setIndicatorsEnabled(true);

        }
        else {
            // Covers the case of DataForDB not being ready yet.
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

    public void setTrainings(List<TrainingWithExercise> trainingList){
        this.trainingList = trainingList;
        notifyDataSetChanged();
    }



}
