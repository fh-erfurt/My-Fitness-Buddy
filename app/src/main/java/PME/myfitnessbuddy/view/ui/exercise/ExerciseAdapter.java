package PME.myfitnessbuddy.view.ui.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;

public class ExerciseAdapter extends ListAdapter<ExerciseWithMuscleGroup, ExerciseAdapter.ExerciseViewHolder> {
    public interface ExerciseClickListener {
        void onClick(long contactId);
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseName;
        private final ImageView imageView;

        private long currentExerciseId = -1;

        private ExerciseViewHolder(View itemView, ExerciseClickListener exerciseClickListener) {
            super(itemView);
            this.exerciseName = itemView.findViewById(R.id.list_item_exercise_name);
            this.imageView =itemView.findViewById(R.id.list_item_exercise_image);

            itemView.setOnClickListener( v -> {
                exerciseClickListener.onClick( this.currentExerciseId);
            });
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return new ItemDetailsLookup.ItemDetails<Long>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Nullable
                @Override
                public Long getSelectionKey() {
                    return getItemId();
                }
            };
        }
    }

    private final LayoutInflater inflater;
    private List<ExerciseWithMuscleGroup> exerciseList;
    private final ExerciseClickListener exerciseClickListener;    // Our listener for item taps
    private SelectionTracker<Long> selectionTracker;            // A helper to track selected items

    public ExerciseAdapter(Context context, ExerciseClickListener exerciseClickListener) {
        super(new ExerciseListDiffCallback());
        this.inflater = LayoutInflater.from(context);
        this.exerciseClickListener = exerciseClickListener;
        this.setHasStableIds( true );
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_exercise, parent, false);
        return new ExerciseViewHolder(itemView, this.exerciseClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseWithMuscleGroup current = this.getItem(position);

        holder.currentExerciseId = current.getExerciseId();
        holder.exerciseName.setText(String.format("%s", current.getExercise().getDesignation()));
        holder.imageView.setImageResource(current.getExercise().getProfileImageId());

        // Check if the item is selected - if so mark it
        if( !selectionTracker.isSelected( (long)position ) ){
           // holder.imageView.setImageResource(current.getExercise().getProfileImageId());

        }
        else{
          //  holder.imageView.setImageResource( R.drawable.ic_baseline_check_24 );
        }

        // Set Activated will trigger a different background color - see drawable/list_item_background.xml
        holder.itemView.setActivated( selectionTracker.isSelected( (long)position ) );


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ExerciseWithMuscleGroup getExercise(int position )
    {
        return getItem(position);
    }

    public void setExercises(List<ExerciseWithMuscleGroup> exerciseList){
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }


}
