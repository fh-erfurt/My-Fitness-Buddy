package PME.myfitnessbuddy.view.ui.training;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.training.TrainingWithExercise;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingListFragment} factory method to
 * create an instance of this fragment.
 */
public class TrainingListFragment extends BaseFragment  {

    private TrainingListViewModel trainingListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_traininglist, container, false);

        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.btnToTrainingCreate);
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragment_traininglist_to_trainingCreateFragment, null));

        trainingListViewModel = this.getViewModel(TrainingListViewModel.class);
        RecyclerView trainingListView = v.findViewById(R.id.list_view_trainings);

        final TrainingListAdapter adapter = new TrainingListAdapter(this.requireActivity(),
        trainingId -> {
            Bundle args = new Bundle();
            args.putLong("trainingId", trainingId);
            NavController nc = NavHostFragment.findNavController( this );
            nc.navigate( R.id.action_fragment_traininglist_to_fragment_trainingdetails, args );
        });

        trainingListView.setAdapter( adapter );

        SelectionTracker<Long> tracker = new SelectionTracker.Builder<>(
                "mySelectionId",
                trainingListView,
                new StableIdKeyProvider(trainingListView),
                new TrainingItemDetailsLookup(trainingListView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(
                        SelectionPredicates.createSelectAnything()
                )
                .build();

        tracker.addObserver( new TrainingSelectionObserver(tracker, adapter) );
        adapter.setSelectionTracker( tracker );


        trainingListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

        trainingListViewModel.getTrainings().observe(this.requireActivity(), trainings -> {

            adapter.submitList( trainings );
        });

        return v;

    }

    private class TrainingSelectionObserver extends SelectionTracker.SelectionObserver<Long> {

        private final SelectionTracker<Long> tracker;
        private final TrainingListAdapter adapter;
        ActionMode mode;

        public TrainingSelectionObserver(SelectionTracker<Long> tracker, TrainingListAdapter adapter) {
            this.tracker = tracker;
            this.adapter = adapter;
        }

        @Override
        public void onSelectionChanged() {
            super.onSelectionChanged();

            if (mode != null) return;

            mode = requireActivity().startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.list_action_mode_menu, menu);
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                    if (item.getItemId() == R.id.list_action_delete) {
                        trainingListViewModel.deleteTrainings( getSelectedTrainings() );
                        tracker.clearSelection();
                        return true;
                    }else if (item.getItemId() == R.id.list_action_cancel) {
                        tracker.clearSelection();
                        return true;
                    }

                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        @Override
        protected void onSelectionCleared() {
            if (mode != null) mode.finish();
            mode = null;
        }

        private List<TrainingWithExercise> getSelectedTrainings() {
            List<TrainingWithExercise> selectedContacts = new ArrayList<>(tracker.getSelection().size());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tracker.getSelection().iterator().forEachRemaining(aLong -> {
                    selectedContacts.add(adapter.getTraining(aLong.intValue()));
                });
            }

            return selectedContacts;
        }
    }

}