/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.wearable.geofencing;

import static com.example.android.wearable.geofencing.Constants.ALFA_ID;
import static com.example.android.wearable.geofencing.Constants.ALFA_LATITUDE;
import static com.example.android.wearable.geofencing.Constants.ALFA_LONGITUDE;
import static com.example.android.wearable.geofencing.Constants.ALFA_RADIUS_METERS;

import static com.example.android.wearable.geofencing.Constants.BIKIRNIEKU_ID;
import static com.example.android.wearable.geofencing.Constants.BIKIRNIEKU_LATITUDE;
import static com.example.android.wearable.geofencing.Constants.BIKIRNIEKU_LONGITUDE;
import static com.example.android.wearable.geofencing.Constants.BIKIRNIEKU_RADIUS_METERS;

import static com.example.android.wearable.geofencing.Constants.WORKPLACE_ID;
import static com.example.android.wearable.geofencing.Constants.WORKPLACE_LATITUDE;
import static com.example.android.wearable.geofencing.Constants.WORKPLACE_LONGITUDE;
import static com.example.android.wearable.geofencing.Constants.WORKPLACE_RADIUS_METERS;

import static com.example.android.wearable.geofencing.Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST;
import static com.example.android.wearable.geofencing.Constants.GEOFENCE_EXPIRATION_TIME;
import static com.example.android.wearable.geofencing.Constants.TAG;

import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA;
import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA_LATITUDE;
import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA_LONGITUDE;
import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA_RADIUS_METERS;

import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA_1;
import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA_LATITUDE_1;
import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA_LONGITUDE_1;
import static com.example.android.wearable.geofencing.Constants.GUSTAVA_ZEMGALA_RADIUS_METERS_1;

import static com.example.android.wearable.geofencing.Constants.FOREST_ID;
import static com.example.android.wearable.geofencing.Constants.FOREST_LATITUDE;
import static com.example.android.wearable.geofencing.Constants.FOREST_LONGITUDE;
import static com.example.android.wearable.geofencing.Constants.FOREST_RADIUS_METERS;

import static com.example.android.wearable.geofencing.Constants.HOME_ID;
import static com.example.android.wearable.geofencing.Constants.HOME_LATITUDE;
import static com.example.android.wearable.geofencing.Constants.HOME_LONGITUDE;
import static com.example.android.wearable.geofencing.Constants.HOME_RADIUS_METERS;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, ResultCallback<Status>, OnMapReadyCallback {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String GEOFENCE_TEST_ACTION = LOG_TAG + ".geofenceTestAction";

    // Internal List of Geofence objects. In a real app, these might be provided by an API based on
    // locations within the user's proximity.
    List<Geofence> gfList;

    // These will store hard-coded geofences in this sample app.
    private SimpleGeofence mAndroidBuildingGeofence;
    private SimpleGeofence g1;
    private SimpleGeofence g2;
    private SimpleGeofence g3;
    private SimpleGeofence g4;
    private SimpleGeofence g5;
    private SimpleGeofence g6;

    // Persistent storage for geofences.
    private SimpleGeofenceStore gfStorage;

    private LocationServices mLocationService;
    // Stores the PendingIntent used to request geofence monitoring.
    private PendingIntent mGeofenceRequestIntent;
    private GoogleApiClient mApiClient;

    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            Toast.makeText(this, "Action success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Action fail", Toast.LENGTH_SHORT).show();
        }
    }

    // Defines the allowable request types (in this example, we only add geofences).
    private enum REQUEST_TYPE {
        ADD
    }

    private REQUEST_TYPE mRequestType;

    private LongPressLocationSource mLocationSource;

    private EditText pointNameEt;


    @Override
    protected void onResume() {
        super.onResume();
        mLocationSource.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationSource.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_demo);

        mLocationSource = new LongPressLocationSource();

        pointNameEt = (EditText) findViewById(R.id.et);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Rather than displayng this activity, simply display a toast indicating that the geofence
        // service is being created. This should happen in less than a second.
        if (!isGooglePlayServicesAvailable()) {
            Log.e(TAG, "Google Play services unavailable.");
            finish();
            return;
        }

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        // Instantiate a new geofence storage area.
        gfStorage = new SimpleGeofenceStore(this);
        // Instantiate the current List of geofences.
        gfList = new ArrayList<Geofence>();
//        createGeofences();

        Logger.i(LOG_TAG, "permissions granted: " + PermissionDialogActivity.isLocationPermissionGranted(getApplicationContext()));
        if (!PermissionDialogActivity.isLocationPermissionGranted(getApplicationContext())) {
            PermissionDialogActivity.startPermissionDialogActivity(this, PermissionDialogActivity.LOCATION_PERMISION_FOR_AUTO_START);
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(gfList);
        return builder.build();
    }

    /**
     * In this sample, the geofences are predetermined and are hard-coded here. A real app might
     * dynamically create geofences based on the user's location.
     */
//    public void createGeofences() {
//        // Create internal "flattened" objects containing the geofence data.
//        mAndroidBuildingGeofence = new SimpleGeofence(
//                WORKPLACE_ID,                // geofenceId.
//                WORKPLACE_LATITUDE,
//                WORKPLACE_LONGITUDE,
//                WORKPLACE_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );
//        g1 = new SimpleGeofence(
//                GUSTAVA_ZEMGALA,                // geofenceId.
//                GUSTAVA_ZEMGALA_LATITUDE,
//                GUSTAVA_ZEMGALA_LONGITUDE,
//                GUSTAVA_ZEMGALA_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );
//
//        g2 = new SimpleGeofence(
//                GUSTAVA_ZEMGALA_1,                // geofenceId.
//                GUSTAVA_ZEMGALA_LATITUDE_1,
//                GUSTAVA_ZEMGALA_LONGITUDE_1,
//                GUSTAVA_ZEMGALA_RADIUS_METERS_1,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );
//
//        g3 = new SimpleGeofence(
//                ALFA_ID,                // geofenceId.
//                ALFA_LATITUDE,
//                ALFA_LONGITUDE,
//                ALFA_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );
//
//        g4 = new SimpleGeofence(
//                BIKIRNIEKU_ID,                // geofenceId.
//                BIKIRNIEKU_LATITUDE,
//                BIKIRNIEKU_LONGITUDE,
//                BIKIRNIEKU_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );
//
//        g5 = new SimpleGeofence(
//                FOREST_ID,                // geofenceId.
//                FOREST_LATITUDE,
//                FOREST_LONGITUDE,
//                FOREST_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );
//
//        g6 = new SimpleGeofence(
//                HOME_ID,                // geofenceId.
//                HOME_LATITUDE,
//                HOME_LONGITUDE,
//                HOME_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );
//
//        // Store these flat versions in SharedPreferences and add them to the geofence list.
//        gfStorage.setGeofence(WORKPLACE_ID, mAndroidBuildingGeofence);
//        gfStorage.setGeofence(GUSTAVA_ZEMGALA, g1);
//        gfStorage.setGeofence(GUSTAVA_ZEMGALA_1, g2);
//        gfStorage.setGeofence(ALFA_ID, g3);
//        gfStorage.setGeofence(BIKIRNIEKU_ID, g4);
//        gfStorage.setGeofence(FOREST_ID, g5);
//        gfStorage.setGeofence(HOME_ID, g6);
//        gfList.add(mAndroidBuildingGeofence.toGeofence());
//        gfList.add(g1.toGeofence());
//        gfList.add(g2.toGeofence());
//        gfList.add(g3.toGeofence());
//        gfList.add(g4.toGeofence());
//        gfList.add(g5.toGeofence());
//        gfList.add(g6.toGeofence());
//    }

    private boolean isGoogleServicesConnected;

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If the error has a resolution, start a Google Play services activity to resolve it.
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Logger.e(TAG, "Exception while resolving connection error.", e);
            }
        } else {
            int errorCode = connectionResult.getErrorCode();
            Logger.e(TAG, "Connection to Google Play services failed with error code " + errorCode);
        }
        Toast.makeText(this, "Service stop", Toast.LENGTH_SHORT).show();
        isGoogleServicesConnected = false;
    }

    /**
     * Once the connection is available, send a request to add the Geofences.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Get the PendingIntent for the geofence monitoring request.
        // Send a request to add the current geofences.
        isGoogleServicesConnected = true;

        addPointsToGeofences();

//        finish();
    }

    private void addPointsToGeofences() {
        if (isGoogleServicesConnected && gfList.size() > 0) {
            mGeofenceRequestIntent = getGeofenceTransitionPendingIntent();
        /*LocationServices.GeofencingApi.addGeofences(mApiClient, gfList,
                mGeofenceRequestIntent);*/
            LocationServices.GeofencingApi.addGeofences(
                    mApiClient,
                    getGeofencingRequest(),
                    getGeofenceTransitionPendingIntent()
            ).setResultCallback(this);
            Toast.makeText(this, getString(R.string.geofence_point_added), Toast.LENGTH_SHORT).show();
            isGoogleServicesConnected = true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (null != mGeofenceRequestIntent) {
            LocationServices.GeofencingApi.removeGeofences(mApiClient, mGeofenceRequestIntent);
        }
        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT).show();
        isGoogleServicesConnected = false;
    }


    /**
     * Checks if Google Play services is available.
     *
     * @return true if it is.
     */
    private boolean isGooglePlayServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Google Play services is available.");
            }
            return true;
        } else {
            Log.e(TAG, "Google Play services is unavailable.");
            return false;
        }
    }

    /**
     * Create a PendingIntent that triggers GeofenceTransitionIntentService when a geofence
     * transition occurs.
     */
    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(this, GeofenceService.class);
        intent.setAction(GEOFENCE_TEST_ACTION);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setLocationSource(mLocationSource);
        map.setOnMapLongClickListener(mLocationSource);
        map.setMyLocationEnabled(true);
        googleMap = map;
    }

    private GoogleMap googleMap;


    /**
     * A {@link LocationSource} which reports a new location whenever a user long presses the map
     * at
     * the point at which a user long pressed the map.
     */
    private class LongPressLocationSource implements LocationSource, GoogleMap.OnMapLongClickListener {

        private OnLocationChangedListener mListener;

        /**
         * Flag to keep track of the activity's lifecycle. This is not strictly necessary in this
         * case because onMapLongPress events don't occur while the activity containing the map is
         * paused but is included to demonstrate best practices (e.g., if a background service were
         * to be used).
         */
        private boolean mPaused;

        @Override
        public void activate(LocationSource.OnLocationChangedListener listener) {
            mListener = listener;
        }

        @Override
        public void deactivate() {
            mListener = null;
        }

        @Override
        public void onMapLongClick(LatLng point) {
//            if (mListener != null && !mPaused) {
//                Location location = new Location("LongPressLocationProvider");
//                location.setLatitude(point.latitude);
//                location.setLongitude(point.longitude);
            String pointName = pointNameEt.getText().toString();
            if (TextUtils.isEmpty(pointName)) {
                pointName = "Point " + (gfList.size() - 1);
            }
            if (googleMap != null) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title(pointName));
            }
            SimpleGeofence sgf = new SimpleGeofence(
                    pointName,                // geofenceId.
                    point.latitude,
                    point.longitude,
                    70.f,
                    GEOFENCE_EXPIRATION_TIME,
                    Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
            );
            gfList.add(sgf.toGeofence());
            gfStorage.setGeofence(pointName, sgf);
            pointNameEt.setText("");
            addPointsToGeofences();
//                mListener.onLocationChanged(location);
//            }
        }

        public void onPause() {
            mPaused = true;
        }

        public void onResume() {
            mPaused = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionDialogActivity.LOCATION_PERMISION_FOR_AUTO_START) {
            Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT);
        }
    }
}
