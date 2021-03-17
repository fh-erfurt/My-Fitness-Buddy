package PME.myfitnessbuddy.view.ui.home;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.google.android.material.snackbar.Snackbar;
import com.myfitnessbuddy.R;


import PME.myfitnessbuddy.view.ui.evaluation.EvaluationFragment;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseFragment;

import PME.myfitnessbuddy.view.ui.person.PersonFragment;
import PME.myfitnessbuddy.view.ui.settings.SettingsFragment;
import PME.myfitnessbuddy.view.ui.training.TrainingListFragment;




public class HomeFragment extends Fragment  implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    CardView cardViewTraining;
    CardView cardViewExercise;
    CardView cardViewAnalyse;
    CardView cardViewSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
                Navigation.findNavController(v).navigate(R.id.fragment_evaluation);
                replaceFragment(fragment);
                break;
            case R.id.cardview_home_settings:
                fragment = new SettingsFragment();
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_fragment_settings);
                replaceFragment(fragment);
                break;


        }

    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if(!newFragment.isAdded()) {
            fragmentManager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            } else {
            transaction.show(newFragment);
            }
        }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }
}