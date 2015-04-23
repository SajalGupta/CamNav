package com.example.sajal.camnav;

/**
 * Created by Sajal on 20-04-2015.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

public class MyPreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        SharedPreferences SP = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        SP.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("ListPref","Preference Changed");
        startActivity(new Intent(this, Alpha.class));
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.activity_settings);
            ListPreference mListPreference = (ListPreference) findPreference("downloadType");
            if(mListPreference==null){
                Log.i("ListPref","GOT NULL D:");
            }
            else  Log.i("ListPref", "NOT NULL :D");


        }
    }


}
