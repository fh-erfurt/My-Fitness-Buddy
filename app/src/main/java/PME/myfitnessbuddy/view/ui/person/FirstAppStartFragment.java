package PME.myfitnessbuddy.view.ui.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfitnessbuddy.R;

import PME.myfitnessbuddy.view.ui.core.BaseFragment;

public class FirstAppStartFragment extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_firststart_userinput, container, false);
        return view;
    }
}
