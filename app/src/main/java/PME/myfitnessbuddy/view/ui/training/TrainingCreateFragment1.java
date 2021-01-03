package PME.myfitnessbuddy.view.ui.training;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.myfitnessbuddy.R;

import java.util.ArrayList;
import java.util.List;

import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**

 */
public class TrainingCreateFragment1 extends BaseFragment implements View.OnClickListener{

    View root;

    private SharedViewModel viewModel;
    private EditText editText;

    EditText trainingDesignation;
    Spinner trainingType;

    CheckBox beine;
    CheckBox brust;
    CheckBox rücken;
    CheckBox bizeps;

    List<String> muscleGroupList = new ArrayList();



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
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

            }
        });

        viewModel.getType().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

            }
        });



        final TrainingCreateAdapter adapter = new TrainingCreateAdapter(this.requireActivity(),
                trainingId -> {
                    Bundle args = new Bundle();
                    args.putLong("trainingId", trainingId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.action_fragment_traininglist_to_fragment_trainingdetails, args );
                });


        trainingDesignation = (EditText) root.findViewById(R.id.createTrainingDesignation);
        trainingType = (Spinner) root.findViewById(R.id.createTrainingType);



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
        // Inflate the layout for this fragment


        return root;
    }


    @Override
    public void onClick(View v) {

        viewModel.setText(trainingDesignation.getText());

        viewModel.setType(trainingType.getSelectedItem().toString());


        Navigation.findNavController(v).navigate(R.id.action_trainingCreateFragment_to_trainingCreateFragment2);
    }


}