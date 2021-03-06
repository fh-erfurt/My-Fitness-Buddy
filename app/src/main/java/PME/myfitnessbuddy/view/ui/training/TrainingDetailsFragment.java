package PME.myfitnessbuddy.view.ui.training;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseAdapter;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingDetailsFragment extends BaseFragment {

    public static final String ARG_TRAINING_ID = "trainingId";
    public static final String ARG_UPDATED_TRAINING_ID="updatedTrainingId";
    public long updateTrainingId;
    private TrainingDetailsViewModel trainingDetailsViewModel;
    private LiveData<Training> trainingLiveData;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        exerciseListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));
        exerciseViewModel.getExercisesFromTraining((int) trainingId).observe(this.requireActivity(), adapter::setExercises);

        return root;
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

        Log.i("EventCallbacks", "Stopped observing Detail Contact");
    }



}