package PME.myfitnessbuddy.view.ui.training;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<CharSequence> text = new MutableLiveData<>();
    private MutableLiveData<CharSequence> trainingsName = new MutableLiveData<>();
    private MutableLiveData<CharSequence> trainingsCategory = new MutableLiveData<>();
    private MutableLiveData<CharSequence> type = new MutableLiveData<>();

    public void setText(CharSequence input) {
        text.setValue(input);
    }
    public LiveData<CharSequence> getText() { return text; }
    public void setType(CharSequence input) {
        type.setValue(input);
    }
    public LiveData<CharSequence> getType() {
        return type;
    }

    // share data from fragment to fragment
    public void setTrainingsName(CharSequence input) {
        trainingsName.setValue(input);
    }


    public LiveData<CharSequence> getTrainingsName() {

        return trainingsName;
    }

    public void setCategroy(CharSequence input) {
        trainingsCategory.setValue(input);
    }

    public LiveData<CharSequence> getCategory() {

        return trainingsCategory;
    }


}
