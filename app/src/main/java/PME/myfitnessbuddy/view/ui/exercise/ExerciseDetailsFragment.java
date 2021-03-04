package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        /////////////Baustelle Start/////////////////

        List  <ExerciseWithTrainingsLog> exerciseWithTrainingsLogs = viewModel.getExerciseWithTrainingsLogLiveDataByExerciseId(1);
      //  String x = DateFormat.getInstance().format(exerciseWithTrainingsLogs.get(0).trainingsLog.get(0).getCreated(),"dd");
        exerciseWithTrainingsLogs.get(0).sortTrainingsLog();

        SimpleDateFormat df = new SimpleDateFormat("hh:mm dd.MM.yyyy", Locale.GERMANY);

        String time = df.format(new Date(exerciseWithTrainingsLogs.get(0).trainingsLog.get(0).getCreated()));
        time.replaceAll(" ", "Uhr"); //geht nicht :(

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

        nameView.setText(String.format("%s %s", exercise.getDesignation(), "  id:"+exercise.getExerciseId()));


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
     List  <ExerciseWithTrainingsLog> exerciseWithTrainingsLogs = viewModel.getExerciseWithTrainingsLogLiveDataByExerciseId(1);
     String x = DateFormat.getInstance().format(exerciseWithTrainingsLogs.get(0).trainingsLog.get(0).getCreated());

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

    public void setLastRecord(List <TrainingsLog> trainingsLogs){

       String oldDate1;
       String oldDate2;
       String oldDate3;
       String actualDate;

        for(int i = 0; i< trainingsLogs.size(); i++){ /////rückwärts!!!!!!!!!!!
         /*
            if(trainingsLogs.get(i).getCreated() != actualDate){
                oldDate1 = trainingsLogs.get(i).getCreated();
                oldDate2 = trainingsLogs.get(i+1).getCreated();
                oldDate3 = trainingsLogs.get(i+2).getCreated();

            }

          */

        }

    }

    ///////////////////////////////////////////////

}