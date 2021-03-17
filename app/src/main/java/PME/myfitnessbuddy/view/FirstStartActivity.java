package PME.myfitnessbuddy.view;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.myfitnessbuddy.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.storage.Dao.PersonDao;
import PME.myfitnessbuddy.view.ui.evaluation.PersonWeightViewModel;
import PME.myfitnessbuddy.view.ui.exercise.ExerciseViewModel;
import PME.myfitnessbuddy.view.ui.home.HomeFragment;
import PME.myfitnessbuddy.view.ui.person.PersonViewModel;

public class FirstStartActivity extends AppCompatActivity {

    PersonDao daoPerson;
    String personName;
    int age;
    String dateOfBirthString;
    String gender;
    int personHeight;
    int bodyWeight;

    private String actualDate;
    private SimpleDateFormat dateFormatDDMMYYYY = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

    EditText editTextPersonName;
    EditText editTextAge;
    EditText editTextGender;
    EditText editTextPersonHeight;
    EditText editTextBodyWeight;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_firststart_userinput);

        Button buttonInsertUserDate = (Button) findViewById(R.id.firststart_userinput_button);
        buttonInsertUserDate.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {

        this.editTextPersonName = (EditText) findViewById(R.id.firststart_username_input);
        this.editTextAge = (EditText) findViewById(R.id.firststart_age_input);
        this.editTextGender = (EditText) findViewById(R.id.firststart_gender_input);
        this.editTextPersonHeight = (EditText) findViewById(R.id.firststart_height_input);
        this.editTextBodyWeight = (EditText) findViewById(R.id.firststart_bodyweight_input);

        PersonViewModel personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        PersonWeightViewModel personWeightViewModel  = new ViewModelProvider(this).get(PersonWeightViewModel.class);

        if (editTextPersonName.getText().toString().matches("")){
            //editTextPersonName.setText("");
            Toast.makeText(FirstStartActivity.this, "Bitte gib deinen Namen ein", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editTextAge.getText().toString().matches("") ||
                Integer.parseInt(editTextAge.getText().toString()) <= 0 ||
                Integer.parseInt(editTextAge.getText().toString()) > 90
        ){
            editTextAge.setText("");
            Toast.makeText(FirstStartActivity.this, "Bitte gib dein Alter ein", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editTextGender.getText().toString().matches("")){
            editTextGender.setText("");
            Toast.makeText(FirstStartActivity.this, "Bitte gib dein Geschlecht ein", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editTextPersonHeight.getText().toString().matches("") ||
                Integer.parseInt(editTextPersonHeight.getText().toString()) <= 0 ||
                Integer.parseInt(editTextPersonHeight.getText().toString()) > 240){
            editTextPersonHeight.setText("");
            Toast.makeText(FirstStartActivity.this, "Bitte gib deine Größe ein", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editTextBodyWeight.getText().toString().matches("") ||
                Integer.parseInt(editTextBodyWeight.getText().toString()) <= 0||
                Integer.parseInt(editTextBodyWeight.getText().toString()) > 350){
            editTextBodyWeight.setText("");
            Toast.makeText(FirstStartActivity.this, "Bitte gib dein Gewicht ein", Toast.LENGTH_SHORT).show();
            return;
        }

        personName = editTextPersonName.getText().toString();
        age = Integer.parseInt(editTextAge.getText().toString());

        dateOfBirthString = convertDate(age);

        gender = editTextGender.getText().toString();
        personHeight = Integer.parseInt(editTextPersonHeight.getText().toString());
        bodyWeight = Integer.parseInt(editTextBodyWeight.getText().toString());

        Person checkPerson = personViewModel.getPersonByName(personName);
        if(checkPerson == null) {
            Person firstPerson = new Person(personName, dateOfBirthString, 1, personHeight, bodyWeight);
            firstPerson.setCreated(System.currentTimeMillis());
            firstPerson.setModified(firstPerson.getCreated());
            firstPerson.setVersion(1);
            personViewModel.insert(firstPerson);

            PersonWeight firstWeight = new PersonWeight(bodyWeight);
            personViewModel.insert(firstWeight);
        }

        //Change FirstStartActivity to MainActivity
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }


    public String convertDate (int age){

        Date dateObject = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateObject);
        cal.add(Calendar.YEAR, -age);
        Date dateOfBirth= cal.getTime();
        String convertDate = dateFormatDDMMYYYY.format(dateOfBirth);

        return convertDate;
    }


}
