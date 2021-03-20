package PME.myfitnessbuddy.view.ui.core;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

/**
 * standard fragment implementation from which is inherited
 *
 * */

public class BaseFragment extends Fragment {

    /*
       Getting a ViewModel as well as a specific AndroidViewModel that has
       access to the context requires some effort. This method hides all the
       complex stuff.
    */
    protected <T extends ViewModel> T getViewModel(Class<T> tClass) {
        return new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        requireActivity().getApplication()
                )).get(tClass);
    }

}
