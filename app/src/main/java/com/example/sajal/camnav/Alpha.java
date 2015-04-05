package com.example.sajal.camnav;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Alpha extends ActionBarActivity {

    String radioButtonChecked;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alpha);
        recyclerView = (RecyclerView)findViewById(R.id.mDrawerList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        List<RecylerViewData> data = getData();
        adapter = new RecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        drawerFragment.setUp(R.id.fragment_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);





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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        Intent i = new Intent(this, Beta.class);
        EditText userName = (EditText) findViewById(R.id.enterNameText);
        EditText contactNum = (EditText) findViewById(R.id.editNumberText);
        String userNameString = userName.getText().toString();
        String contactNumString = contactNum.getText().toString();
        i.putExtra("userName", userNameString);
        i.putExtra("contactNum", contactNumString);
        i.putExtra("radioButton", radioButtonChecked);
        startActivity(i);


    }

    public void onRadioButtonClicked(View view) {
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

    }
    public static List<RecylerViewData> getData(){
        Log.i("getData","Get data was called");
        List<RecylerViewData> data = new ArrayList<>();
        int[] icons = {R.mipmap.ic_subway,R.mipmap.ic_taxi,R.mipmap.ic_bus};
        String[] titles = {"Metro Station","Taxi Stand","Bus Stop"};
        for(int i=0;i<titles.length && i<icons.length;i++){
            RecylerViewData current = new RecylerViewData();
            current.iconId=icons[i];
            current.title=titles[i];
            data.add(current);
            Log.i("Data : ",current.title);
        }
        return data;

    }


}
