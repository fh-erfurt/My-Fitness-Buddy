package PME.myfitnessbuddy.view.ui.evaluation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.myfitnessbuddy.R;


import PME.myfitnessbuddy.view.ui.core.BaseFragment;

public class EvaluationFragment extends BaseFragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evaluation, container, false);



    }
}