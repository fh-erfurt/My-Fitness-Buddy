package PME.myfitnessbuddy.view.ui.training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.model.training.TrainingWithExercise;


import java.util.List;

/**
 * Training list Adapter for RecyclerView
 * */

public class TrainingListAdapter extends ListAdapter<TrainingWithExercise, TrainingListAdapter.TrainingViewHolder> {

    public interface TrainingClickListener {
        void onClick(long trainingId);
    }

    static class TrainingViewHolder extends RecyclerView.ViewHolder {

        private final TextView trainingName;
        private final TextView trainingCategory;
        private final TextView countedExercises;
        private long currentTrainingId = -1;

        private TrainingViewHolder(View itemView, TrainingClickListener trainingClickListener) {
            super(itemView);

            this.trainingName = itemView.findViewById(R.id.list_item_training_name);
            this.trainingCategory = itemView.findViewById(R.id.list_item_training_category);
            this.countedExercises =itemView.findViewById(R.id.list_item_counted_exercises);

            itemView.setOnClickListener( v -> {
                trainingClickListener.onClick( this.currentTrainingId);
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
    private List<TrainingWithExercise> trainingList;
    private final TrainingClickListener trainingClickListener;
    private SelectionTracker<Long> selectionTracker;

    public TrainingListAdapter(Context context, TrainingClickListener trainingClickListener) {
        super(new TrainingListDiffCallback());
        this.inflater = LayoutInflater.from(context);
        this.trainingClickListener = trainingClickListener;
        this.setHasStableIds( true );
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.list_item_training, parent, false);

        return new TrainingViewHolder(itemView, this.trainingClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {

        TrainingWithExercise current = this.getItem(position);

        holder.currentTrainingId = current.getTrainingId();

            holder.trainingName.setText(String.format("%s", current.getTraining().getDesignation()));
            holder.trainingCategory.setText(String.format("%s", current.getTraining().getCategory()));
            holder.countedExercises.setText(String.format("%s" ,current.exercises.size()));

        // Check if the item is selected - if so mark it
        if( !selectionTracker.isSelected( (long)position ) ){

        }
        else{

        }

        // Set Activated will trigger a different background color - see drawable/list_item_background.xml
        holder.itemView.setActivated( selectionTracker.isSelected( (long)position ) );

    }

    public long getItemId(int position) {
        return position;
    }

    public TrainingWithExercise getTraining(int position )
    {
        return getItem(position);
    }

    public void setTrainings(List<TrainingWithExercise> trainingList){
        this.trainingList = trainingList;
        notifyDataSetChanged();
    }



}
