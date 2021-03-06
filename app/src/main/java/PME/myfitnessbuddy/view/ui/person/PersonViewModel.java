package PME.myfitnessbuddy.view.ui.person;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.storage.PersonRepository;
import PME.myfitnessbuddy.storage.PersonWeightRepository;

/**
 * PersonViewModel class fetches the person and his properties from the database
 * */

public class PersonViewModel extends AndroidViewModel {

    private PersonRepository personRepository;
    private PersonWeightRepository personWeightRepository;

    private List<PersonWeight> allPersonWeights;
    public Person person;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepository = PersonRepository.getRepository(application);
        person = personRepository.getLastPerson();

        personWeightRepository = PersonWeightRepository.getRepository(application);
        allPersonWeights = personWeightRepository.getPersonWeights();
    }

    public void insert(Person person) {
        personRepository.insert(person);
    }

    public void update(Person person) {
        personRepository.update(person);
    }

    public void insert(PersonWeight personWeight) {
        personWeightRepository.insert(personWeight);
    }

    public void update(PersonWeight personWeight) {
        personWeightRepository.update(personWeight);
    }

    public Person getPerson() {
        return person;
    }

    public List<PersonWeight> getAllPersonWeights() { return allPersonWeights; }
}