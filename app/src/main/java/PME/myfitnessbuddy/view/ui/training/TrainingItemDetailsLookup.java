package PME.myfitnessbuddy.view.ui.training;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView of exercises for Training Details
 * */

public class TrainingItemDetailsLookup extends ItemDetailsLookup<Long>{

    private final RecyclerView recyclerView;

    public TrainingItemDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetailsLookup.ItemDetails<Long> getItemDetails(@NonNull MotionEvent event) {
        View view = recyclerView.findChildViewUnder(event.getX(), event.getY());

        if (view != null) {
            TrainingListAdapter.TrainingViewHolder holder =
                    (TrainingListAdapter.TrainingViewHolder)recyclerView.getChildViewHolder(view);

            return holder.getItemDetails();
        }

        return null;
    }
}
