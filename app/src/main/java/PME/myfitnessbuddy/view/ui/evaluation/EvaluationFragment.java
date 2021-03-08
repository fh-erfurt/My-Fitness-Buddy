package PME.myfitnessbuddy.view.ui.evaluation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.myfitnessbuddy.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

public class EvaluationFragment extends BaseFragment {


    private PersonWeightViewModel viewModel;
    private List<PersonWeight> weightLogs;
    private SimpleDateFormat dateFormatDDMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    private String weightLog = "";

    private TextView evaluationBodyweight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_evaluation, container, false);
        viewModel = this.getViewModel(PersonWeightViewModel.class);

        weightLogs = viewModel.getLastWeights();
        Log.i("tag","WEIGHT!!!"+weightLogs.get(0).getWeight());

        this.evaluationBodyweight = (TextView) root.findViewById((R.id.evaluation_bodyweight_textview)) ;
        setWeightLog(root);
        this.evaluationBodyweight.setText(this.weightLog);





        return root;
    }

    private void setWeightLog(View root){

        List<PersonWeight> weightLogList =new ArrayList<>();

        weightLogList.addAll(this.weightLogs);
        this.weightLog = createWeightLog(weightLogList);
    }


    private String newLine(String log){

        if(log.equals("")){
            return "";
        }else{
            return "\n\n";
        }
    }

    private String addWeightLog(String newLine, PersonWeight personWeight, int counter){
        return newLine+counter+". | "+this.dateFormatDDMMYYYY.format(personWeight.getCreated())
                +"Gewicht: "+personWeight.getWeight()+"Kg";
    }

    private String createWeightLog(List <PersonWeight> weightLogList){


        String weightLog ="";

        for(int i = 0; i< weightLogList.size(); i++) {

            weightLog += addWeightLog(newLine(weightLog), weightLogList.get(i) ,i+1);
        }


        return weightLog;
    }


}