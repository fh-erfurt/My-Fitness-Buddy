package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.myfitnessbuddy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.exercise.ExerciseWithTrainingsLog;
import PME.myfitnessbuddy.model.training.Training;
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
    private String oldDate1 = "";
    private String oldDate2 = "";
    private String oldDate3 = "";

    private TextView textViewOldDay1;
    private TextView textViewOldDay2;
    private TextView textViewOldDay3;



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

        this.textViewOldDay1 = (TextView) root.findViewById(R.id.textView6);


        /////////////Baustelle Start/////////////////

        List  <ExerciseWithTrainingsLog> exerciseWithTrainingsLogs = viewModel.getExerciseWithTrainingsLogLiveDataByExerciseId(1);

        exerciseWithTrainingsLogs.get(0).sortTrainingsLog();

        sortedTrainingsLogs = exerciseWithTrainingsLogs.get(0).getTrainingsLog();

        Date dateObject = new Date();
        this.actualDate = dateFormatDDMMYYYY.format(dateObject);

        set3LogEntrysFromTheLastDays();
        setTextFieldLastTrainingDates();

        Button buttonEndTrainingSet = (Button) root.findViewById(R.id.button5);
        buttonEndTrainingSet.setOnClickListener(this::onClick);

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

    public void set3LogEntrysFromTheLastDays(){

        for(int i = 0; i< this.sortedTrainingsLogs.size(); i++){ /////rückwärts!!!!!!!!!!!

            String logEntryDate = this.dateFormatDDMMYYYY.format(this.sortedTrainingsLogs.get(i).getCreated());

            if(!this.actualDate.equals(logEntryDate)){
                this.oldDate1 = this.dateFormatDDMMYYYY_HHMM.format(this.sortedTrainingsLogs.get(i).getCreated()) + " Uhr";


                if(i+1 >= this.sortedTrainingsLogs.size()){
                    break;
                }
                this.oldDate2 = this.dateFormatDDMMYYYY_HHMM.format(this.sortedTrainingsLogs.get(i+1).getCreated()) + " Uhr";


                if(i+2 >= this.sortedTrainingsLogs.size()){
                    break;
                }
                this.oldDate3 = dateFormatDDMMYYYY_HHMM.format(this.sortedTrainingsLogs.get(i+2).getCreated()) + " Uhr";
                break;
            }
        }

    }

    public void setTextFieldLastTrainingDates(){

        if (!oldDate1.equals("")){
            this.textViewOldDay1.setText(this.oldDate1 + "\n" +
                    this.oldDate2 + "\n" +
                    this.oldDate3);
        }

    }

    ///////////////////////////////////////////////

}