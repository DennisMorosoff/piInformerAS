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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {

    private static final String LOCATE = MainActivity.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "position";

    // TODO: Rename and change types of parameters
    private int mParam;

    private OnFragmentInteractionListener mListener;

    public MainPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageFragment newInstance(int param1) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOCATE, "MainPageFragment.onCreateView starts");

        // получаем указатель на корневой компонент слоя fragment_pages. В
        // данном случае это ImageView
        View rootView = inflater.inflate(R.layout.fragment_main_page, container,
                false);

        // получаем указатель на компонент - ImageView
        TextView mPageText = (TextView) rootView.findViewById(R.id.stubtext);

        /* Массив заголовков страниц (меню) */
        String[] mPageDescriptions = getResources().getStringArray(R.array.menu_array_description);

        // загружаем рисунок в компонент в отдельном потоке
        loadText(mPageText, mPageDescriptions[mParam]);

        Log.d(LOCATE, "MainPageFragment.onCreateView finish");

        return rootView;
    }

    private void loadText(final TextView pageText, final String text) {
        // загружаем рисунок в компонент в отдельном потоке
        pageText.post(new Runnable() {
            @Override
            public void run() {
                pageText.setText(text);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //           mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
