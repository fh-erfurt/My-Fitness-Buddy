package PME.myfitnessbuddy.view.ui.training;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.view.ui.core.BaseFragment;

public class TrainingCreateFragment1 extends BaseFragment implements View.OnClickListener{

    View root;

    private SharedViewModel viewModel;

    EditText trainingDesignation;
    Spinner trainingCategory;


    public TrainingCreateFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_training_create1, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

            }
        });

        viewModel.getTrainingsName().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

            }
        });

        trainingDesignation = (EditText) root.findViewById(R.id.createTrainingDesignation);
        trainingCategory = (Spinner) root.findViewById(R.id.createTrainingType);


        trainingDesignation.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        Button button = (Button) root.findViewById(R.id.btnCreateTrainingNext);
        button.setOnClickListener(this::onClick);

        return root;
    }


    @Override
    public void onClick(View v) {

        viewModel.setCategroy(trainingCategory.getSelectedItem().toString());
        viewModel.setTrainingsName(trainingDesignation.getText().toString());
        Navigation.findNavController(v).navigate(R.id.action_trainingCreateFragment_to_trainingCreateFragment2);
    }

}