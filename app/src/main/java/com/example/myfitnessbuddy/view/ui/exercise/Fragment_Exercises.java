package com.example.myfitnessbuddy.view.ui.exercise;

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

import com.example.myfitnessbuddy.R;
import com.example.myfitnessbuddy.model.exercise.Exercise;
import com.example.myfitnessbuddy.view.ui.Core.BaseFragment;
import com.example.myfitnessbuddy.view.ui.training.TrainingListAdapter;
import com.example.myfitnessbuddy.view.ui.training.TrainingListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Exercises#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Exercises extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Fragment_Exercises() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Fragment_TrainingList.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.myfitnessbuddy.view.ui.exercise.Fragment_Exercises newInstance() {
        com.example.myfitnessbuddy.view.ui.exercise.Fragment_Exercises fragment = new com.example.myfitnessbuddy.view.ui.exercise.Fragment_Exercises();
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
        View vE = inflater.inflate(R.layout.fragment_exercises, container, false);


        Button b = (Button) vE.findViewById(R.id.button);
        //  b.setOnClickListener(this);

        ExerciseViewModel exerciseViewModel = this.getViewModel(ExerciseViewModel.class);

        RecyclerView trainingListView = vE.findViewById(R.id.list_view_trainings);
        final ExerciseAdapter adapter = new ExerciseAdapter(this.requireActivity(),
                exerciseId -> {
                    Bundle args = new Bundle();
                    args.putLong("exerciseId", exerciseId);
                    NavController nc = NavHostFragment.findNavController( this );
                    nc.navigate( R.id.Fragment_Exercises, args );
                });

        trainingListView.setAdapter( adapter );
        trainingListView.setLayoutManager( new LinearLayoutManager(this.requireActivity()));

        exerciseViewModel.getExercises().observe(this.requireActivity(), adapter::setExercises);

        return vE;

    }



}