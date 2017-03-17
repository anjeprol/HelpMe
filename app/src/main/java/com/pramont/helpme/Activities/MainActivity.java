package com.pramont.helpme.Activities;

import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pramont.helpme.Emails.Gmail;
import com.pramont.helpme.Fragments.Buttons;
import com.pramont.helpme.Fragments.Contacts;
import com.pramont.helpme.Fragments.Settings;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    private final static String TAG = "MainActivity";

    private Toolbar     mToolbar;
    private TabLayout   mTabLayout;
    private ViewPager   mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar        = (Toolbar)     findViewById(R.id.toolbar);
        mViewPager      = (ViewPager)   findViewById(R.id.viewPager);
        mTabLayout      = (TabLayout)   findViewById(R.id.tabs);

        setSupportActionBar(mToolbar);
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true); to add the arrow getting back to previous activity

        mTabLayout.setupWithViewPager(mViewPager);

        setupViewPager(mViewPager);
        setupTabIcons();


        //TODO this is to check the status of the button, for now, is not checking, dummy data
        checkStatus();

        try {
            // TODO activate the function of send the email
            //onPermissionChecked();
            Log.d(Constants.TAG_EMAIL,getString(R.string.email_sending));
        } catch (Exception e) {
            Log.e(Constants.TAG_EMAIL,getString(R.string.error)+e.getMessage(), e);
        }
    }


    /*
    * Method to check the status of the button, if is active or not, depends if is the first time
    * */

    private void checkStatus(){

    }

    /*
    * Method to add the icons to each tab from toolBar
    * */
    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_button_24dp,
                R.drawable.ic_people_24dp,
                R.drawable.ic_settings_24dp
        };

        for(int ind = 0; ind < 3 ; ind++ ){
            mTabLayout.getTabAt(ind).setIcon(tabIcons[ind]);
        }
    }

    /*
    * Method to add the fragments into the viewPager
    * */

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Buttons(), getString(R.string.title_1));
        adapter.addFrag(new Contacts(),getString(R.string.title_2));
        adapter.addFrag(new Settings(),getString(R.string.title_3));
        viewPager.setAdapter(adapter);
    }

    /*
    * Class for setup the viewPager using the FragmentPagerAdapter
    * */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList      = new ArrayList<>();
        private final List<String>   mFragmentTitleList = new ArrayList<>();

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

    /*
    * Methond to send the email
    * */
    public void onPermissionChecked()
    {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //Sending mail
            Gmail.sendMail();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
