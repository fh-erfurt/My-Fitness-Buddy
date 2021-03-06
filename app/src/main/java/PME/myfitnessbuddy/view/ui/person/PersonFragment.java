package PME.myfitnessbuddy.view.ui.person;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;

import com.myfitnessbuddy.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

public class PersonFragment extends BaseFragment implements View.OnClickListener {

    private PersonViewModel personViewModel;
    private LiveData<List<Person>> personLiveData;
    private LiveData<List<PersonWeight>> personweightLiveData;
    EditText editTextNewBodyweight;

    Button buttonUpdateActualBodyweight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        personViewModel = this.getViewModel(PersonViewModel.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editTextNewBodyweight=(EditText) view.findViewById(R.id.fragment_profile_update_bodyweight_input_number);

        editTextNewBodyweight.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        buttonUpdateActualBodyweight = (Button) view.findViewById(R.id.fragment_profile_update_bodyweight_button);
        buttonUpdateActualBodyweight.setOnClickListener(this::onClick);

              return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        this.personLiveData = personViewModel.getAllUsers();
        this.personLiveData.observe(requireActivity(), this::updateView);

        this.personweightLiveData = personViewModel.getAllPersonWeights();
        this.personweightLiveData.observe(requireActivity(), this::updateView2);

    }


    @Override
    public void onPause() {
        super.onPause();
        this.personLiveData.removeObservers(requireActivity());
        this.personweightLiveData.removeObservers(requireActivity());

    }



    private void updateView(List<Person> person) {
        assert getView() != null;
        assert person != null;
        if(!person.isEmpty()) {
            TextView age = getView().findViewById(R.id.fragment_profile_age_textview);
            age.setText(Integer.toString(getAge(person.get(0).getBirthday())));

            TextView nickname=getView().findViewById(R.id.fragment_profile_profilename_textview);
            nickname.setText(person.get(0).getNickname());
            TextView height=getView().findViewById(R.id.fragment_profile_height_textview);
            height.setText(Integer.toString((int) person.get(0).getHeight()) + " cm" );
           // TextView bodyweight=getView().findViewById(R.id.fragment_profile_bodyweight_textview);
           // bodyweight.setText(Integer.toString((int)person.get(0).getWeight())+ " KG" );
            TextView gender=getView().findViewById(R.id.fragment_profile_gender_textview);

            if(person.get(0).getGender()==1)
            {
                gender.setText("Male");
            }else
            {
                gender.setText("Female");
            }


        }


    }

    private void updateView2(List<PersonWeight> personWeights) {
       assert getView() != null;
        assert personWeights != null;
        if(!personWeights.isEmpty()) {

            TextView bodyweight=getView().findViewById(R.id.fragment_profile_bodyweight_textview);
            bodyweight.setText(Integer.toString((int)personWeights.get(personWeights.size()-1).getWeight())+ " KG" );

        }


    }



    public int getAge(String birthday) {
        DateTime birth = DateTime.parse(birthday, DateTimeFormat.forPattern("dd.MM.yy"));
        return new DateTime().year().get() - birth.year().get();
    }


    @Override
    public void onClick(View v) {
        updateActualBodyweight();
    }

    private void updateActualBodyweight(){

        double weight;
        try {
            weight = Double.parseDouble(editTextNewBodyweight.getText().toString());
        }
        catch (NumberFormatException e)
        {
            weight = 0;
        }

        PersonWeight personWeight = new PersonWeight(weight);

        personViewModel.insert(personWeight);


    }
}