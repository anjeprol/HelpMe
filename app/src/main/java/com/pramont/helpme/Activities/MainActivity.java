package com.pramont.helpme.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.pramont.helpme.Fragments.Buttons;
import com.pramont.helpme.Fragments.Contacts;
import com.pramont.helpme.Fragments.Settings;
import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;
import com.pramont.helpme.Utils.Preferences;
import com.pramont.helpme.Utils.Utils;
import com.pramont.helpme.sms.Notifications;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private final static String TAG = "MainActivity";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    /**Used to turn gps on.*/
    private GoogleApiClient googleApiClient;
    /**To request gps on.*/
    Context context ;
    /**To get user location.*/
    private FusedLocationProviderClient mFusedLocationClient;
    /**Ti get location.*/
    private LocationCallback locationCallback;
    public static final int REQUEST_LOCATION = 199;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private StringBuilder locat = new StringBuilder();
    private UserSettings mProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        Utils utils = new Utils(this, mProfile);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mProfile = utils.getUserData(new Preferences(getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)));

        mTabLayout.setupWithViewPager(mViewPager);

        utils.checkPermissions();

        setupViewPager(mViewPager);
        setupTabIcons();
    }
/*
    /**
     * This method is to start collecting the current location.

    public void startTracking(){
        turnOnGps();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation();
                locat.append(getString(R.string.url_gmaps))
                        .append(location.getLatitude())
                        .append(",")
                        .append(location.getLongitude());
                mProfile.setLocation(locat.toString());
                locat = new StringBuilder();

                FragmentActivity fragmentActivity = new FragmentActivity();
                ArrayList<String> number = new ArrayList<>();
                number.add("3339567559");
                mProfile.setBodyMessage("");
                mProfile.setDefaultMessage(getString(R.string.default_msg_text));
                mProfile.setPhoneNumbers(number);
                // new Notifications(fragmentActivity).sendSMS(mProfile);

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
            }
        };
    }


    /*
     * This method is to ask for enable the gps.

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

    @SuppressLint("MissingPermission")
    void updateLocation() {
        //noinspection MissingPermission
        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);

    }

    @SuppressLint("MissingPermission")
    public void stopRequestingLocation() {
        try {
            if (mFusedLocationClient.getLocationAvailability() != null || mFusedLocationClient.getLocationAvailability().isSuccessful()) {
                mFusedLocationClient.removeLocationUpdates(locationCallback);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
*/


    private void loadData() {
        mProfile = new Utils()
                .getUserData(
                        new Preferences(this
                                .getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)));
    }

    /**
    * Method to add the icons to each tab from toolBar
    * */
    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_button_24dp,
                R.drawable.ic_settings_24dp,
                R.drawable.ic_people_24dp
        };

        for (int ind = 0; ind < 3; ind++)
        {
            mTabLayout.getTabAt(ind).setIcon(tabIcons[ind]);
        }
    }

    /**
    * Method to add the fragments into the viewPager
    * */

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Buttons(), getString(R.string.title_1));
        adapter.addFrag(new Settings(), getString(R.string.title_3));
        adapter.addFrag(new Contacts(), getString(R.string.title_2));
        viewPager.setAdapter(adapter);
    }


    /*
    * Class for setup the viewPager using the FragmentPagerAdapter
    * */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
    }
}
