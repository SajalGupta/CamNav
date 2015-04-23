package com.example.sajal.camnav;

/**
 * Created by Sajal on 20-04-2015.
 */
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Call super :
        super.onCreate(savedInstanceState);

        // Set the activity's fragment :
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }


    public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

        private SeekBarPreference _seekBarPref;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.activity_settings);


            // Get widgets :
            _seekBarPref = (SeekBarPreference) this.findPreference("SEEKBAR_VALUE");

            // Set listener :
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

            // Set seekbar summary :
            int radius = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getInt("SEEKBAR_VALUE", 50);
            _seekBarPref.setSummary(this.getString(R.string.settings_summary).replace("$1", ""+radius));
            Globals.mWalkRadius=radius;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            // Set seekbar summary :
            int radius = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getInt("SEEKBAR_VALUE", 50);
            Log.i("settingActivityCLass",radius+"");
            Globals.mWalkRadius=radius;
            _seekBarPref.setSummary(this.getString(R.string.settings_summary).replace("$1", ""+radius));
        }
    }
}