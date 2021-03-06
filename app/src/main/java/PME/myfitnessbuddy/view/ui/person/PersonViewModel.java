package PME.myfitnessbuddy.view.ui.person;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.storage.PersonRepository;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository personRepository;
    public LiveData<List<Person>> allUsers;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepository = PersonRepository.getRepository(application);
        allUsers = personRepository.getPersons();
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

    public void deleteAllUsers() {
      //  personRepository.deleteAllUsers();
    }

    public LiveData<List<Person>> getAllUsers() {
        return allUsers;
    }

}