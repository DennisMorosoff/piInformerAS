package ru.sfu.pi.piinformer;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String LOCATE = MainActivity.class.getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     * Фрагмент, управляющий поведением, взаимодействием и отображением бокового меню.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     * Используется для изменения заголовка экрана в методе {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOCATE, "onCreate starts, savedInstanceState: " + savedInstanceState);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Log.d(LOCATE, "onCreate finish");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Log.d(LOCATE, "onNavigationDrawerItemSelected starts, position: " + position);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position))
                .commit();

        Log.d(LOCATE, "onNavigationDrawerItemSelected finish");

    }

    public void onSectionAttached(int number) {

        Log.d(LOCATE, "onSectionAttached starts, number: " + number);

        switch (number) {
            case 0 /* Новости */:

                break;
            case 1 /* Расписание */:

                break;
            case 2 /* Радио */:

                break;
            case 3 /* Абитуриенту */:

                break;
            case 4 /* Структура */:

                break;
            case 5 /* Сайт */:
                // создаём намерение для вызова поиска в интернете информации об институте
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                // пока что выводит только результат запроса
                // "Политехнический институт СФУ"
                intent.putExtra(SearchManager.QUERY, "Политехнический институт СФУ");
                // запускаем браузер, выводим результат поиска
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available,
                            Toast.LENGTH_LONG).show();
                }
                break;
            case 6 /* Настройки */:

                break;
            default /* Выбран несуществующий элемент меню */:
                Toast.makeText(this, R.string.menu_selection_error,
                        Toast.LENGTH_LONG).show();
                break;
        }

        /* Массив заголовков страниц (меню) */
        String[] mPageTitles = getResources().getStringArray(R.array.menu_array);
        setTitle(mPageTitles[number]);

        Log.d(LOCATE, "onSectionAttached finish, mPageTitles[number]: " + mPageTitles[number]);

    }

    public void restoreActionBar() {

        Log.d(LOCATE, "restoreActionBar starts");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

        Log.d(LOCATE, "restoreActionBar finish");

    }

    @Override
    public void setTitle(CharSequence title) {

        Log.d(LOCATE, "setTitle starts, title: " + title);

        mTitle = title;
        restoreActionBar();

        Log.d(LOCATE, "setTitle finish");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(LOCATE, "onCreateOptionsMenu starts, menu: " + menu);

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        Log.d(LOCATE, "onCreateOptionsMenu finish");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(LOCATE, "onOptionsItemSelected starts, item: " + item);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        Log.d(LOCATE, "onOptionsItemSelected finish");

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

            Log.d(LOCATE, "newInstance starts, sectionNumber: " + sectionNumber);

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            Log.d(LOCATE, "newInstance finish");

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Log.d(LOCATE, "onCreateView starts");

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Log.d(LOCATE, "onCreateView finish");

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {

            Log.d(LOCATE, "onAttach starts, activity: " + activity);

            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

            Log.d(LOCATE, "onAttach finish");

        }
    }

}
