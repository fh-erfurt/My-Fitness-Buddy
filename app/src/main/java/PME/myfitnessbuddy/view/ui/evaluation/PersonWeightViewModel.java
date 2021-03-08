package PME.myfitnessbuddy.view.ui.evaluation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import PME.myfitnessbuddy.model.PersonWeight;
import PME.myfitnessbuddy.storage.PersonWeightRepository;

public class PersonWeightViewModel  extends AndroidViewModel {

    public final PersonWeightRepository personWeightRepository;

    public PersonWeightViewModel(Application application) {
        super(application);
        this.personWeightRepository = PersonWeightRepository.getRepository(application);
    }

    public List<PersonWeight> getWeights () {
        return this.personWeightRepository.getPersonWeights();
    }

    public List<PersonWeight> getLastWeights () {
        return this.personWeightRepository.getLastPersonWeights();
    }
}
