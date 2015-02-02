package ru.sfu.pi.piinformer;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAGLOCATE = MainActivity.class.getSimpleName();

    public String[] mPageTitles; /* Массив заголовков страниц (меню) */
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAGLOCATE, "onCreate starts, savedInstanceState: " + savedInstanceState);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Log.d(TAGLOCATE, "onCreate finish");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Log.d(TAGLOCATE, "onNavigationDrawerItemSelected starts, position: " + position);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position))
                .commit();

        Log.d(TAGLOCATE, "onNavigationDrawerItemSelected finish");

    }

    public void onSectionAttached(int number) {

        Log.d(TAGLOCATE, "onSectionAttached starts, number: " + number);

        switch (number) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }

        mPageTitles = getResources().getStringArray(R.array.menu_array);
        setTitle(mPageTitles[number]);

        Log.d(TAGLOCATE, "onSectionAttached finish");

    }

    public void restoreActionBar() {

        Log.d(TAGLOCATE, "restoreActionBar starts");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

        Log.d(TAGLOCATE, "restoreActionBar finish");

    }

    @Override
    public void setTitle(CharSequence title) {

        Log.d(TAGLOCATE, "setTitle starts, title: " + title);

        mTitle = title;
        restoreActionBar();

        Log.d(TAGLOCATE, "setTitle finish");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(TAGLOCATE, "onCreateOptionsMenu starts, menu: " + menu);

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        Log.d(TAGLOCATE, "onCreateOptionsMenu finish");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAGLOCATE, "onOptionsItemSelected starts, item: " + item);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        Log.d(TAGLOCATE, "onOptionsItemSelected finish");

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            Log.d(TAGLOCATE, "newInstance starts, sectionNumber: " + sectionNumber);

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            Log.d(TAGLOCATE, "newInstance finish");

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Log.d(TAGLOCATE, "onCreateView starts");

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Log.d(TAGLOCATE, "onCreateView finish");

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {

            Log.d(TAGLOCATE, "onAttach starts, activity: " + activity);

            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

            Log.d(TAGLOCATE, "onAttach finish");

        }
    }

}
