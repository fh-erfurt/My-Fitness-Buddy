package PME.myfitnessbuddy.view.ui.exercise;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.model.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.MuscleGroup;

import java.io.File;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    static int counter = 0;

    public interface ExerciseClickListener {
        void onClick(long exerciseId);
    }


    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseName;
        private final ImageView imageView;
        private long currentExerciseId = -1;

        private ExerciseViewHolder(View itemView, ExerciseClickListener exerciseClickListener) {
            super(itemView);

            this.exerciseName = itemView.findViewById(R.id.list_item_exercise_name);
            this.imageView =itemView.findViewById(R.id.list_item_exercise_image);

            itemView.setOnClickListener( vE -> {
                exerciseClickListener.onClick( this.currentExerciseId );
            });
        }
    }

    private final LayoutInflater inflater;
    private List<ExerciseWithMuscleGroup> exerciseList;

    private final ExerciseClickListener exerciseClickListener;
  //  MuscleGroupRepository muscleGroupRepository = MuscleGroupRepository.getRepository(this.)
    private List<MuscleGroup> muscleGroupList;


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
            ExerciseWithMuscleGroup current = this.exerciseList.get(position);
   //        LiveData<MuscleGroup> muscleGroup = muscleGroupDao.geMuscleGroupById(current.getMuscleGroups().get(0).getMuscleGroupId());

            holder.exerciseName.setText(String.format("%s", current.getExercise().getDesignation()));

            holder.currentExerciseId = current.getExercise().getExerciseId();



                holder.imageView.setImageResource(current.getExercise().getProfileImageUrl());


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

    public void setExercises(List<ExerciseWithMuscleGroup> exerciseList){
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }

    public void setMuscleGroups(List<MuscleGroup> muscleGroupList){
        this.muscleGroupList = muscleGroupList;
        notifyDataSetChanged();
    }

}