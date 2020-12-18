package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myfitnessbuddy.R;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;
import PME.myfitnessbuddy.view.ui.training.TrainingListAdapter;
import PME.myfitnessbuddy.view.ui.training.TrainingListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseFragment} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment {

    private ExerciseViewModel ExerciseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExerciseViewModel =
                new ViewModelProvider(this).get(ExerciseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exercise, container, false);
        final TextView textView = root.findViewById(R.id.text_exercise);
        ExerciseViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        //TrainingListViewModel trainingListViewModel = this.getViewModel(TrainingListViewModel.class);
/*
        RecyclerView trainingListView = root.findViewById(R.id.exercises);
        final ExerciseAdapter adapter = new ExerciseAdapter(this.requireActivity(),
                exerciseId -> {
                    Bundle args = new Bundle();
                    args.putLong("trainingId", trainingId);
                    NavController nc = NavHostFragment.findNavController( this );
                    //nc.navigate( R.id.action_fragment_traininglist_to_fragment_traininglist_trainingdetails, args );
                });

        trainingListView.setAdapter( adapter );
        trainingListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

        trainingListViewModel.getTrainings().observe(this.requireActivity(), adapter::setTrainings);
*/

        return root;
    }
}