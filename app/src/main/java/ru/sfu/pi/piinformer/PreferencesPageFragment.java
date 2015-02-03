package ru.sfu.pi.piinformer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferencesPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreferencesPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferencesPageFragment extends Fragment {

    private static final String LOCATE = MainActivity.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PreferencesPageFragment() {

        Log.d(LOCATE, "PreferencesPageFragment starts");

        // для подклассов фрагмента нужен обязательный конструктор, хотя бы
        // даже пустой

        Log.d(LOCATE, "PreferencesPageFragment finish");

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreferencesPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreferencesPageFragment newInstance(String param1, String param2) {

        Log.d(LOCATE, "PreferencesPageFragment.newInstance starts");

        PreferencesPageFragment fragment = new PreferencesPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        Log.d(LOCATE, "PreferencesPageFragment.newInstance finish");

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(LOCATE, "PreferencesPageFragment.onCreate starts, savedInstanceState: " + savedInstanceState);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.d(LOCATE, "PreferencesPageFragment.onCreate finish");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOCATE, "PreferencesPageFragment.onCreateView starts");

        // получаем указатель на корневой компонент слоя fragment_pages. В
        // данном случае это ImageView
        View rootView = inflater.inflate(R.layout.fragment_preferences_page, container,
                false);

        // получаем указатель на компонент - ImageView
        ImageView mPageImage = (ImageView) rootView.findViewById(R.id.image);

        // загружаем рисунок в компонент в отдельном потоке
        loadImage(mPageImage, R.drawable.prefs);

        Log.d(LOCATE, "PreferencesPageFragment.onCreateView finish");

        return rootView;
    }

    private void loadImage(final ImageView mPageImage, final int imageId) {
        // загружаем рисунок в компонент в отдельном потоке
        mPageImage.post(new Runnable() {
            @Override
            public void run() {
                mPageImage.setImageResource(imageId);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

        Log.d(LOCATE, "PreferencesPageFragment.onButtonPressed starts");

        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

        Log.d(LOCATE, "PreferencesPageFragment.onButtonPressed finish");

    }

    @Override
    public void onAttach(Activity activity) {

        Log.d(LOCATE, "PreferencesPageFragment.onAttach starts, activity: " + activity);

        super.onAttach(activity);

        try {

            // TODO: Получить указатель на активность, при попытке выполнить это вылетает
            // этот указатель нужен для связи между фрагментами через главную активность
            // mListener = (OnFragmentInteractionListener) activity;

            Log.d("myLogs", "Получен указатель на mListener: " + mListener);

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        Log.d(LOCATE, "PreferencesPageFragment.onButtonPressed finish");

    }

    @Override
    public void onDetach() {

        Log.d(LOCATE, "PreferencesPageFragment.onDetach starts");

        super.onDetach();
        mListener = null;

        Log.d(LOCATE, "PreferencesPageFragment.onDetach finish");

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
