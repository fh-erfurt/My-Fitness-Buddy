package PME.myfitnessbuddy.view.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.myfitnessbuddy.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    CardView cardViewTraining;
    CardView cardViewExercise;
    CardView cardViewAnalyse;
    CardView cardViewSettings;
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
        switch (v.getId())
        {
            case R.id.cardview_home_training:
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            break;

            case R.id.cardview_home_exercise:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.cardview_home_analyse:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.cardview_home_settings:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;


        }

    }
}