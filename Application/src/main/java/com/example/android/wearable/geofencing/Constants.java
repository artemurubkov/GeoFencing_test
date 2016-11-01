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

import android.net.Uri;

import com.google.android.gms.location.Geofence;

/** Constants used in companion app. */
public final class Constants {

    private Constants() {
    }

    public static final String TAG = "ExampleGeofencingApp";

    // Request code to attempt to resolve Google Play services connection failures.
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    // Timeout for making a connection to GoogleApiClient (in milliseconds).
    public static final long CONNECTION_TIME_OUT_MS = 100;

    // For the purposes of this demo, the geofences are hard-coded and should not expire.
    // An app with dynamically-created geofences would want to include a reasonable expiration time.
    public static final long GEOFENCE_EXPIRATION_TIME = Geofence.NEVER_EXPIRE;

    // Geofence parameters for the Android building on Google's main campus in Mountain View.
    public static final String WORKPLACE_ID = "workplace";
    public static final double WORKPLACE_LATITUDE = 56.970688;
    public static final double WORKPLACE_LONGITUDE = 24.161230;
    public static final float WORKPLACE_RADIUS_METERS = 70.0f;

    // Geofence parameters for the Yerba Buena Gardens near the Moscone Center in San Francisco.
    public static final String GUSTAVA_ZEMGALA = "gustava_zemgala";
    public static final double GUSTAVA_ZEMGALA_LATITUDE = 56.972701;
    public static final double GUSTAVA_ZEMGALA_LONGITUDE = 24.165375;
    public static final float GUSTAVA_ZEMGALA_RADIUS_METERS = 50.0f;

    public static final String GUSTAVA_ZEMGALA_1 = "gustava_zemgala_1";
    public static final double GUSTAVA_ZEMGALA_LATITUDE_1 = 56.972912;
    public static final double GUSTAVA_ZEMGALA_LONGITUDE_1 = 24.166083;
    public static final float GUSTAVA_ZEMGALA_RADIUS_METERS_1 = 150.0f;

    public static final String BIKIRNIEKU_ID = "bikirnieku";
    public static final double BIKIRNIEKU_LATITUDE = 56.973498;
    public static final double BIKIRNIEKU_LONGITUDE = 24.167862;
    public static final float BIKIRNIEKU_RADIUS_METERS = 30.0f;

    public static final String ALFA_ID = "alfa";
    public static final double ALFA_LATITUDE = 56.983204;
    public static final double ALFA_LONGITUDE = 24.202774;
    public static final float ALFA_RADIUS_METERS = 450.0f;

    public static final String FOREST_ID = "forest";
    public static final double FOREST_LATITUDE = 56.985998;
    public static final double FOREST_LONGITUDE = 24.212901;
    public static final float FOREST_RADIUS_METERS = 50.0f;

    public static final String HOME_ID = "home";
    public static final double HOME_LATITUDE = 56.988182;
    public static final double HOME_LONGITUDE = 24.226432;
    public static final float HOME_RADIUS_METERS = 60.0f;

    // The constants below are less interesting than those above.

    // Path for the DataItem containing the last geofence id entered.
    public static final String GEOFENCE_DATA_ITEM_PATH = "/geofenceid";
    public static final Uri GEOFENCE_DATA_ITEM_URI =
            new Uri.Builder().scheme("wear").path(GEOFENCE_DATA_ITEM_PATH).build();
    public static final String KEY_GEOFENCE_ID = "geofence_id";

    // Keys for flattened geofences stored in SharedPreferences.
    public static final String KEY_LATITUDE = "com.example.wearable.geofencing.KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "com.example.wearable.geofencing.KEY_LONGITUDE";
    public static final String KEY_RADIUS = "com.example.wearable.geofencing.KEY_RADIUS";
    public static final String KEY_EXPIRATION_DURATION =
            "com.example.wearable.geofencing.KEY_EXPIRATION_DURATION";
    public static final String KEY_TRANSITION_TYPE =
            "com.example.wearable.geofencing.KEY_TRANSITION_TYPE";
    // The prefix for flattened geofence keys.
    public static final String KEY_PREFIX = "com.example.wearable.geofencing.KEY";

    // Invalid values, used to test geofence storage when retrieving geofences.
    public static final long INVALID_LONG_VALUE = -999l;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;

}
