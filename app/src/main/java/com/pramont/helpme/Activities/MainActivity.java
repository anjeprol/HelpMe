package com.pramont.helpme.Activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pramont.helpme.Fragments.Buttons;
import com.pramont.helpme.Fragments.Contacts;
import com.pramont.helpme.Fragments.Settings;
import com.pramont.helpme.Pojos.NotificationSettings.UserSettings;
import com.pramont.helpme.R;
import com.pramont.helpme.Utils.Constants;
import com.pramont.helpme.Utils.Preferences;
import com.pramont.helpme.Utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
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


        setupViewPager(mViewPager);
        setupTabIcons();
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
