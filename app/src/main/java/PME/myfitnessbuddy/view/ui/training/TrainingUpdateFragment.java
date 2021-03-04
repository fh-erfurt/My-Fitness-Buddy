package PME.myfitnessbuddy.view.ui.training;

import android.animation.ArgbEvaluator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseViewModel;

public class TrainingUpdateFragment extends BaseFragment implements SelectableViewHolder.OnItemSelectedListener ,View.OnClickListener {

    private SharedViewModel viewModel;
    // counter for training without name
    static int counter = 1;
    public static final String ARG_UPDATE_TRAINING_ID = "trainingId";

    private   SelectableAdapter   adapter;
    List<Exercise> selectedExercises;
    ExerciseViewModel exerciseViewModel;
    String designation,category;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private long mParam1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mParam1 = bundle.getLong(ARG_UPDATE_TRAINING_ID);
        }
    }



    public TrainingUpdateFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        exerciseViewModel = this.getViewModel(ExerciseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_training_update, container, false);

        RecyclerView recyclerView =  root.findViewById(R.id.selection_list);


        List<Exercise> selectableExercises = generateListFromAllExercises();
        adapter = new SelectableAdapter(this.requireActivity(), this,selectableExercises,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getTrainingsName().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

            }
        });


        viewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

            }
        });

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.btnUpdateTraining);
        button.setOnClickListener(this::onClick);

        return root;
    }


    @Override
    public void onItemSelected(Exercise selectableItem) {

        selectedExercises = adapter.getSelectedItems();

    }



    public List<Exercise> generateListFromAllExercises(){
        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);


        assert getArguments() != null;
        final long trainingId = getArguments().getLong(ARG_UPDATE_TRAINING_ID);

        return exerciseViewModel.getExercisesWhichAreNotInTrainingFromRepo((int) trainingId);
    }

    @Override
    public void onClick(View v) {

        assert getArguments() != null;
        final long trainingId = getArguments().getLong(ARG_UPDATE_TRAINING_ID);
        TrainingListViewModel trainingListViewModel = this.getViewModel(TrainingListViewModel.class);

        if(selectedExercises != null) {
            for (int i = 0; i < selectedExercises.size(); i++) {

                long exerciseId = selectedExercises.get(i).getExerciseId();

                TrainingExerciseCrossRef trainingExerciseCrossRef = new TrainingExerciseCrossRef(trainingId, exerciseId);
                trainingExerciseCrossRef.setCreated(System.currentTimeMillis());
                trainingExerciseCrossRef.setModified(trainingExerciseCrossRef.getCreated());
                trainingExerciseCrossRef.setVersion(1);
                trainingListViewModel.insertExerciseCrossRef(trainingExerciseCrossRef);
            }
        }

        Bundle args = new Bundle();
        args.putLong("updatedTrainingId", trainingId);
        Navigation.findNavController(v).navigate(R.id.action_trainingUpdateFragment_to_fragment_trainingdetails,args);
    }

}