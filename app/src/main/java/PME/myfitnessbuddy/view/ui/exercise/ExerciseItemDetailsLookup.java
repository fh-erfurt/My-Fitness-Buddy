package PME.myfitnessbuddy.view.ui.exercise;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;


public class ExerciseItemDetailsLookup extends ItemDetailsLookup<Long>{

    private final RecyclerView recyclerView;

    public ExerciseItemDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent event) {
        View view = recyclerView.findChildViewUnder(event.getX(), event.getY());

        if (view != null) {
            ExerciseAdapter.ExerciseViewHolder holder =
                    (ExerciseAdapter.ExerciseViewHolder)recyclerView.getChildViewHolder(view);

           return holder.getItemDetails();
        }

        return null;
    }

}
