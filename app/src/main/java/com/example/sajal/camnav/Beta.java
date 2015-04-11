package com.example.sajal.camnav;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Beta extends ActionBarActivity {

    final String TAG = "MyIntentExample";
    Location loc;
    String radioButton;
    static int[] i = new int[1];
    private String searchType;
    private HandleJSON obj;
    LatLng[] latLnStore=null;
    String[] names=null;
    private Toolbar toolbar;
    private GoogleMap mMap;
    CardView myCard;
    int walkRadius;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beta);
        Context context = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerReceiver(receiver,new IntentFilter(GetLocationService.BROADCAST_ACTION));
        Log.i(TAG,"We're inside Oncreate");
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        drawerFragment.setUp(R.id.fragment_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);
        myCard = (CardView)findViewById(R.id.card_view);
        Bundle AlphaData = getIntent().getExtras();
        if (AlphaData == null) {
            return;
        }

        String walk_radius = AlphaData.getString("walk_radius");
        walkRadius = Integer.parseInt(walk_radius);
        Log.i("walkRadInt",walkRadius+"");
       // String contactNum = AlphaData.getString("contactNum");
        radioButton = AlphaData.getString("radioButton");
        TextView betaText = (TextView) findViewById(R.id.PlaceOfInterestText);
        betaText.setText("Please wait while we fetch nearby "+radioButton+"s");
        Intent i= new Intent(context, GetLocationService.class);
        i.putExtra("KEY1", "Value to be used by the service");
        Log.i(TAG,"Service about to start");
        context.startService(i);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {


        i[0]=0;
        super.onPause();
        unregisterReceiver(receiver);
        Intent i= new Intent(getApplicationContext(), GetLocationService.class);
        getApplicationContext().stopService(i);


    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG,"We're inside ONRECV BETA ACTIVITY");

            Bundle extra = intent.getExtras();

            loc = (Location)extra.get("Location");

           /* TextView betaStartLocationLat = (TextView) findViewById(R.id.BetaStartLocationLat);
            TextView betaStartLocationLon = (TextView) findViewById(R.id.BetaStartLocationLong);
            TextView betaProvider = (TextView) findViewById(R.id.betaProvider);
            betaStartLocationLat.setText("Latitude : " + loc.getLatitude());
            betaStartLocationLon.setText("Longitude : " + loc.getLongitude());
            betaProvider.setText(loc.getProvider()); */
            if(i[0]==0){
                setUpMapIfNeeded();
                String locationParameters = loc.getLatitude() + "," + loc.getLongitude();
                if(radioButton.equals("Metro Station"))
                {
                    searchType="subway_station";
                }
                if(radioButton.equals("Bus Stop")){
                    searchType="bus_station";
                }
                if(radioButton.equals("Taxi Stand")){
                    searchType="taxi_stand";
                }
                String intermediateURL = "&radius="+walkRadius+"&types=" + searchType + "&key=AIzaSyCxlMg0r_bb0R07g6D0lB2dBT9lijsTB-0";
                String baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
                String finalURL = baseURL + locationParameters + intermediateURL;


                obj = new HandleJSON(finalURL);

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        obj.fetchJSON();
                        long futureTime = System.currentTimeMillis()+6000;
                        while(System.currentTimeMillis()<futureTime){
                            synchronized (this){
                                try{
                                    wait(futureTime-System.currentTimeMillis());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        handler.sendEmptyMessage(0);
                    }

                };
                Thread jsonThread = new Thread(r);
                jsonThread.start();
                i[0]++;

            }


        }
    };
     Handler handler = new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {

            Log.i("Gamma","waiting for parse");
            if(obj.parsingComplete){

                Log.i("Gamma","Parsing Complete");

                latLnStore = obj.getLatlnStore();

                names = obj.getNames();
                globalStoreLatLng gs = (globalStoreLatLng) getApplication();
                gs.setLatLnStore(latLnStore);
                populateDrawer();
                ObjectAnimator animX = ObjectAnimator.ofFloat(myCard, "x", 300f);
                ObjectAnimator animAlpha = ObjectAnimator.ofFloat(myCard, "alpha", 0);
                AnimatorSet animXAlpha = new AnimatorSet();
                animXAlpha.playTogether(animX,animAlpha);
                animXAlpha.setDuration(500);
                animXAlpha.start();
                animXAlpha.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        TextView cardTextFirst = (TextView) findViewById(R.id.cardTextFirst);
                        TextView betaText = (TextView) findViewById(R.id.PlaceOfInterestText);
                        betaText.setText("Slide the drawer from the left");
                        cardTextFirst.setText("The Nearest "+radioButton+"s have been fetched");
                        myCard.animate().alpha(1).x(9f).setDuration(500);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                //myCard.animate().alpha(0).x(300f).setDuration(1000);




            }
            return true;
        }
         });
    @Override
    protected void onDestroy() {

            super.onDestroy();
        i[0]=0;
        Intent i= new Intent(getApplicationContext(), GetLocationService.class);
        getApplicationContext().stopService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i= new Intent(getApplicationContext(), GetLocationService.class);
        getApplicationContext().startService(i);
        registerReceiver(receiver,new IntentFilter(GetLocationService.BROADCAST_ACTION));
    }

   /* public void onClickPlaces(View view) {

        Intent i = new Intent(this,Gamma.class);
        if(latLnStore==null || names==null){
            Toast toast = Toast.makeText(getApplicationContext(),"Please wait while we fetch crap",Toast.LENGTH_SHORT);
            toast.show();
        }
        else {

            i.putExtra("names", names);
            globalStoreLatLng gs = (globalStoreLatLng) getApplication();
            gs.setLatLnStore(latLnStore);
            startActivity(i);
        }


    } */

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_beta_activity))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        LatLng currentPosition = new LatLng(loc.getLatitude(),loc.getLongitude());
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                currentPosition).zoom(17).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet(
                        "Lat:" + loc.getLatitude() + "Lng:"
                                + loc.getLongitude())
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Start Position"));
    }
    private void populateDrawer(){
        final LatLng[] latLnStore;
        String[] names;

        latLnStore= obj.getLatlnStore();
        names = obj.getNames();
        ListAdapter mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,names){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };
        ListView gammaListView = (ListView) findViewById(R.id.gammaListView);
        gammaListView.setAdapter(mAdapter);

        gammaListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LatLng chosenOne =  latLnStore[position];
                        Intent i = new Intent(Beta.this,MapsActivity.class);
                        i.putExtra("destinationMarker",chosenOne);
                        startActivity(i);
                    }
                }
        );
    }


}
