package PME.myfitnessbuddy.view.ui.training;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseViewModel;


public class TrainingCreateFragment2 extends BaseFragment implements SelectableViewHolder.OnItemSelectedListener ,View.OnClickListener {

    private SharedViewModel viewModel;

    // counter for training without name
    static int counter = 1;

    private   SelectableAdapter   adapter;

    List<Exercise> selectedExercises;

    ExerciseViewModel exerciseViewModel;

    String designation,category;

    public TrainingCreateFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        exerciseViewModel = this.getViewModel(ExerciseViewModel.class);

        View root = inflater.inflate(R.layout.fragment_training_create2, container, false);

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

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.btnCreateTraining);
        button.setOnClickListener(this::onClick);

        return root;
    }

    @Override
    public void onItemSelected(Exercise selectableItem) {

        selectedExercises = adapter.getSelectedItems();

    }

    public List<Exercise> generateListFromAllExercises(){
        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);

        return exerciseViewModel.getExercisesFromRepo();
    }

    @Override
    public void onClick(View v) {

        category = viewModel.getCategory().getValue().toString();

        // if no name type in edittext we use a standard word
        if(!viewModel.getTrainingsName().getValue().toString().equals("")) {
            designation = viewModel.getTrainingsName().getValue().toString();
        }
        else {
            designation = "Training "+counter;
            counter++;
        }

        TrainingListViewModel trainingListViewModel = this.getViewModel(TrainingListViewModel.class);

        long trainingId =  insertTraining(trainingListViewModel);

        if(selectedExercises != null) {
            for (int i = 0; i < selectedExercises.size(); i++) {

                long exerciseId = selectedExercises.get(i).getExerciseId();

                inserTrainingExerciseCrossRef(trainingId, exerciseId, trainingListViewModel);
            }
        }

        Navigation.findNavController(v).navigate(R.id.action_trainingCreateFragment2_to_fragment_traininglist);
    }

    public long insertTraining(TrainingListViewModel trainingListViewModel){

        Training training = new Training(designation,category);
        training.setCreated( System.currentTimeMillis() );
        training.setModified( training.getCreated() );
        training.setVersion( 1 );

        return trainingListViewModel.insertTraining(training);
    }

    public void inserTrainingExerciseCrossRef(long trainingId, long exerciseId,TrainingListViewModel trainingListViewModel){

        TrainingExerciseCrossRef trainingExerciseCrossRef = new TrainingExerciseCrossRef(trainingId, exerciseId);
        trainingExerciseCrossRef.setCreated(System.currentTimeMillis());
        trainingExerciseCrossRef.setModified(trainingExerciseCrossRef.getCreated());
        trainingExerciseCrossRef.setVersion(1);
        trainingListViewModel.insertExerciseCrossRef(trainingExerciseCrossRef);

    }

}