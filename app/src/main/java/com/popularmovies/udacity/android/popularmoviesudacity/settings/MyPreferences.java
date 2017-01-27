package com.popularmovies.udacity.android.popularmoviesudacity.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.popularmovies.udacity.android.popularmoviesudacity.R;

/**
 * Created by smenesid on 26-Jan-17.
 */

public class MyPreferences extends AppCompatActivity {

    private static final String LOG_TAG = MyPreferences.class.getName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            setPreferenceSummary(preference, newValue);
            return true;
        }

        private void setPreferenceSummary(Preference preference, Object newValue) {
        }
    }
}