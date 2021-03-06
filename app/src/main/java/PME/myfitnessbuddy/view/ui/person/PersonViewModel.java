package PME.myfitnessbuddy.view.ui.person;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.storage.PersonRepository;
import PME.myfitnessbuddy.storage.PersonWeightRepository;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository personRepository;
    private PersonWeightRepository personWeightRepository;




    private LiveData<List<PersonWeight>> allPersonWeights;
    public LiveData<List<Person>> allUsers;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepository = PersonRepository.getRepository(application);
        allUsers = personRepository.getPersons();

        personWeightRepository = PersonWeightRepository.getRepository(application);
        allPersonWeights = personWeightRepository.getPersonWeights();
    }

    public void insert(Person person) {
        personRepository.insert(person);
    }

    public void update(Person person) {
        personRepository.update(person);
    }

    public void delete(Person person) {
      //  personRepository.delete(person);
    }

    public void insert(PersonWeight personWeight) {
        personWeightRepository.insert(personWeight);
    }

    public void update(PersonWeight personWeight) {
        personWeightRepository.update(personWeight);
    }

    public void delete(PersonWeight personWeight) {
        //  personWeightRepository.delete(personWeight);
    }

    public void deleteAllUsers() {
      //  personRepository.deleteAllUsers();
    }

    public LiveData<List<Person>> getAllUsers() {
        return allUsers;
    }

    public LiveData<List<PersonWeight>> getAllPersonWeights() { return allPersonWeights; }
}