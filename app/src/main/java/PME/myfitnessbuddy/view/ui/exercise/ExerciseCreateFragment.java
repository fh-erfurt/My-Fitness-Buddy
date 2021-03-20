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

import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * fragment to create a new exercise
 * user input necessary data
 * */

public class ExerciseCreateFragment extends BaseFragment implements View.OnClickListener {

    View root;

    EditText exerciseName;
    EditText exerciseDescription;
    Spinner exercisePicture;

    CheckBox legs;
    CheckBox chest;
    CheckBox back;
    CheckBox biceps;

    List <String> muscleGroupList = new ArrayList();

    public ExerciseCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        exerciseName = (EditText) root.findViewById(R.id.createExerciseName);
        exerciseDescription = (EditText) root.findViewById(R.id.createExerciseDescription);
        exercisePicture = (Spinner) root.findViewById(R.id.createExerciseMuscleGroup);
        
        legs = (CheckBox) root.findViewById(R.id.checkboxLegs);
        biceps = (CheckBox) root.findViewById(R.id.checkboxBizeps);
        chest = (CheckBox) root.findViewById(R.id.checkboxChest);
        back = (CheckBox) root.findViewById(R.id.checkboxBack);

        legs.setOnClickListener(this::onCheckboxClicked);
        biceps.setOnClickListener(this::onCheckboxClicked);
        chest.setOnClickListener(this::onCheckboxClicked);
        back.setOnClickListener(this::onCheckboxClicked);

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

    /**
     *
     * the exercise is saved with the associated muscle groups,
     * then navigate to the exercise list
     */
    @Override
    public void onClick(View v) {

        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);

        long exerciseId = insertExercise(exerciseViewModel);

        long muscleGroupId;

        for (int i = 0; i<muscleGroupList.size(); i++){

            List<MuscleGroup> muscleGroups = exerciseViewModel.getMuscleGroupForDesignation(muscleGroupList.get(i));
            muscleGroupId = muscleGroups.get(0).getMuscleGroupId();

            inserExerciseMuscleGroupCrossRef(exerciseId, muscleGroupId, exerciseViewModel);

        }

        Navigation.findNavController(v).navigate(R.id.action_createExerciseFragment_to_fragment_exercise);

    }

    public long insertExercise(ExerciseViewModel exerciseViewModel){

        Exercise exercise = new Exercise(exerciseName.getText().toString() ,exerciseDescription.getText().toString(),0);
        exercise.setCreated( System.currentTimeMillis() );
        exercise.setProfileImageId( exercise.checkImgAndGetId(exercisePicture.getSelectedItem().toString()));
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( 1 );

        return exerciseViewModel.insertExercise(exercise);
    }

    public void inserExerciseMuscleGroupCrossRef(long exerciseId, long muscleGroupId, ExerciseViewModel exerciseViewModel){

        ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef = new ExerciseMuscleGroupCrossRef(exerciseId, muscleGroupId);
        exerciseMuscleGroupCrossRef.setCreated( System.currentTimeMillis() );
        exerciseMuscleGroupCrossRef.setModified( exerciseMuscleGroupCrossRef.getCreated() );
        exerciseMuscleGroupCrossRef.setVersion( 1 );
        exerciseViewModel.insertExerciseCrossRef(exerciseMuscleGroupCrossRef);

    }

    /**
     * which muscle groups belong to the exercise
     */
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
                else{
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