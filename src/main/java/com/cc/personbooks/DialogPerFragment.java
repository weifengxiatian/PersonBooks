package com.cc.personbooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DialogPerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DialogPerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogPerFragment extends DialogFragment implements AlertDialog.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText perwater;
    private EditText perelec;

    // private OnFragmentInteractionListener mListener;
    private extreAction mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param content Parameter 2.
     * @return A new instance of fragment DialogPerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogPerFragment newInstance(String title, String content) {
        DialogPerFragment fragment = new DialogPerFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    public DialogPerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TITLE);
            mParam2 = getArguments().getString(CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_per, null);
        perelec = (EditText) inflate.findViewById(R.id.perElec);
        perwater = (EditText) inflate.findViewById(R.id.perWater);
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setView(inflate)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(getArguments().getString(TITLE))
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this);
//        return super.onCreateDialog(savedInstanceState);
        return  b.create();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(double tempelec, double tempWater) {
        if (mListener != null) {
            mListener.todoSomethings(tempelec, tempWater);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (extreAction) activity;
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case AlertDialog.BUTTON_POSITIVE :
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor edit = sp.edit();
                //Double.parseDouble(perelec.getText().toString());
                long elec = (long) (Double.parseDouble(perelec.getText().toString())* 100);
                long water = (long) (Double.parseDouble(perwater.getText().toString())*100);
                edit.putLong("perElec", elec );
                edit.putLong("perWater", water );
                Toast.makeText(getActivity(), "elec : " + Double.parseDouble(perelec.getText().toString()) + "/ water : " + Double.parseDouble(perwater.getText().toString()), Toast.LENGTH_SHORT).show();
                edit.commit();
                onButtonPressed(Double.parseDouble(perelec.getText().toString()), Double.parseDouble(perwater.getText().toString()));
                break;
            case AlertDialog.BUTTON_NEGATIVE :
                break;
            case AlertDialog.BUTTON_NEUTRAL :
                break;
        }
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

    public interface extreAction {
        // TODO: Update argument type and name
        public void todoSomethings(double tempelec, double tempWater);
    }
}
