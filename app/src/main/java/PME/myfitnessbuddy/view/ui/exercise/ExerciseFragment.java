package PME.myfitnessbuddy.view.ui.exercise;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfitnessbuddy.R;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseFragment} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends BaseFragment {



    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exercise, container, false);

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.btnToExerciseCreate);

        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.createExerciseFragment, null));

     //   final TextView textView = root.findViewById(R.id.text_exercise);
/*
        exerciseViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/

        RecyclerView exerciseListView = root.findViewById(R.id.exercises);


          // Liste -> Detailansicht (siehe TrainingDetailsfragment)

        final ExerciseAdapter adapter = new ExerciseAdapter(this.requireActivity(),
                exerciseId -> {
                    Bundle args = new Bundle();
                    args.putLong("exerciseId", exerciseId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_fragment_exercise_to_fragment_exercisedetail , args );
                });


        //////////////////////////////////////später ersetzen (siehe oben)//////////////////////
      //  final ExerciseAdapter adapter = new ExerciseAdapter(this.requireActivity(), null);

        exerciseListView.setAdapter( adapter );
        exerciseListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

        exerciseViewModel.getExercises().observe(this.requireActivity(), adapter::setExercises);
      //  exerciseViewModel.getMuscleGroups().observe(this.requireActivity(), adapter::setMuscleGroups);


        return root;
    }
}