package com.example.myfitnessbuddy.view.ui.training;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfitnessbuddy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_TrainingList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_TrainingList extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Fragment_TrainingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Fragment_TrainingList.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_TrainingList newInstance() {
        Fragment_TrainingList fragment = new Fragment_TrainingList();
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
        View v = inflater.inflate(R.layout.fragment_traininglist, container, false);


        FloatingActionButton b = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        b.setOnClickListener(this);
        return v;

    }

    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_fragment_TrainingList_to_fragment_Exercises);
        //Intent i = new Intent(getActivity(), TrainingList_AddTraining.class);
       // startActivity(i);
    }


}