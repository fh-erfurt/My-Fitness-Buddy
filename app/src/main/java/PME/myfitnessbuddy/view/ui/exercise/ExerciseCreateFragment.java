package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;

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

import com.github.javafaker.Faker;
import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**

 * create an instance of this fragment.
 *
 */
public class ExerciseCreateFragment extends BaseFragment implements View.OnClickListener {

    View root;

    EditText exerciseName;
    EditText exerciseDescription;
    Spinner exercisePicture;

    CheckBox beine;
    CheckBox brust;
    CheckBox rücken;
    CheckBox bizeps;

    List <String> muscleGroupList = new ArrayList();



    public ExerciseCreateFragment() {
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

        exerciseName = (EditText) root.findViewById(R.id.createExerciseName);
        exerciseDescription = (EditText) root.findViewById(R.id.createExerciseDescription);
        exercisePicture = (Spinner) root.findViewById(R.id.createExerciseMuscleGroup);

        beine = (CheckBox) root.findViewById(R.id.checkboxLegs);
        bizeps = (CheckBox) root.findViewById(R.id.checkboxBizeps);
        brust = (CheckBox) root.findViewById(R.id.checkboxChest);
        rücken = (CheckBox) root.findViewById(R.id.checkboxBack);

        beine.setOnClickListener(this::onCheckboxClicked);
        bizeps.setOnClickListener(this::onCheckboxClicked);
        brust.setOnClickListener(this::onCheckboxClicked);
        rücken.setOnClickListener(this::onCheckboxClicked);

        exerciseName.addTextChangedListener(new TextWatcher() {

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



        exerciseDescription.addTextChangedListener(new TextWatcher() {

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


        String name = exerciseName.getText().toString();
        String description = exerciseDescription.getText().toString();
        String pictureName = exercisePicture.getSelectedItem().toString();

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
        exercise.setProfileImageId( exercise.checkImgAndGetId(pictureName) );
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
            case R.id.checkboxBizeps:
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
            case R.id.checkboxBack:
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
            case R.id.checkboxLegs:
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
            case R.id.checkboxChest:
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