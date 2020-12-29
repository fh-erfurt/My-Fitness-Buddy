package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.javafaker.Faker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.model.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.ExerciseCrossRefRepository;
import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.MuscleGroupRepository;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link createExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class createExerciseFragment extends BaseFragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment createExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static createExerciseFragment newInstance(String param1, String param2) {
        createExerciseFragment fragment = new createExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public createExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        Button button = (Button) root.findViewById(R.id.btnCreateExercise);
        button.setOnClickListener(this::onClick);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onClick(View v) {
        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);


        Faker faker = Faker.instance();


        MuscleGroup muscleGroup = new MuscleGroup(faker.chuckNorris().fact());
        muscleGroup.setCreated( System.currentTimeMillis() );
        muscleGroup.setProfileImageUrl( R.drawable.info );
        muscleGroup.setModified( muscleGroup.getCreated() );
        muscleGroup.setVersion( 1 );


        Exercise exercise = new Exercise(faker.pokemon().name(),faker.pokemon().location());
        exercise.setCreated( System.currentTimeMillis() );
        //exercise.setProfileImageUrl( faker.avatar().image() );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( 1 );

        long exerciseId = exerciseViewModel.insertExercise(exercise);
        long muscleGroupId = exerciseViewModel.insertMuscleGroup(muscleGroup);

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef = new ExerciseMuscleGroupCrossRef(exerciseId, muscleGroupId);
        exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
        //exercise.setProfileImageUrl( faker.avatar().image() );
        exerciseMuscleGroupCrossRef.setModified( exerciseMuscleGroupCrossRef.getCreated() );
        exerciseMuscleGroupCrossRef.setVersion( 1 );
        exerciseViewModel.insertExerciseCrossRef(exerciseMuscleGroupCrossRef);




        Navigation.findNavController(v).navigate(R.id.action_createExerciseFragment_to_fragment_exercise);

    }
}