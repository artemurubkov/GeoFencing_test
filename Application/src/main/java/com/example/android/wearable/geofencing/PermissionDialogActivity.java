package com.example.android.wearable.geofencing;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by georgijs.demics on 25-May-16.
 */
public class PermissionDialogActivity extends AppCompatActivity {

    private final String LOG_TAG = PermissionDialogActivity.class.getSimpleName();

    public static final String LOG_TAG_EMAIL = /*TrackingConstants.LOG_TAG_EMAIL + */"PDA";

    public static final int LOCATIONS_PERMISSION_VALUE = 5;
    public static final int BEACON_REQUEST_CODE = 6;
    public static final int LOCATION_PERMISION_FOR_AUTO_START = 7;


    private static String[] PERMISSIONS_LOCATION = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static void startPermissionDialogActivity(Activity activity, int requestCode) {
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, PermissionDialogActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(LOG_TAG, "onCreate()");
        checkForPermission();
    }

    private void checkForPermission() {
        if (!isLocationPermissionGranted(getApplicationContext())) {
            requestLocationsPermission();
        } else {
            finishActivityWithResult(RESULT_OK);
        }
    }

    private void requestLocationsPermission() {
        //If will be approved, uncomment this, to handle rationale.
        //TODO: if rationale is used, then onRequestPermissionsResult method is not triggered -> need to fix this.
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            AlertDialog.Builder rationaleDialog = new AlertDialog.Builder(PermissionDialogActivity.this);
//            rationaleDialog.setMessage(getResources().getString(R.string.str_permission_rationale))
//                    .setPositiveButton(R.string.str_dialog_ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(PermissionDialogActivity.this, PERMISSIONS_LOCATION, TrackingConstants.LOCATIONS_PERMISSION_VALUE);
//                            finish();
//                        }
//                    }).show();
//        } else {
//            ActivityCompat.requestPermissions(PermissionDialogActivity.this, PERMISSIONS_LOCATION, TrackingConstants.LOCATIONS_PERMISSION_VALUE);
//        }

        //For now, we will just launch permission request. Remove, if rationale will be approved.
        ActivityCompat.requestPermissions(PermissionDialogActivity.this, PERMISSIONS_LOCATION, LOCATIONS_PERMISSION_VALUE);
    }

    private void finishActivityWithResult(int result) {
        setResult(result);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATIONS_PERMISSION_VALUE || requestCode == LOCATION_PERMISION_FOR_AUTO_START) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.i(LOG_TAG, "permission granted");
                finishActivityWithResult(RESULT_OK);
            } else {
                Logger.i(LOG_TAG, "permission denied");
                finishActivityWithResult(RESULT_CANCELED);
            }
        }
    }

    public static boolean isLocationPermissionGranted(Context context) {
        boolean is = (Build.VERSION.SDK_INT < 23
                || (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
        return is;
    }
}
