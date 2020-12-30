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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.javafaker.Faker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;

import java.util.ArrayList;
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

    CheckBox beine;
    CheckBox brust;
    CheckBox rücken;
    CheckBox bizeps;

    List <String> muscleGroupList = new ArrayList();



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

        beine = (CheckBox) root.findViewById(R.id.Beine);
        bizeps = (CheckBox) root.findViewById(R.id.Bizeps);
        brust = (CheckBox) root.findViewById(R.id.Brust);
        rücken = (CheckBox) root.findViewById(R.id.Rücken);

        beine.setOnClickListener(this::onCheckboxClicked);
        bizeps.setOnClickListener(this::onCheckboxClicked);
        brust.setOnClickListener(this::onCheckboxClicked);
        rücken.setOnClickListener(this::onCheckboxClicked);

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


        Exercise exercise = new Exercise(name,description);
        exercise.setCreated( System.currentTimeMillis() );
        exercise.setProfileImageUrl( exercise.checkImgAndGetId(muscleGroupString) );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( 1 );

        long exerciseId = exerciseViewModel.insertExercise(exercise);
        long muscleGroupId;

        for (int i = 0; i<muscleGroupList.size(); i++){

            List<MuscleGroup> muscleGroups = exerciseViewModel.getMuscleGroupForDesignation(muscleGroupList.get(i));
            muscleGroupId = muscleGroups.get(0).getMuscleGroupId();

            ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef = new ExerciseMuscleGroupCrossRef(exerciseId, muscleGroupId);
            exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
            //exercise.setProfileImageUrl( faker.avatar().image() );
            exerciseMuscleGroupCrossRef.setModified( exerciseMuscleGroupCrossRef.getCreated() );
            exerciseMuscleGroupCrossRef.setVersion( 1 );
            exerciseViewModel.insertExerciseCrossRef(exerciseMuscleGroupCrossRef);

        }

        Navigation.findNavController(v).navigate(R.id.action_createExerciseFragment_to_fragment_exercise);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.Bizeps:
                if (checked) {
                    if (!muscleGroupList.contains("Bizeps")) {
                        muscleGroupList.add("Bizeps");
                    }
                }

                 else {
                        if (muscleGroupList.contains("Bizeps")) {

                            muscleGroupList.remove("Bizeps");
                        }
                    }
                break;
            case R.id.Rücken:
                if (checked) {
                    if (!muscleGroupList.contains("Rücken")) {
                        muscleGroupList.add("Rücken");
                    }
                }
                    else {
                    if (muscleGroupList.contains("Rücken")) {

                        muscleGroupList.remove("Rücken");
                    }
                }
                break;
            case R.id.Beine:
                if (checked) {
                    if (!muscleGroupList.contains("Beine")) {
                        muscleGroupList.add("Beine");
                    }
                }

                    else {
                    if (muscleGroupList.contains("Beine")) {

                        muscleGroupList.remove("Beine");
                    }
                }
                break;
            case R.id.Brust:
                if (checked) {
                    if (!muscleGroupList.contains("Brust")) {
                        muscleGroupList.add("Brust");
                    }
                }

                    else {
                    if (muscleGroupList.contains("Brust")) {

                        muscleGroupList.remove("Brust");
                    }
                }
                break;

        }
    }



}