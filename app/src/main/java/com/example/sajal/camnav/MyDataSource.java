package com.example.sajal.camnav;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.jwetherell.augmented_reality.data.DataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sajal on 30-03-2015.
 */
public class MyDataSource extends DataSource {
    private List<Marker> cachedMarkers = new ArrayList<Marker>();
    private static Bitmap icon = null;
    private List<Marker> fromMapActivity = new ArrayList<Marker>();
    @Override
    public List<Marker> getMarkers() {
        for(int i=0;i<fromMapActivity.size();i++){
            cachedMarkers.add(fromMapActivity.get(i));
        }
        return  cachedMarkers;
    }
    public void setMarkers(LatLng[] stepData){

        for(int i=0;i<stepData.length;i++){
            fromMapActivity.add(new Marker("Turn : "+i,stepData[i].latitude,stepData[i].longitude,0, Color.GREEN));
        }

    }
}
