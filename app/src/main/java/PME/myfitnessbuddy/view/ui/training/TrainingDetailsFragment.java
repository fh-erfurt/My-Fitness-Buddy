package PME.myfitnessbuddy.view.ui.training;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myfitnessbuddy.R;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingDetailsFragment extends BaseFragment {

    public static final String ARG_TRAINING_ID = "trainingId";
    private TrainingDetailsViewModel viewModel;
    private LiveData<Training> trainingLiveData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrainingDetailsFragment() {
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
    public static TrainingDetailsFragment newInstance(String param1, String param2) {
        TrainingDetailsFragment fragment = new TrainingDetailsFragment();
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
        View root = inflater.inflate(R.layout.fragment_trainingdetails, container, false);
        viewModel = this.getViewModel( TrainingDetailsViewModel.class );
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        assert getArguments() != null;
        final long trainingId = getArguments().getLong(ARG_TRAINING_ID);
        this.trainingLiveData = this.viewModel.getTraining( trainingId );
        this.trainingLiveData.observe( requireActivity(), this::updateView);

        Log.i("EventCallbacks", "Observing Detail Contact");

    }

    private void updateView(Training training) {

        Log.i("EventCallbacks", "Update Detail View with Training: " + training);

        assert getView() != null;

        //ToDo Fragment
        TextView nameView = getView().findViewById( R.id.fragment_traininglist );

        nameView.setText(String.format("%s %s", training.getDesignation(), "  id:"+training.getId()));


    }

    @Override
    public void onPause() {
        super.onPause();

        this.trainingLiveData.removeObservers(requireActivity());

        Log.i("EventCallbacks", "Stopped observing Detail Contact");
    }

}