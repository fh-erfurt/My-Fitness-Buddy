package PME.myfitnessbuddy.view.ui.training;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingListFragment extends BaseFragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_traininglist, container, false);

        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.btnToTrainingCreate);
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragment_traininglist_to_trainingCreateFragment, null));

        TrainingListViewModel trainingListViewModel = this.getViewModel(TrainingListViewModel.class);
        RecyclerView trainingListView = v.findViewById(R.id.list_view_trainings);

        final TrainingListAdapter adapter = new TrainingListAdapter(this.requireActivity(),
        trainingId -> {
            Bundle args = new Bundle();
            args.putLong("trainingId", trainingId);
            NavController nc = NavHostFragment.findNavController( this );
            nc.navigate( R.id.action_fragment_traininglist_to_fragment_trainingdetails, args );
        });

        trainingListView.setAdapter( adapter );
        trainingListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

        trainingListViewModel.getTrainings().observe(this.requireActivity(), adapter::setTrainings);

        return v;

    }



}