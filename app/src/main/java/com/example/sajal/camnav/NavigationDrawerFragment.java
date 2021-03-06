package com.example.sajal.camnav;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Boolean mUserLearnedDrawer=false;
    private Boolean mFromSavedInstanceState=false;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;



    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        rootView.setFitsSystemWindows(true);

        return rootView;
    }


    public void setUp(int fragmentID, DrawerLayout drawerLayout, final Toolbar toolbar) {
        View containerView = getActivity().findViewById(fragmentID);    
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               if(!mUserLearnedDrawer){
                   mUserLearnedDrawer=true;
                   saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
               }
               getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView,slideOffset);

                   if(slideOffset<0.4){
                       toolbar.setAlpha(1-slideOffset);
                   }


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
    public static void saveToPreferences(Context context,String preferenceString,String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceString,preferenceValue);
        editor.apply();
    }
    public static String readFromPreferences(Context context,String preferenceString,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceString,defaultValue);
    }
}
