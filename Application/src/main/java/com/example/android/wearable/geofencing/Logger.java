package com.example.android.wearable.geofencing;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.BatteryManager;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static void i(String tag, String string) {
            android.util.Log.i(tag, "" + string);
        appendLogToFile(tag, string);
/*			try {
                 FileOutputStream fOut = ConnectedInsuranceApp.getContext().openFileOutput("ci_log.txt", Context.MODE_APPEND | Context.MODE_WORLD_READABLE);
		         String strToWrite = tag + ":" + string + "\r\n";
		         fOut.write(strToWrite.getBytes());
		         fOut.close();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }*/
    }

    public static void i(String tag, String string, Throwable t) {
            android.util.Log.i(tag, "" + string, t);
        appendLogToFile(tag, string);
    }

    public static void e(String tag, String string) {
            android.util.Log.e(tag, "" + string);
        appendLogToFile(tag, string);
    }

    public static void e(String tag, String string, Throwable t) {
            android.util.Log.e(tag, "" + string, t);
        appendLogToFile(tag, string);
    }

    private static void appendLogToFile(String tag, String message) {
        appendLogToFile(fullDateAsStringFormat.format(new Date()) + ", " + tag + ", " + message);
    }

    private static void appendLogToFile(String text) {
        File dir = null;
        try {
            if(BuildConfig.VERSION_CODE >= 18) {
                dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/GeoFencingTest");
            }
        } catch(Exception e){
            //non-standard Android - documents dir can't be returned
        }

        File logFile = null;
        if (dir != null) {
            logFile = new File(dir, "entersExits.txt");
        } else {
            logFile = new File("../" + GeofenceApplication.getContext().getApplicationInfo().dataDir + "/entersExits.txt");
        }
        if (!logFile.exists()) {
            try {
                if (dir != null) {
                    dir.mkdirs();
                }
                logFile.createNewFile();
            } catch (IOException e) {
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.append(", batLevel: " + getCurrentDeviceBatteryLevel());
            buf.newLine();
            buf.close();
        } catch (IOException e) {
        }
    }

    public static int getCurrentDeviceBatteryLevel() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        Intent batteryIntent = GeofenceApplication.getContext().registerReceiver(null, filter);
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        // Error checking that probably isn't needed but I added just in case.
        if (level == -1 || scale == -1 || status == BatteryManager.BATTERY_STATUS_CHARGING) {
            return 100;
        }
        return level;
    }

    public static final String fullDateAsStringPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final SimpleDateFormat fullDateAsStringFormat = new SimpleDateFormat(fullDateAsStringPattern);
}
