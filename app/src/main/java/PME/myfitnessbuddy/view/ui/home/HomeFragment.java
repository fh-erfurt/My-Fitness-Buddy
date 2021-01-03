package PME.myfitnessbuddy.view.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.view.MainActivity;
import PME.myfitnessbuddy.view.ui.evaluation.EvaluationFragment;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseFragment;
import PME.myfitnessbuddy.view.ui.training.TrainingListAdapter;
import PME.myfitnessbuddy.view.ui.training.TrainingListFragment;
import PME.myfitnessbuddy.view.ui.training.TrainingListViewModel;

public class HomeFragment extends Fragment  implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    CardView cardViewTraining;
    CardView cardViewExercise;
    CardView cardViewAnalyse;
    CardView cardViewSettings;
    NavHostFragment navHostFragment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cardViewExercise=(CardView) root.findViewById(R.id.cardview_home_exercise);
        cardViewExercise.setOnClickListener(this);



        cardViewTraining=(CardView) root.findViewById(R.id.cardview_home_training);
        cardViewTraining.setOnClickListener(this);

        cardViewAnalyse=(CardView) root.findViewById(R.id.cardview_home_analyse);
        cardViewAnalyse.setOnClickListener(this);

        cardViewSettings=(CardView) root.findViewById(R.id.cardview_home_settings);
        cardViewSettings.setOnClickListener(this);

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId())
        {
            case R.id.cardview_home_training:
                fragment = new TrainingListFragment();
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_fragment_traininglist);
                replaceFragment(fragment);
                break;

            case R.id.cardview_home_exercise:
                fragment = new ExerciseFragment();
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_fragment_exercise);
                replaceFragment(fragment);
                break;

            case R.id.cardview_home_analyse:
                fragment = new EvaluationFragment();
                replaceFragment(fragment);
                break;
            case R.id.cardview_home_settings:
                Snackbar.make(v, "Hier muss noch das fragment hin", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;


        }

    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}