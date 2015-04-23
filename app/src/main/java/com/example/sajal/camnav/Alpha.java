package com.example.sajal.camnav;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class Alpha extends ActionBarActivity implements RecyclerViewAdapter.ClickListener{

    //String radioButtonChecked;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    TextView seekBarText;
    int radiusStringConverted;
    SeekBar seekBar;
    String radiusString;

    @Override
    protected void onResume() {
        super.onResume();
        switch(radiusString){
            case "1": radiusStringConverted=500;
                break;
            case "2": radiusStringConverted=1000;
                break;
            case "3": radiusStringConverted=1500;
                break;
            case "4": radiusStringConverted=2000;
                break;



        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        radiusString =  sp.getString("downloadType","-1");
        Log.i("PrefenceAlpha",radiusString);
        seekBarText.setText("I can walk " + radiusStringConverted + " meters");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        switch(radiusString){
            case "1": radiusStringConverted=500;
                break;
            case "2": radiusStringConverted=1000;
                break;
            case "3": radiusStringConverted=1500;
                break;
            case "4": radiusStringConverted=2000;
                break;



        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        radiusString =  sp.getString("downloadType","-1");
        Log.i("PrefenceAlpha",radiusString);
        seekBarText.setText("I can walk " + radiusStringConverted + " meters");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alpha);
        recyclerView = (RecyclerView)findViewById(R.id.mDrawerList);
        recyclerView.setHasFixedSize(true);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        radiusString =  sp.getString("downloadType","-1");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        List<RecylerViewData> data = getData();
        adapter = new RecyclerViewAdapter(this,data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
       // recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
          //      getApplicationContext()
      //  ));
        recyclerView.setScrollContainer(false);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        seekBarText = (TextView) findViewById(R.id.seekBarText);
        switch(radiusString){
            case "1": radiusStringConverted=500;
                      break;
            case "2": radiusStringConverted=1000;
                break;
            case "3": radiusStringConverted=1500;
                break;
            case "4": radiusStringConverted=2000;
                break;



        }
        seekBarText.setText("I can walk " + radiusStringConverted + " meters");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Log.i("PrefenceAlpha",sp.getString("downloadType","-1"));
       /* seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBarText.setText("I can walk " + seekBar.getProgress() + " meters");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarText.setText("I can walk " + Globals.mWalkRadius + " meters");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }); */





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alpha, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MyPreferencesActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

   /* public void onClick(View view) {
        Intent i = new Intent(this, Beta.class);
        EditText userName = (EditText) findViewById(R.id.enterNameText);
        EditText contactNum = (EditText) findViewById(R.id.editNumberText);
        String userNameString = userName.getText().toString();
        String contactNumString = contactNum.getText().toString();
        i.putExtra("userName", userNameString);
        i.putExtra("contactNum", contactNumString);
        i.putExtra("radioButton", radioButtonChecked);
        startActivity(i);


    } */

   /* public void onRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) view;
        boolean checked = radioButton.isChecked();
        Context context = getApplicationContext();
        switch (view.getId()) {
            case R.id.metroRadioButton:
                if (checked) {
                    radioButtonChecked = radioButton.getText().toString();
                    Toast toast = Toast.makeText(context, radioButtonChecked, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.taxiRadioButton:
                if (checked) {
                    radioButtonChecked = radioButton.getText().toString();
                    Toast toast = Toast.makeText(context, radioButtonChecked, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.busRadioButton:
                if (checked) {
                    radioButtonChecked = radioButton.getText().toString();
                    Toast toast = Toast.makeText(context, radioButtonChecked, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }

    } */
    public static List<RecylerViewData> getData(){
        Log.i("getData","Get data was called");
        List<RecylerViewData> data = new ArrayList<>();
        int[] icons = {R.mipmap.ic_subway,R.mipmap.ic_taxi,R.mipmap.ic_bus,R.mipmap.ic_atm,R.mipmap.ic_drugstore};
        String[] titles = {"Metro Station","Taxi Stand","Bus Stop","ATM","Medicine Shop"};
        for(int i=0;i<titles.length && i<icons.length;i++){
            RecylerViewData current = new RecylerViewData();
            current.iconId=icons[i];
            current.title=titles[i];
            data.add(current);
            Log.i("Data : ",current.title);
        }
        return data;

    }


    @Override
    public void itemClicked(View view, int position,List<RecylerViewData> data) {
        Intent i = new Intent(this, Beta.class);
        i.putExtra("radioButton",data.get(position).title);
        i.putExtra("walk_radius",radiusStringConverted);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {


            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


    }


}
