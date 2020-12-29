package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.javafaker.Faker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;

import java.util.List;

import PME.myfitnessbuddy.model.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.ExerciseCrossRefRepository;
import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.MuscleGroupRepository;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**

 * create an instance of this fragment.
 *
 */
public class createExerciseFragment extends BaseFragment implements View.OnClickListener {

    View root;

    EditText createExerciseName;
    EditText createExerciseDescription;
    Spinner createExerciseMuscleGroup;



    public createExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        createExerciseName = (EditText) root.findViewById(R.id.createExerciseName);
        createExerciseDescription = (EditText) root.findViewById(R.id.createExerciseDescription);
        createExerciseMuscleGroup = (Spinner) root.findViewById(R.id.createExerciseMuscleGroup);

        createExerciseName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        createExerciseDescription.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        Button button = (Button) root.findViewById(R.id.btnCreateExercise);
        button.setOnClickListener(this::onClick);
        // Inflate the layout for this fragment
        return root;
    }


    @Override
    public void onClick(View v) {


        String name = createExerciseName.getText().toString();
        String description = createExerciseDescription.getText().toString();
        String muscleGroupString = createExerciseMuscleGroup.getSelectedItem().toString();

        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);


        Faker faker = Faker.instance();

/*
        MuscleGroup muscleGroup = new MuscleGroup(muscleGroupString);
        muscleGroup.setCreated( System.currentTimeMillis() );
        muscleGroup.setProfileImageUrl( muscleGroupString );
        muscleGroup.setModified( muscleGroup.getCreated() );
        muscleGroup.setVersion( 1 );
*/
        List<MuscleGroup> muscleGroups = exerciseViewModel.getMuscleGroupForDesignation(muscleGroupString);

        Exercise exercise = new Exercise(name,description);
        exercise.setCreated( System.currentTimeMillis() );
        //exercise.setProfileImageUrl( faker.avatar().image() );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( 1 );

        long exerciseId = exerciseViewModel.insertExercise(exercise);
        long muscleGroupId = muscleGroups.get(0).getMuscleGroupId();

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef = new ExerciseMuscleGroupCrossRef(exerciseId, muscleGroupId);
        exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
        //exercise.setProfileImageUrl( faker.avatar().image() );
        exerciseMuscleGroupCrossRef.setModified( exerciseMuscleGroupCrossRef.getCreated() );
        exerciseMuscleGroupCrossRef.setVersion( 1 );
        exerciseViewModel.insertExerciseCrossRef(exerciseMuscleGroupCrossRef);


        Navigation.findNavController(v).navigate(R.id.action_createExerciseFragment_to_fragment_exercise);

    }


}