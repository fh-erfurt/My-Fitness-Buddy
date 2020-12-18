package PME.myfitnessbuddy.view.ui.training;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.myfitnessbuddy.R;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingListFragment extends BaseFragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public TrainingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment TrainingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrainingListFragment newInstance() {
        TrainingListFragment fragment = new TrainingListFragment();
        Bundle args = new Bundle();
      //  args.putString(ARG_PARAM1, param1);
    //    args.putString(ARG_PARAM2, param2);
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
        View v = inflater.inflate(R.layout.fragment_traininglist, container, false);


        Button b = (Button) v.findViewById(R.id.button);
        //  b.setOnClickListener(this);

        TrainingListViewModel trainingListViewModel = this.getViewModel(TrainingListViewModel.class);

        RecyclerView trainingListView = v.findViewById(R.id.list_view_trainings);
        final TrainingListAdapter adapter = new TrainingListAdapter(this.requireActivity(),
        trainingId -> {
            Bundle args = new Bundle();
            args.putLong("trainingId", trainingId);
            NavController nc = NavHostFragment.findNavController( this );
            //nc.navigate( R.id.action_fragment_traininglist_to_fragment_traininglist_trainingdetails, args );
        });

        trainingListView.setAdapter( adapter );
        trainingListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

        trainingListViewModel.getTrainings().observe(this.requireActivity(), adapter::setTrainings);

        return v;

    }



}