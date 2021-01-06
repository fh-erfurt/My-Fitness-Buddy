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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public TrainingCreateFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TrainingCreateFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static TrainingCreateFragment2 newInstance(String param1) {
        TrainingCreateFragment2 fragment = new TrainingCreateFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mParam1 = bundle.getString("param1");
        }
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
        Training training = new Training(designation,category);
        training.setCreated( System.currentTimeMillis() );
        training.setModified( training.getCreated() );
        training.setVersion( 1 );


        long trainingId = trainingListViewModel.insertTraining(training);

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

        Navigation.findNavController(v).navigate(R.id.action_trainingCreateFragment2_to_fragment_traininglist);
    }

}