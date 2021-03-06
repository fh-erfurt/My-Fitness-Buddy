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

import com.myfitnessbuddy.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.NumberFormat;
import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.view.ui.core.BaseFragment;

/**
 * Person profile View implementation class
 * is used to show all properties of the person
 *
 * */

public class PersonFragment extends BaseFragment implements View.OnClickListener {

    private PersonViewModel personViewModel;

    private Person person = null;
    private List<PersonWeight> personweights = null;

    EditText editTextNewBodyweight;
    TextView bodyweight;
    TextView bmi;
    Button buttonUpdateActualBodyweight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        editTextNewBodyweight= (EditText)view.findViewById(R.id.fragment_profile_update_bodyweight_input_number);
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

        personViewModel = this.getViewModel(PersonViewModel.class);
        person = this.personViewModel.getPerson();
        personweights = this.personViewModel.getAllPersonWeights();

        // show all properties from person on view if they exist
        if(person!=null && personweights!=null){

            TextView age = view.findViewById(R.id.fragment_profile_age_textview);
            age.setText(Integer.toString(getAge(person.getBirthday())));

            TextView nickname=view.findViewById(R.id.fragment_profile_profilename_textview);
            nickname.setText(person.getNickname());

            TextView height=view.findViewById(R.id.fragment_profile_height_textview);
            height.setText(Integer.toString((int) person.getHeight()) + " cm" );

            TextView gender=view.findViewById(R.id.fragment_profile_gender_textview);
            gender.setText((person.getGender()==1) ? "männlich" : "weiblich");

            bodyweight=view.findViewById(R.id.fragment_profile_bodyweight_textview);
            bodyweight.setText(Integer.toString((int)personweights.get(personweights.size()-1).getWeight())+ " KG" );


            bmi=view.findViewById(R.id.fragment_profile_bmi_textview);
           bmi.setText(calculateBmi(personweights.get(personweights.size()-1).getWeight(),person.getHeight()));
        }



        return view;
    }

    // standard formula for calculating the BMI
    private String calculateBmi(double bodyweight, double height)
    {
        double heightInMeter= height/100;
        double bmi= bodyweight/(Math.pow(heightInMeter,2));

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(bmi);
    }

    // get Age from date of Birth
    public int getAge(String birthday) {

        DateTime birth = DateTime.parse(birthday, DateTimeFormat.forPattern("dd.MM.yy"));
        return new DateTime().year().get() - birth.year().get();

    }

    @Override
    public void onClick(View v) {
        updateActualBodyweight();
    }

    // instant update from BMI if weight is changed
    private void updateActualBodyweight(){

                double weight;
        try {
            weight = Double.parseDouble(editTextNewBodyweight.getText().toString());
        }
        catch (NumberFormatException e)
        {
            weight = 0;
        }

        PersonWeight personweight = new PersonWeight(weight);
        personViewModel.insert(personweight);

        bodyweight.setText(personweight.getWeight()+ " KG" );
        double newWeight = personweight.getWeight();
        String newBMI =calculateBmi(newWeight,person.getHeight());

        bmi.setText(newBMI);
    }



}