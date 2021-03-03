package PME.myfitnessbuddy.view.ui.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myfitnessbuddy.R;

import java.util.List;
import java.util.Observer;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.storage.Dao.PersonDao;
import PME.myfitnessbuddy.storage.MyFitnessBuddyDatabase;
import PME.myfitnessbuddy.view.MainActivity;

public class PersonFragment extends Fragment {

    private PersonViewModel personViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        return view;
    }
}