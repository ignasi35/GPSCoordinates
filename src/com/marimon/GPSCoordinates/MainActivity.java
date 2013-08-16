package com.marimon.GPSCoordinates;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.*;

public class MainActivity extends Activity implements LocationListener {
    private  static final String TAG="MainActivity"  ;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LocationManager locationManager = (LocationManager) this.getSystemService(
                Context.LOCATION_SERVICE);
        Log.d(TAG, locationManager.toString());
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, this);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0,0, this);
        }catch (Exception e){
            Log.e(TAG, e.toString()) ;
        }

        setText("Default text");
    }

    private void setText(String text) {
        TextView t =(TextView)findViewById(R.id.gpsCoordinatesView);
        t.setText("[" +new Date() +"]: "+ text);
    }

    @Override
    public void onLocationChanged(Location location) {
        Map<String, String> map =
        new UberMap()
        .put("longitude", location.getLongitude())
        .put("latitude", location.getLatitude())
        .put("altitude", location.getAltitude())
        .put("accuracy", location.getAccuracy())
        .put("speed", location.getSpeed())
        .put("time", location.getTime())
        .put("extras", asString(asMap(location.getExtras())))
        .asMap()
        ;
        setText(asString(map));
    }

    private Map<String, String> asMap(Bundle extras) {
        Set<String> keys = extras.keySet();
        Map<String, String> result = new HashMap<String, String>(keys.size()) ;
        for(String key:keys) {
            result.put(key, extras.get(key).toString() ) ;
        }
        return result ;
    }

    private String asString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(Map.Entry<String, String> entry:map.entrySet()){
            sb.append(entry.getKey()).append(':').append(entry.getValue()).append('\n');
        }
        return sb.append(']').toString();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        setText("provider: " + provider + " has extras...\n" +
           asString(asMap(extras))
        );
    }

    @Override
    public void onProviderEnabled(String provider) {
        setText("provider " + provider+" enabled.");
    }

    @Override
    public void onProviderDisabled(String provider) {
        setText("provider " + provider+" disabled.");
    }
}
