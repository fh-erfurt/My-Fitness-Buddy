package PME.myfitnessbuddy.view.ui.settings;
import android.os.Bundle;
import PME.myfitnessbuddy.view.ui.core.Constants;

import androidx.preference.PreferenceFragmentCompat;
import androidx.appcompat.app.AppCompatDelegate;

import com.myfitnessbuddy.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        findPreference(Constants.PREF_Light_MODE).setOnPreferenceChangeListener((preference, newValue) -> {
            AppCompatDelegate.setDefaultNightMode(
                    (Boolean)newValue ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );

            return true;
        });
    }
}
