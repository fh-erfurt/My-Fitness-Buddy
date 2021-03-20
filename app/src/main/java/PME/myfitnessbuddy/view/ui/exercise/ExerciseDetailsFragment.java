package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;

import androidx.lifecycle.LiveData;

import android.text.Editable;
import android.text.TextWatcher;
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
 * exercise detail fragment shows the details from an clicked exercise
 * */

public class ExerciseDetailsFragment extends BaseFragment implements View.OnClickListener{

    public static final String ARG_EXERCISE_ID = "exerciseId";

    private ExerciseDetailsViewModel viewModel;
    private LiveData<Exercise> exerciseLiveData;

    //Trainingslogs are sorted by creation date, in descending order to quickly find the latest entries
    private List<TrainingsLog> sortedTrainingsLogs;

    private String lastLog = "";
    private String actualLog = "";
    private int actualLogNumberOfSets = 0;

    private TextView textViewActualLog;

    EditText editTextRepetitions;
    EditText editTextWeight;

    private String actualDate;
    private final SimpleDateFormat dateFormatDDMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

    public ExerciseDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_exercisedetail, container, false);
        viewModel = this.getViewModel( ExerciseDetailsViewModel.class );

        TextView textViewLastLog = (TextView) root.findViewById(R.id.exercisedetail_last_log_textview);
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
        textViewLastLog.setText(this.lastLog);
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


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        assert getArguments() != null;
        final long exerciseId = getArguments().getLong(ARG_EXERCISE_ID);
        this.exerciseLiveData = this.viewModel.getExercise( exerciseId );
        this.exerciseLiveData.observe( requireActivity(), this::updateView);

    }

    private void updateView(Exercise exercise) {

        assert getView() != null;

        TextView nameView = getView().findViewById( R.id.fragment_exercise_id );

        nameView.setText(String.format("%s %s", exercise.getDesignation(), "  "));
    }

    @Override
    public void onPause() {
        super.onPause();
        this.exerciseLiveData.removeObservers(requireActivity());
    }

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

            saveTrainingLog();

        break;

            default:
                break;
        }
    }

    public void saveTrainingLog(){

        String repetition = this.editTextRepetitions.getText().toString();
        String weight = this.editTextWeight.getText().toString();
        String alternativeText = "";

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

        this.actualLog += addLogEntry(newLine(this.actualLog), trainingsLog, this.actualLogNumberOfSets+1);
        this.actualLogNumberOfSets++;
        this.textViewActualLog.setText(actualLog);
    }

    /**
     * Ensures that the saved log entries are correctly listed when entering the fragment.
     *
     * sets the Dates for the log labels
     * sets the member variables lastLog and actualLog
     * @param root (to change text views)
     */
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

        setDatesForLogLabels(oldDay, root);
        this.lastLog = createLog(oldDay);
        this.actualLog = createLog(actualDay);
        
    }

    private String addLogEntry(String newLine, TrainingsLog trainingsLog, int numberOfSet){

        return newLine+numberOfSet+". Satz | "
                + " Wiederholungen: "+trainingsLog.getRepetitions() +" | "
                + "Gewicht: "+trainingsLog.getWeight()+"Kg";

    }

    /**
     *
     * The log is created in ascending order according to the creation date
     */
    private String createLog(List<TrainingsLog> trainingsLogList) {

        Collections.reverse(trainingsLogList);
        String log = "";

        for (int i = 0; i<trainingsLogList.size(); i++){
            log += addLogEntry(newLine(log), trainingsLogList.get(i) ,i+1);
        }

        return log;
    }

    /**
     * After a log entry there are 2 line breaks.
     * There is no blank line before the first and after the last log entry.
     */
    private String newLine(String log){

        if(log.equals("")){
            return "";
        }else{
            return "\n\n";
        }
    }

    /**
     *
     *The labels for the logs are given the correct dates
     * ("last record", "record today")
     *
     */
    private void setDatesForLogLabels(List<TrainingsLog> oldDayLogEntry, View root) {
        if (!oldDayLogEntry.isEmpty()) {

            TextView titleLastRecord = (TextView) root.findViewById(R.id.exercisedetail_last_log_label_textview);
            titleLastRecord.append("\n"+this.dateFormatDDMMYYYY.format(oldDayLogEntry.get(0).getCreated()));

            TextView titleRecordToday = (TextView) root.findViewById(R.id.exercisedetail_today_log_label_textview);
            titleRecordToday.append("\n"+this.actualDate);

        }
    }

}