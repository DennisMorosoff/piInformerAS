package ru.sfu.pi.piinformer;

import android.app.Activity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final String LOCATE = MainActivity.class.getSimpleName();
    public static final String myLogs = "myLogs";
    Intent lastRadioIntent = null;
    BroadcastReceiver br;
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

        Log.d(myLogs, "onCreate starts, savedInstanceState: " + savedInstanceState);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {

                Log.d(LOCATE, "br.onReceive starts, intent.getAction(): " + intent.getAction());

                refreshNavigationDrawerAdapter(intent);

                lastRadioIntent = intent;

            }
        };

        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter();
        intFilt.addAction(MusicService.ACTION_PLAY);
        intFilt.addAction(MusicService.ACTION_PAUSE);
        intFilt.addAction(MusicService.ACTION_STOP);

        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);

        Log.d(LOCATE, "onCreate finish");
    }

    private void refreshNavigationDrawerAdapter(Intent intent) {

        Log.d(LOCATE, "refreshNavigationDrawerAdapter starts, intent.getAction(): " + intent.getAction());

        String action = intent.getAction();

        if (action.equals(MusicService.ACTION_PLAY))
            mNavigationDrawerFragment.mDrawerListView.setAdapter(mNavigationDrawerFragment.createAdapter(NavigationDrawerFragment.mItemsIconsPause));

        if (action.equals(MusicService.ACTION_PAUSE))
            mNavigationDrawerFragment.mDrawerListView.setAdapter(mNavigationDrawerFragment.createAdapter(NavigationDrawerFragment.mItemsIconsPlay));

        if (action.equals(MusicService.ACTION_STOP))
            mNavigationDrawerFragment.mDrawerListView.setAdapter(mNavigationDrawerFragment.createAdapter(NavigationDrawerFragment.mItemsIconsPlay));

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(br);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_HEADSETHOOK:
                startService(new Intent(MusicService.ACTION_TOGGLE_PLAYBACK));
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Log.d(LOCATE, "onNavigationDrawerItemSelected starts, position: " + position);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0 /* Новости */:

                Log.d(LOCATE, "Выбран нулевой элемент бокового меню starts");

                fragmentManager.beginTransaction()
                        .replace(R.id.container, MainPageFragment.newInstance(position))
                        .commit();

                break;
            case 1 /* Расписание */:

                Log.d(LOCATE, "Выбран первый элемент бокового меню");

                fragmentManager.beginTransaction()
                        .replace(R.id.container, MainPageFragment.newInstance(position))
                        .commit();

                break;
            case 2 /* Запустить радио */:

                Log.d(myLogs, "Выбран второй элемент бокового меню");

                Log.d(myLogs, "Выбран второй элемент бокового меню, lastRadioIntent: " + lastRadioIntent);





                PhoneStateListener phoneStateListener = new PhoneStateListener() {

                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {

                        Log.d(LOCATE, "phoneStateListener.onCallStateChanged starts, state: " + state + ", incomingNumber: " + incomingNumber);
                        //public static final int CALL_STATE_IDLE = 0;
                        //public static final int CALL_STATE_RINGING = 1;
                        //public static final int CALL_STATE_OFFHOOK = 2;

                        super.onCallStateChanged(state, incomingNumber);

                        switch (state) {
                            case TelephonyManager.CALL_STATE_IDLE:

                                Log.d(LOCATE, "TelephonyManager.CALL_STATE_IDLE starts");

                                if (lastRadioIntent == null) {
                                    startService(new Intent(MusicService.ACTION_PLAY));
                                } else if (!lastRadioIntent.getAction().equals(MusicService.ACTION_PLAY)) {
                                    Log.d(myLogs, "Второе условие, lastRadioIntent.getAction(): " + lastRadioIntent.getAction());
                                    startService(new Intent(MusicService.ACTION_PLAY));
                                } else if (lastRadioIntent.getAction().equals(MusicService.ACTION_PLAY)) {
                                    Log.d(myLogs, "Третье условие, lastRadioIntent.getAction(): " + lastRadioIntent.getAction());
                                    startService(new Intent(MusicService.ACTION_PAUSE));
                                }

                                Log.d(LOCATE, "TelephonyManager.CALL_STATE_IDLE finish");
                                break;
                            case TelephonyManager.CALL_STATE_RINGING:

                                Log.d(LOCATE, "TelephonyManager.CALL_STATE_RINGING starts");

                                startService(new Intent(MusicService.ACTION_PAUSE));

                                Log.d(LOCATE, "TelephonyManager.CALL_STATE_RINGING finish");
                                break;
                            case TelephonyManager.CALL_STATE_OFFHOOK:

                                Log.d(LOCATE, "TelephonyManager.CALL_STATE_OFFHOOK starts");

                                startService(new Intent(MusicService.ACTION_PAUSE));

                                Log.d(LOCATE, "TelephonyManager.CALL_STATE_OFFHOOK finish");
                                break;
                            default:/* Выбрано несуществующее состояние телефона */
                                Toast.makeText(getApplicationContext(), R.string.radio_start_error,
                                        Toast.LENGTH_LONG).show();
                        }
                    }
                };

                Log.d(LOCATE, "Получаем экземпляр TelephonyManager");

                TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

                Log.d(LOCATE, "Экземпляр TelephonyManager получен: " + mgr + ", phoneStateListener: " + phoneStateListener);

                if (mgr != null) {
                    mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
                }

                Log.d(LOCATE, "Состояние телефона прочитано: " + mgr.getCallState());

                break;
            case 3 /* Остановить радио */:
                Log.d(LOCATE, "Выбран третий элемент бокового меню");

                startService(new Intent(MusicService.ACTION_STOP));

                Log.d(LOCATE, "Радио остановлено");
                break;
            case 4 /* Структура */:

                Log.d(LOCATE, "Выбран четвертый элемент бокового меню");

                fragmentManager.beginTransaction()
                        .replace(R.id.container, MainPageFragment.newInstance(position))
                        .commit();

                break;
            case 5 /* Сайт */:

                Log.d(LOCATE, "Выбран пятый элемент бокового меню");

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

                Log.d(LOCATE, "Выбран шестой элемент бокового меню");

                fragmentManager.beginTransaction()
                        .replace(R.id.container, PreferencesPageFragment.newInstance(" ", " "))
                        .commit();

                break;
            default /* Выбран несуществующий элемент меню */:
                Toast.makeText(this, R.string.menu_selection_error,
                        Toast.LENGTH_LONG).show();
                break;
        }

        Log.d(LOCATE, "onNavigationDrawerItemSelected finish");


    }

    public void onSectionAttached(int number) {

        Log.d(LOCATE, "onSectionAttached starts, number: " + number);

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
