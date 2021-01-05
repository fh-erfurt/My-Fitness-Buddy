package PME.myfitnessbuddy.view.ui.training;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;


/////////// LÖSUNG1 (falls Lösung2 nicht funktionier)........ später löschen
public class TrainingCreateAdapter extends RecyclerView.Adapter<TrainingCreateAdapter.ExerciseViewHolder>{

    static int counter = 0;

    public static List<Model> items = new ArrayList<>();



    public interface ExerciseClickListener {
        void onClick(long exerciseId);
    }


    static class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView exerciseName;
        private final ImageView imageView;
        private long currentExerciseId = -1;

        CheckedTextView mCheckedTextView;
        List <Integer> checkedExerciseIdList = new ArrayList();



      @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (!items.get(adapterPosition).getChecked()) {
                mCheckedTextView.setChecked(true);
                items.get(adapterPosition).setChecked(true);

            }
            else  {
                mCheckedTextView.setChecked(false);
                items.get(adapterPosition).setChecked(false);

            }
        }

        void bind(int position) {
            // check the state of the model
            if (!items.get(position).getChecked()) {
                mCheckedTextView.setChecked(false);}
            else {
                mCheckedTextView.setChecked(true);
            }
            mCheckedTextView.setText(String.valueOf(items.get(position).getPosition()));
        }

        private ExerciseViewHolder(View itemView, TrainingCreateAdapter.ExerciseClickListener exerciseClickListener) {
            super(itemView);

            mCheckedTextView = itemView.findViewById(R.id.list_item_exercise_with_checkbox_image);
            itemView.setOnClickListener(this);

            this.exerciseName = itemView.findViewById(R.id.checked_text_item);
            this.imageView =itemView.findViewById(R.id.list_item_exercise_with_checkbox_image);

            itemView.setOnClickListener( vE -> {
                int adapterPosition = getAdapterPosition();
                if (!items.get(adapterPosition).getChecked()) {
                    mCheckedTextView.setChecked(true);
                    items.get(adapterPosition).setChecked(true);
                    checkedExerciseIdList.add(new Integer(adapterPosition+1));
                }
                else  {
                    mCheckedTextView.setChecked(false);
                    items.get(adapterPosition).setChecked(false);
                    checkedExerciseIdList.remove(new Integer(adapterPosition+1));
                }

                exerciseClickListener.onClick( this.currentExerciseId );
            });
        }
    }

    private final LayoutInflater inflater;
    private List<ExerciseWithMuscleGroup> exerciseList;

    private final TrainingCreateAdapter.ExerciseClickListener exerciseClickListener;
    //  MuscleGroupRepository muscleGroupRepository = MuscleGroupRepository.getRepository(this.)
    private List<MuscleGroup> muscleGroupList;


    public TrainingCreateAdapter(Context context, TrainingCreateAdapter.ExerciseClickListener exerciseClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.exerciseClickListener = exerciseClickListener;
    }

    @NonNull
    @Override
    public TrainingCreateAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_exercise_with_checkbox, parent, false);

        Log.i( "OnCreateViewHolder", "Count: " + ++TrainingCreateAdapter.counter);

        return new TrainingCreateAdapter.ExerciseViewHolder(itemView, this.exerciseClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingCreateAdapter.ExerciseViewHolder holder, int position) {

        if (this.exerciseList != null && !this.exerciseList.isEmpty()) {
            ExerciseWithMuscleGroup current = this.exerciseList.get(position);
            //        LiveData<MuscleGroup> muscleGroup = muscleGroupDao.geMuscleGroupById(current.getMuscleGroups().get(0).getMuscleGroupId());

            holder.exerciseName.setText(String.format("%s", current.getExercise().getDesignation()));

            holder.currentExerciseId = current.getExercise().getExerciseId();

            holder.imageView.setImageResource(current.getExercise().getProfileImageId());
            String Bla = String.valueOf(current.getExercise().getProfileImageId());

            holder.bind(position);

        }
        else {
            // Covers the case of DataForDB not being ready yet.
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

    void loadItems(List<Model> tournaments) {
        this.items = tournaments;
        for (int x = 0; x <=100; x++) {
            Model model = new Model();
            model.setPosition(x+1);

            items.add(model);
        }
        notifyDataSetChanged();
    }

}
