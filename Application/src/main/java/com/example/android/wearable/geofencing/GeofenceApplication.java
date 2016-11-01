package com.example.android.wearable.geofencing;

import android.app.Application;
import android.content.Context;

/**
 * Created by artem.urubkov on 2/24/2016.
 */
public class GeofenceApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
