package PME.myfitnessbuddy.view.ui.training;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<CharSequence> trainingsName = new MutableLiveData<>();

    private MutableLiveData<CharSequence> trainingsCategory = new MutableLiveData<>();

    public void setTrainingsName(CharSequence input) {
        trainingsName.setValue(input);
    }

    public LiveData<CharSequence> getTrainingsName() { return trainingsName; }

    public void setCategroy(CharSequence input) {
        trainingsCategory.setValue(input);
    }

    public LiveData<CharSequence> getCategory() { return trainingsCategory; }


}
