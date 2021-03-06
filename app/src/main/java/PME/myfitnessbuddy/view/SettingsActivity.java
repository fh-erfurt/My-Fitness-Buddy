package PME.myfitnessbuddy.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import PME.myfitnessbuddy.view.ui.settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
