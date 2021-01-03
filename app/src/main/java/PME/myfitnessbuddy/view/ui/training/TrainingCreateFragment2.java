package PME.myfitnessbuddy.view.ui.training;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.myfitnessbuddy.R;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseAdapter;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseViewModel;

import static PME.myfitnessbuddy.view.ui.training.TrainingCreateAdapter.items;


public class TrainingCreateFragment2 extends BaseFragment implements SelectableViewHolder.OnItemSelectedListener ,View.OnClickListener {

    private SharedViewModel viewModel;

    private   SelectableAdapter   adapter;

    List<Exercise> selectedItems;

    ExerciseViewModel exerciseViewModel;

    String designation;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "trainingName";

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString("trainingName");
            Log.println(Log.INFO,"Test","Teeeeesssst111111111");
        }
        Log.println(Log.INFO,"Test","Teeeeesssst");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        exerciseViewModel = this.getViewModel(ExerciseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_training_create2, container, false);

        RecyclerView recyclerView =  root.findViewById(R.id.selection_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        List<Exercise> selectableItems = generateItems();
        List<Exercise> selectableItecsacsms = exerciseViewModel.getdgdsgfdgs();
        adapter = new SelectableAdapter(this.requireActivity(), this,selectableItems,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));

        List<MuscleGroup> muscleGroups = exerciseViewModel.getMuscleGroupForDesignation("Beine");

        Bundle bundle = getArguments();

        if (mParam1 != null) {
            designation = mParam1;
        }
        else {
            designation = "test";
        }

       // exerciseViewModel.getdgdsgfdgs().observe(this.requireActivity(), adapter::setExercises);

/*
        RecyclerView exerciseListView = root.findViewById(R.id.exercisesWithCheckbox);

        final TrainingCreateAdapter adapter = new TrainingCreateAdapter(this.requireActivity(),
                exerciseId -> {
                    Bundle args = new Bundle();
                    args.putLong("exerciseId", exerciseId);
     //               NavController nc = NavHostFragment.findNavController( this );
     //               nc.navigate( R.id.action_fragment_exercise_to_fragment_exercisedetail , args );
                });


 */
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

            }
        });
/*
        exerciseListView.setAdapter( adapter );
        exerciseListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

       trainingCreateViewModel.getExercises().observe(this.requireActivity(), adapter::setExercises);
        // Inflate the layout for this fragment

        for (int x = 0; x <= 100; x++) {
            Model model = new Model();
            model.setPosition(x+1);

            items.add(model);
        }

        adapter.loadItems(items);

 */

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.btnCreateTraining);
        button.setOnClickListener(this::onClick);

        return root;
    }

    @Override
    public void onItemSelected(Exercise selectableItem) {

        selectedItems = adapter.getSelectedItems();

    }

    public List<Exercise> generateItems(){
        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);

        return exerciseViewModel.getdgdsgfdgs();
    }

    @Override
    public void onClick(View v) {

        String category = viewModel.getText().getValue().toString();


        TrainingListViewModel trainingListViewModel = this.getViewModel(TrainingListViewModel.class);

        Training training = new Training(designation,category);
        training.setCreated( System.currentTimeMillis() );
        training.setModified( training.getCreated() );
        training.setVersion( 1 );

        long trainingId = trainingListViewModel.insertTraining(training);

        for (int i = 0; i<selectedItems.size(); i++){


           long exerciseId = selectedItems.get(i).getExerciseId();

            TrainingExerciseCrossRef trainingExerciseCrossRef = new TrainingExerciseCrossRef(trainingId, exerciseId);
            trainingExerciseCrossRef.setCreated( System.currentTimeMillis() );
            //exercise.setProfileImageUrl( faker.avatar().image() );
            trainingExerciseCrossRef.setModified( trainingExerciseCrossRef.getCreated() );
            trainingExerciseCrossRef.setVersion( 1 );
            trainingListViewModel.insertExerciseCrossRef(trainingExerciseCrossRef);

        }

        Navigation.findNavController(v).navigate(R.id.action_trainingCreateFragment2_to_fragment_traininglist);
    }


}