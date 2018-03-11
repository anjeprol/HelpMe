package com.pramont.helpme.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.R;
import com.pramont.helpme.sms.Notifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by antoniopradoo on 3/30/17.
 */

public class Utils  extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks{

    /**Used to turn gps on.*/
    private GoogleApiClient googleApiClient;
    /**To request gps on.*/
    Context context ;
    /**To get user location.*/
    private FusedLocationProviderClient mFusedLocationClient;
    /**Ti get location.*/
    private LocationCallback locationCallback;
    private static final int REQUEST_LOCATION = 199;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private StringBuilder locat = new StringBuilder();
    private UserSettings mProfile;

    public Utils(Context context, UserSettings userSettings) {
        this.context = context;
        this.mProfile = userSettings;
    }

    public Utils() {
    }

    /**
     * @Description method to split the emails and phones that are concatenated separated by comas
     * and return the ArrayList
     *
     * @Param emails String
     */
    public ArrayList<String> splitString(String string) {
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(string.split(",")));

        for (String value : stringArrayList)
        {
            System.out.println(value);
        }
        return stringArrayList;
    }

    /**
     * @Description Method to read the sharedPreferences
     *
     * @Param preferences Preferences
     */
    public UserSettings getUserData(Preferences preferences){
        UserSettings userSettings = preferences.getPreferences();
        return userSettings;
    }

    public boolean checkPermissions() {
        final int sendSMS = ContextCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS);

        final int coarseLocation = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        final int fineLocation = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        final int internet = ContextCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET);

        final List<String> listPermissionsNeeded = new ArrayList<>();


        if (sendSMS != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }

        if (coarseLocation != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (fineLocation != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (internet != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }

        //Checking the version of the current SDK
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!listPermissionsNeeded.isEmpty())
            {
                ActivityCompat.requestPermissions((Activity) context,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        REQUEST_LOCATION);
                return true;
            }

        }
        return false;
    }


    /**
     * This method is to start collecting the current location.
     */
    @SuppressLint("MissingPermission")
    public void startTracking(LocationManager locationManager, UserSettings userSettings){
        mProfile = userSettings;
        mLocationManager = locationManager;
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locat.append(context.getString(R.string.url_gmaps))
                        .append(location.getLatitude())
                        .append(",")
                        .append(location.getLongitude());
                mProfile.setLocation(locat.toString());
                locat = new StringBuilder();
                mProfile.setDefaultMessage(context.getString(R.string.default_msg_text));
                new Notifications(new FragmentActivity()).sendSMS(mProfile);

                Log.d("LOCATION: ", mProfile.getLocation());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                turnOnGps();
            }
        };
        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);
    }

    /**
     * This method is to ask for enable the gps.
     */
    public void turnOnGps (){
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(final ConnectionResult connectionResult) {

                        }
                    }).build();
        }
        googleApiClient.connect();

        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setNeedBle(true);
        builder.setAlwaysShow(true);

        final Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(final Task<LocationSettingsResponse> task) {
                try {
                    final LocationSettingsResponse response = task.getResult(ApiException.class);
                } catch (final ApiException exception) {
                    if (exception.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            final ResolvableApiException resolvable = (ResolvableApiException) exception;
                            resolvable.startResolutionForResult(
                                    (Activity) context,
                                    REQUEST_LOCATION);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public boolean checkGPSStatus() {
        final String locationProviders = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (locationProviders == null || locationProviders.equals("")) {
            turnOnGps();
            return false;
        }

        if (checkPermissions()) {
            return true;
        }

        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        checkGPSStatus();
    }
}
