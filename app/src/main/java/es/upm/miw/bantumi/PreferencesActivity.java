package es.upm.miw.bantumi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class PreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new ActividadPreferencesFragment())
                .commit();
    }

    public static class ActividadPreferencesFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.preferences_layout, rootKey);
        }
    }
}
