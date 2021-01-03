package PME.myfitnessbuddy.view.ui.training;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;

public class SelectableAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

    private List<Exercise> mValues;
    private boolean isMultiSelectionEnabled = true;
    SelectableViewHolder.OnItemSelectedListener listener;


    public SelectableAdapter(FragmentActivity itemView, SelectableViewHolder.OnItemSelectedListener listener,
                             List<Exercise> items, boolean isMultiSelectionEnabled) {
        this.listener = listener;
        this.isMultiSelectionEnabled = true;


        mValues = new ArrayList<>();
        for (Exercise item : items) {
            mValues.add(item);
        }



    }

    @Override
    public SelectableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_exercisewithcheckbox, parent, false);

        return new SelectableViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SelectableViewHolder holder = (SelectableViewHolder) viewHolder;
        Exercise selectableItem = mValues.get(position);
        String name = selectableItem.getDesignation();
        holder.textView.setText(name);
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

        holder.mItem = selectableItem;
        holder.setChecked(holder.mItem.isSelected());
    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public List<Exercise> getSelectedItems() {

        List<Exercise> selectedItems = new ArrayList<>();
        for (Exercise item : mValues) {
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

            for (Exercise selectableItem : mValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item);
    }

    public void setExercises(List<Exercise> exerciseList){
        this.mValues = exerciseList;
        notifyDataSetChanged();
    }
}
