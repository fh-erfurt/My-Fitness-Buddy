package PME.myfitnessbuddy.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myfitnessbuddy.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.view.ui.person.PersonViewModel;

/**
 * FirstStartActivity for MyFitnessBuddy
 * only works if app never used before
 * storage person for profile and analysis
 * */

public class FirstStartActivity extends AppCompatActivity {

    String personName;
    int age;
    String dateOfBirthString;
    String gender;
    int personHeight;
    int bodyWeight;

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


        // validation for profile data
        if (editTextPersonName.getText().toString().matches("")){
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

        dateOfBirthString = convertAndCalculateDate(age);

        gender = editTextGender.getText().toString();
        personHeight = Integer.parseInt(editTextPersonHeight.getText().toString());
        bodyWeight = Integer.parseInt(editTextBodyWeight.getText().toString());

        // just one user allowed, check if one exists otherwise create new one
        Person checkPerson = personViewModel.getPerson();
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


    // method to calculate date of birth with the age
    public String convertAndCalculateDate (int age){

        Date dateObject = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateObject);
        cal.add(Calendar.YEAR, -age);
        Date dateOfBirth= cal.getTime();
        String convertDate = dateFormatDDMMYYYY.format(dateOfBirth);

        return convertDate;
    }


}
