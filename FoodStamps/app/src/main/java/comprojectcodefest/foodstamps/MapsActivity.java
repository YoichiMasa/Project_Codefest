package comprojectcodefest.foodstamps;

import android.content.IntentSender;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.*;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
//    private Spinner healthOptions;
//    private Marker mark;
//    private ArrayList<Marker> markerList;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        createMapView();
//        markerList = new ArrayList<Marker>();
//        healthOptions = (Spinner) findViewById(R.id.healthOptions);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.scale, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        healthOptions.setAdapter(adapter);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
        .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
        .addApi(LocationServices.API)
        .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        InputStream inputStream = getResources().openRawResource(R.raw.paphila);
        CSVFile csvFile = new CSVFile(inputStream);
        Hashtable locationResult = csvFile.read();
        Iterator<Map.Entry> keys;
        Map.Entry            entry;
        keys = locationResult.entrySet().iterator();
        while(keys.hasNext())
        {
            entry = keys.next();
            String storeName = entry.getKey().toString();
            PairSet pairReceived = (PairSet)entry.getValue();
            LatLng receivedLoc = pairReceived.returnLoc();
            int receivedH = pairReceived.returnHealthy();

            addMarker(storeName, receivedLoc, receivedH);

        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        createMapView();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == mMap){
                mMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == mMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }


    /**
     * Adds a marker to the map
     */
    private void addMarker(String storeName, LatLng storeLoc, int healthy){

        /** Make sure that the map has been initialised **/
        if(null != mMap){
            if(healthy == 1)
            {mMap.addMarker(new MarkerOptions()
                    .position(storeLoc)
                    .title(storeName)
                    .snippet("Unhealthy")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            else if(healthy == 2)
            { mMap.addMarker(new MarkerOptions()
                    .position(storeLoc)
                    .title(storeName)
                    .snippet("Average")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }
            else if(healthy == 3)
            {mMap.addMarker(new MarkerOptions()
                    .position(storeLoc)
                    .title(storeName)
                    .snippet("Healthy")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        };
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        mMap.setMyLocationEnabled(true);
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        Circle circle = mMap.addCircle(new CircleOptions().center(latLng).radius(1000));
        circle.setVisible(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, getZoomLevel(circle)));
    }

    public int getZoomLevel(Circle circle)
    {
        int zoomLevel = 0;
        if (circle != null){
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel =(int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}



