package PME.myfitnessbuddy.view.ui.training;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;

/**
 * Selectable Adapter
 * for delete training
 * check if training is selected
 * */

public class SelectableAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

    private List<Exercise> exercises;
    private boolean isMultiSelectionEnabled = true;
    SelectableViewHolder.OnItemSelectedListener exerciseSelectedListener;


    public SelectableAdapter(SelectableViewHolder.OnItemSelectedListener exerciseSelectedListener,
                             List<Exercise> items) {
        this.exerciseSelectedListener = exerciseSelectedListener;
        this.isMultiSelectionEnabled = true;

        exercises = new ArrayList<>();
        for (Exercise item : items) {
            exercises.add(item);
        }

    }

    @Override
    public SelectableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_exercise_with_checkbox, parent, false);

        return new SelectableViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SelectableViewHolder holder = (SelectableViewHolder) viewHolder;
        Exercise selectableItem = exercises.get(position);
        String name = selectableItem.getDesignation();
        holder.textView.setText(name);
        holder.imageView.setImageResource(selectableItem.getProfileImageId());

        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        }

        holder.exercise = selectableItem;
        holder.setChecked(holder.exercise.isSelected());
    }

    @Override
    public int getItemCount() {

        return exercises.size();
    }

    public List<Exercise> getSelectedItems() {

        List<Exercise> selectedItems = new ArrayList<>();
        for (Exercise item : exercises) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
            return SelectableViewHolder.MULTI_SELECTION;
        }
        else{
            return SelectableViewHolder.SINGLE_SELECTION;
        }
    }

    @Override
    public void onItemSelected(Exercise item) {
        if (!isMultiSelectionEnabled) {

            for (Exercise selectableExercise : exercises) {
                if (!selectableExercise.equals(item)
                        && selectableExercise.isSelected()) {
                    selectableExercise.setSelected(false);
                } else if (selectableExercise.equals(item)
                        && item.isSelected()) {
                    selectableExercise.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        exerciseSelectedListener.onItemSelected(item);
    }

    public void setExercises(List<Exercise> exerciseList){
        this.exercises = exerciseList;
        notifyDataSetChanged();
    }

}
