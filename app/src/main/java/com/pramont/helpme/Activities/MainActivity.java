package com.pramont.helpme.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private StringBuilder locat = new StringBuilder();
    private UserSettings mProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils utils = new Utils();

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mProfile = utils.getUserData(new Preferences(getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)));

        mTabLayout.setupWithViewPager(mViewPager);

        if(checkPermissions())
        {
            startGPS();
        }


        setupViewPager(mViewPager);
        setupTabIcons();
    }

    public void startGPS(){
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 10:
                //   configure_button();
                updateLocation();
                break;
            default:
                break;
        }
    }

    private boolean checkPermissions() {
        final int sendSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        final int coarseLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        final int fineLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        final int internet = ContextCompat.checkSelfPermission(this,
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
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        10);
                return true;
            }

        }
        return false;
    }

    @SuppressLint("MissingPermission")
    void updateLocation() {
        //noinspection MissingPermission
        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 5000, 0, mLocationListener);
    }


    /*
    * Method to load the shared preferences
    * */
    private void loadData() {
        Preferences preferences = new Preferences(getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE));

        mProfile = preferences.getPreferences();
    }

    /*
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

    /*
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
