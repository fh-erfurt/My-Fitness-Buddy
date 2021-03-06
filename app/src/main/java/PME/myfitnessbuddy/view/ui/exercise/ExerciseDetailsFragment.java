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

import com.myfitnessbuddy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private String lastLogDay = "";
    private int actualLogNumberOfSets = 0;

    private TextView textViewLastLog;
    private TextView textViewActualLog;

    EditText editTextRepetitions;
    EditText editTextWeight;

    private String actualDate;
    private SimpleDateFormat dateFormatDDMMYYYY_HHMM = new SimpleDateFormat("dd.MM.yyyy, k:mm:ss ", Locale.GERMAN);
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

        assert getArguments() != null;
        long exerciseId = getArguments().getLong(ARG_EXERCISE_ID);

        List <ExerciseWithTrainingsLog> exerciseWithTrainingsLogs = viewModel.getExerciseWithTrainingsLogLiveDataByExerciseId(exerciseId);

        exerciseWithTrainingsLogs.get(0).sortTrainingsLog();

        sortedTrainingsLogs = exerciseWithTrainingsLogs.get(0).getTrainingsLog();

        Date dateObject = new Date();
        this.actualDate = dateFormatDDMMYYYY.format(dateObject);

        setLogEntrys(root);
        this.textViewLastLog.setText(this.lastLog);
        this.textViewActualLog.setText(this.actualLog);


        Button buttonEndTrainingSet = (Button) root.findViewById(R.id.exercisedetail_finish_set_button);
        buttonEndTrainingSet.setOnClickListener(this::onClick);

        Button buttonRepsDecrease = (Button) root.findViewById(R.id.exercisedetail_reps_increase_button);
        buttonRepsDecrease.setOnClickListener(this::onClick);

        Button buttonRepsIncrease = (Button) root.findViewById(R.id.exercisedetail_reps_decrease_button);
        buttonRepsIncrease.setOnClickListener(this::onClick);

        Button buttonWeightIncrease = (Button) root.findViewById(R.id.exercisedetail_weight_increase_button);
        buttonWeightIncrease.setOnClickListener(this::onClick);

        Button buttonWeightDecrease = (Button) root.findViewById(R.id.exercisedetail_weight_decrease_button);
        buttonWeightDecrease.setOnClickListener(this::onClick);

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

        EditText repsText = (EditText) getView().findViewById(R.id.exercisedetail_reps_input);
        EditText weightText = (EditText) getView().findViewById(R.id.exercisedetail_weight_input);

        switch (v.getId())
        {
            case R.id.exercisedetail_reps_decrease_button:

                int repsDecrease = Integer.parseInt( repsText.getText().toString() );
                if(repsDecrease >=1) {
                    repsDecrease = repsDecrease - 1;
                }
                repsText.setText(String.valueOf(repsDecrease));
                break;

            case R.id.exercisedetail_reps_increase_button:
                int repsIncrease = Integer.parseInt( repsText.getText().toString() );
                if(repsIncrease <=99) {
                    repsIncrease = repsIncrease +1;
                }
                repsText.setText(String.valueOf(repsIncrease));
                break;
            case R.id.exercisedetail_weight_decrease_button:
                double weightDecrease = Double.parseDouble( weightText.getText().toString() );
                if(weightDecrease >=1.25) {
                    weightDecrease = weightDecrease -1.25;
                }
                weightText.setText(String.valueOf(weightDecrease));
                break;
            case R.id.exercisedetail_weight_increase_button:
                double weightIncrease = Double.parseDouble( weightText.getText().toString() );
                if(weightIncrease <=998.75) {
                    weightIncrease = weightIncrease +1.25;
                }
                weightText.setText(String.valueOf(weightIncrease));
                break;


        case R.id.exercisedetail_finish_set_button:
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

        this.actualLog += addLog(newLine(this.actualLog), trainingsLog, this.actualLogNumberOfSets+1);
        this.actualLogNumberOfSets++;
        this.textViewActualLog.setText(actualLog);
        break;

            default:
                break;
        }
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





    private void setLogEntrys(View root){

        List<TrainingsLog> actualDay = new ArrayList<>();
        List<TrainingsLog> oldDay = new ArrayList<>();

        for(int i = 0; i< this.sortedTrainingsLogs.size(); i++){

            String logEntryDate = this.dateFormatDDMMYYYY.format(this.sortedTrainingsLogs.get(i).getCreated());

            if(this.actualDate.equals(logEntryDate)){
                actualDay.add(this.sortedTrainingsLogs.get(i));
                this.actualLogNumberOfSets = i+1;
                continue;
            }
            for(int j = i; j < this.sortedTrainingsLogs.size(); j++){

                String logEntryDate2 = this.dateFormatDDMMYYYY.format(this.sortedTrainingsLogs.get(j).getCreated());

                if(!logEntryDate2.equals(logEntryDate)){
                    break;
                }

                oldDay.add(this.sortedTrainingsLogs.get(j));
            }

            break;
        }

        setLogLogTitles(oldDay, root);
        this.lastLog = createLog(oldDay);
        this.actualLog = createLog(actualDay);
        
    }
/*
    public String addLog(String firstRound, int logIndex, int numberOfSet){
        this.actualLogNumberOfSets = numberOfSet;
        return firstRound+numberOfSet+". "+this.dateFormatDDMMYYYY_HHMM.format(this.sortedTrainingsLogs.get(logIndex).getCreated()) + " Uhr"
                + "\nrepetitions: "+this.sortedTrainingsLogs.get(logIndex).getRepetitions()
                +", weight: "+this.sortedTrainingsLogs.get(logIndex).getWeight()+", text:"+this.sortedTrainingsLogs.get(logIndex).getAlternativeText();
    }

 */

    private String addLog(String newLine, TrainingsLog trainingsLog, int numberOfSet){
        return newLine+numberOfSet+". Satz: "+this.dateFormatDDMMYYYY_HHMM.format(trainingsLog.getCreated()) + " Uhr"
                + "\nrepetitions: "+trainingsLog.getRepetitions()
                +", weight: "+trainingsLog.getWeight()+", text:"+trainingsLog.getTrainingsLogId();
    }

    private String createLog(List<TrainingsLog> trainingsLogList) {

        Collections.reverse(trainingsLogList);
        String log = "";

        for (int i = 0; i<trainingsLogList.size(); i++){
            log += addLog(newLine(log), trainingsLogList.get(i) ,i+1);
        }

        return log;
    }

    private String newLine(String log){

        if(log.equals("")){
            return "";
        }else{
            return "\n\n";
        }
    }

    private void setLogLogTitles(List<TrainingsLog> trainingsLogList, View root) {
        if (!trainingsLogList.isEmpty()) {

            TextView titleLastLog = (TextView) root.findViewById(R.id.exercisedetail_last_log_label_textview);
            titleLastLog.append("\n"+this.dateFormatDDMMYYYY.format(trainingsLogList.get(0).getCreated()));

            TextView titleActualLog = (TextView) root.findViewById(R.id.exercisedetail_today_log_label_textview);
            titleActualLog.append("\n"+this.actualDate);

        }
    }


    ///////////////////////////////////////////////


}