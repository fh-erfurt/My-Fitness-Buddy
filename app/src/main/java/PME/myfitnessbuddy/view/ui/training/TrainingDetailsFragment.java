package PME.myfitnessbuddy.view.ui.training;

import android.os.Build;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseAdapter;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseItemDetailsLookup;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseViewModel;

/**
 * training detail fragment shows the details from an clicked training
 * */

public class TrainingDetailsFragment extends BaseFragment {

    public static final String ARG_TRAINING_ID = "trainingId";
    public static final String ARG_UPDATED_TRAINING_ID="updatedTrainingId";

    private TrainingDetailsViewModel trainingDetailsViewModel;
    private LiveData<Training> trainingLiveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert getArguments() != null;
        long trainingId = getArguments().getLong(ARG_TRAINING_ID);
        if(trainingId == 0){
            trainingId = getArguments().getLong(ARG_UPDATED_TRAINING_ID);
        }

        View root = inflater.inflate(R.layout.fragment_trainingdetails, container, false);
        trainingDetailsViewModel = this.getViewModel( TrainingDetailsViewModel.class );
        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.btnAddExercise);

        Bundle args = new Bundle();
        args.putLong("trainingId", trainingId);

        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.trainingUpdateFragment, args));

        RecyclerView exerciseListView = root.findViewById(R.id.exercises);

        final ExerciseAdapter adapter = new ExerciseAdapter(this.requireActivity(),
                exerciseId -> {
                    Bundle args1 = new Bundle();
                    args1.putLong("exerciseId", exerciseId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_fragment_trainingdetails_to_fragment_exercisedetail , args1 );
                });


        exerciseListView.setAdapter( adapter );

        SelectionTracker<Long> tracker = new SelectionTracker.Builder<>(
                "mySelectionId",
                exerciseListView,
                new StableIdKeyProvider(exerciseListView),
                new ExerciseItemDetailsLookup(exerciseListView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(
                        SelectionPredicates.createSelectAnything()
                )
                .build();

        tracker.addObserver( new TrainingDetailsFragment.ExerciseSelectionObserver(tracker, adapter) );
        adapter.setSelectionTracker( tracker );

        exerciseListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

        exerciseViewModel.getExercisesFromTraining((int) trainingId).observe(this.requireActivity(), exercises -> {

            adapter.submitList( exercises );
        });

        return root;
    }

    private class ExerciseSelectionObserver extends SelectionTracker.SelectionObserver<Long> {

        private final SelectionTracker<Long> tracker;
        private final ExerciseAdapter adapter;
        ActionMode mode;

        public ExerciseSelectionObserver(SelectionTracker<Long> tracker, ExerciseAdapter adapter) {
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
                       trainingDetailsViewModel.deleteTrainingExerciseCrossRefs(getArguments().getLong(ARG_TRAINING_ID) ,getSelectedExercises() );
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

        private List<ExerciseWithMuscleGroup> getSelectedExercises() {
            List<ExerciseWithMuscleGroup> selectedExercisesWithMuscleGroup = new ArrayList<>(tracker.getSelection().size());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tracker.getSelection().iterator().forEachRemaining(aLong -> {
                    selectedExercisesWithMuscleGroup.add(adapter.getExercise(aLong.intValue()));
                });
            }

            return selectedExercisesWithMuscleGroup;
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        assert getArguments() != null;
        long trainingId = getArguments().getLong(ARG_TRAINING_ID);
        if(trainingId == 0){
            trainingId = getArguments().getLong(ARG_UPDATED_TRAINING_ID);
        }
        this.trainingLiveData = this.trainingDetailsViewModel.getTraining( trainingId );
        this.trainingLiveData.observe( requireActivity(), this::updateView);

    }

   private void updateView(Training training) {

        assert getView() != null;
        TextView nameView = getView().findViewById( R.id.fragment_training_details_trainingname );
        nameView.setText(String.format("%s %s", training.getDesignation(), " "));

    }

    @Override
    public void onPause() {
        super.onPause();

        this.trainingLiveData.removeObservers(requireActivity());

    }



}