package PME.myfitnessbuddy.view.ui.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LiveData;

import com.myfitnessbuddy.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

public class PersonFragment extends BaseFragment {

    private PersonViewModel personViewModel;
    private LiveData<List<Person>> personLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        personViewModel = this.getViewModel(PersonViewModel.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);



    //    Person person1 = person.getValue().get(0);

     //   TextView age = (TextView) view.findViewById(R.id.textView13);


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        LiveData<List<Person>> person = personViewModel.getAllUsers();
        person.observe(requireActivity(), this::updateView);
    }


    @Override
    public void onPause() {
        super.onPause();
      //  this.personLiveData.removeObservers(requireActivity());   stürzt ab
    }



    private void updateView(List<Person> person) {
        assert getView() != null;
        assert person != null;
        if(!person.isEmpty()) {
            TextView age = getView().findViewById(R.id.fragment_profile_age_textview);
            age.setText("" + getAge(person.get(0).getBirthday()));
        }

    }

    public int getAge(String birthday) {
        DateTime birth = DateTime.parse(birthday, DateTimeFormat.forPattern("dd.MM.yyyy"));
        return new DateTime().year().get() - birth.year().get();
    }


}