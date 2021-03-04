package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.javafaker.Faker;
import com.myfitnessbuddy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.exercise.ExerciseWithTrainingsLog;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetailsFragment extends BaseFragment implements View.OnClickListener{

    public static final String ARG_EXERCISE_ID = "exerciseId";
    private ExerciseDetailsViewModel viewModel;
    private LiveData<Exercise> exerciseLiveData;

    private List<TrainingsLog> sortedTrainingsLogs;
    private String lastLog = "";
    private String actualLog = "";

    private TextView textViewLastLog;
    private TextView textViewActualLog;

    EditText editTextRepetitions;
    EditText editTextWeight;

    private String actualDate;
    private SimpleDateFormat dateFormatDDMMYYYY_HHMM = new SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.GERMANY);
    private SimpleDateFormat dateFormatDDMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public ExerciseDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrainingList_TrainingDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseDetailsFragment newInstance(String param1, String param2) {
        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exercisedetail, container, false);
        viewModel = this.getViewModel( ExerciseDetailsViewModel.class );

        this.textViewLastLog = (TextView) root.findViewById(R.id.exercisedetail_last_log_textview);
        this.textViewActualLog = (TextView) root.findViewById(R.id.exercisedetail_today_log_textview);

        this.editTextRepetitions = (EditText) root.findViewById(R.id.exercisedetail_reps_input);
        this.editTextWeight = (EditText) root.findViewById(R.id.exercisedetail_weight_input);


        /////////////Baustelle Start/////////////////

        assert getArguments() != null;
        long exerciseId = getArguments().getLong(ARG_EXERCISE_ID);

        List  <ExerciseWithTrainingsLog> exerciseWithTrainingsLogs = viewModel.getExerciseWithTrainingsLogLiveDataByExerciseId(exerciseId);

        exerciseWithTrainingsLogs.get(0).sortTrainingsLog();

        sortedTrainingsLogs = exerciseWithTrainingsLogs.get(0).getTrainingsLog();

        Date dateObject = new Date();
        this.actualDate = dateFormatDDMMYYYY.format(dateObject);

        setLogEntrysFromTheLastDay();
        this.textViewLastLog.setText(this.lastLog);
        this.textViewActualLog.setText(this.actualLog);



        Button buttonEndTrainingSet = (Button) root.findViewById(R.id.exercisedetail_finish_set_button);

        buttonEndTrainingSet.setOnClickListener(this::onClick);

        editTextRepetitions.addTextChangedListener(new TextWatcher() {

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

        editTextWeight.addTextChangedListener(new TextWatcher() {

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

        /////////////Baustelle Ende/////////////////


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        assert getArguments() != null;
        final long exerciseId = getArguments().getLong(ARG_EXERCISE_ID);
        this.exerciseLiveData = this.viewModel.getExercise( exerciseId );
        this.exerciseLiveData.observe( requireActivity(), this::updateView);

        Log.i("EventCallbacks", "Observing Detail Contact");

    }

    private void updateView(Exercise exercise) {

        Log.i("EventCallbacks", "Update Detail View with Training: " + exercise);

        assert getView() != null;

        //ToDo Fragment
        TextView nameView = getView().findViewById( R.id.fragment_exercise_id );

        nameView.setText(String.format("%s %s", exercise.getDesignation(), "  "));


    }

    @Override
    public void onPause() {
        super.onPause();

        this.exerciseLiveData.removeObservers(requireActivity());

        Log.i("EventCallbacks", "Stopped observing Detail Contact");
    }


    //////////////Benjamin///////////////
    @Override
    public void onClick(View v) {

        String repetition = this.editTextRepetitions.getText().toString();
        String weight = this.editTextWeight.getText().toString();
        String alternativeText = ""; //////////////TODO/////////////

        assert getArguments() != null;
        long exerciseId = getArguments().getLong(ARG_EXERCISE_ID);

        TrainingsLog trainingsLog = new TrainingsLog(exerciseId,repetition,weight,alternativeText);
        trainingsLog.setCreated( System.currentTimeMillis() );
        trainingsLog.setModified( trainingsLog.getCreated() );
        trainingsLog.setVersion( 1 );

        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);

        exerciseViewModel.insertTrainingsLog(trainingsLog);

        List  <ExerciseWithTrainingsLog> exerciseWithTrainingsLogs = viewModel.getExerciseWithTrainingsLogLiveDataByExerciseId(exerciseId);

        exerciseWithTrainingsLogs.get(0).sortTrainingsLog();

        sortedTrainingsLogs = exerciseWithTrainingsLogs.get(0).getTrainingsLog();

        this.actualLog = addLog("", 0)+"\n\n"+ actualLog;
        this.textViewActualLog.setText(actualLog);

    }

    public void createTrainingLog(){
        ///////////TODO
    }

    public void setTextViewsAfterEndTrainingSet(){
        ///////////TODO
    }

    public void setRecordToday(){
        ////////Todo
    }

    public void setLogEntrysFromTheLastDay(){

        int maxNumberOfEntrys = 5;
        String firstRound = "";

        for(int i = 0; i< this.sortedTrainingsLogs.size(); i++){

            String logEntryDate = this.dateFormatDDMMYYYY.format(this.sortedTrainingsLogs.get(i).getCreated());

            if(this.actualDate.equals(logEntryDate)){
               this.actualLog += addLog(firstRound, i);
                firstRound = "\n\n";
                continue;
            }

            firstRound = "";
            for(int j = 0; j< maxNumberOfEntrys; j++, i++){


                if(i >= this.sortedTrainingsLogs.size()){
                    break;
                }

               String logEntryDate2 = this.dateFormatDDMMYYYY.format(this.sortedTrainingsLogs.get(i).getCreated());

                if(!logEntryDate2.equals(logEntryDate)){
                    break;
                }

                this.lastLog += addLog(firstRound, i);
                firstRound = "\n\n";
            }
                break;
        }

    }

    public String addLog(String firstRound, int i){
        return firstRound+this.dateFormatDDMMYYYY_HHMM.format(this.sortedTrainingsLogs.get(i).getCreated()) + " Uhr"
                + "\nrepetitions: "+this.sortedTrainingsLogs.get(i).getRepetitions()
                +", weight: "+this.sortedTrainingsLogs.get(i).getWeight()+", text:"+this.sortedTrainingsLogs.get(i).getAlternativeText();
    }


    ///////////////////////////////////////////////


}