package PME.myfitnessbuddy.view.ui.training;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.model.exercise.Exercise;

public class SelectableViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    CheckedTextView textView;
    ImageView imageView;
    Exercise exercise;
    OnItemSelectedListener exerciseSelectedListener;


    public SelectableViewHolder(View view, OnItemSelectedListener listener) {
        super(view);
        exerciseSelectedListener = listener;
        textView = (CheckedTextView) view.findViewById(R.id.checked_text_item);
        imageView = (ImageView) view.findViewById(R.id.list_item_exercise_with_checkbox_image);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (exercise.isSelected() && getItemViewType() == MULTI_SELECTION) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }
                exerciseSelectedListener.onItemSelected(exercise);

            }
        });
    }

    public void setChecked(boolean value) {
        if (value) {
            textView.setBackgroundColor(Color.LTGRAY);
        } else {
            textView.setBackground(null);
        }
        exercise.setSelected(value);
        textView.setChecked(value);
    }

    public interface OnItemSelectedListener {

        void onItemSelected(Exercise item);
    }

}
