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
        return root;
    }
}