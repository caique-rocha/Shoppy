package com.google.codelabs.appauth.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Utils.NotificationUtils;
import com.google.codelabs.appauth.saf.Config;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.google.codelabs.appauth.saf.Config.PUSH_NOTIFICATION;
import static com.google.codelabs.appauth.saf.Config.REGISTRATION_COMPLETE;
import static com.google.codelabs.appauth.saf.Config.TOPIC_GLOBAL;

public class NotificationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String TAG = "NotificationFragment";
    private String firebaseRegId;
    private TextView mTextMessage;
    String message;
    BroadcastReceiver mBroadCastReceiver;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notification, container, false);
        mTextMessage=view.findViewById(R.id.textViewDescription);

        mBroadCastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction()==REGISTRATION_COMPLETE) {
                    FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GLOBAL);
                    displayFirebaseRegId();

                }
                else if (intent.getAction().equals(PUSH_NOTIFICATION)){
                    message=intent.getStringExtra("message");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    updateUI();
                    mTextMessage.setText(message);
                }
            }
        };
        displayFirebaseRegId();
        return view;
    }

    private void updateUI() {
        TextView textView=getActivity().findViewById(R.id.textView15);
        textView.setText(String.valueOf(1));

    }

    private void displayFirebaseRegId() {
        SharedPreferences prefs=getActivity().getSharedPreferences(Config.SHARED_PREF,0);
        firebaseRegId=prefs.getString("regId",null);
        Log.d(TAG,firebaseRegId);
    }

    @Override
    public void onResume() {
        super.onResume();

        //register fcm registration complete receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadCastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        //register new push messages receiver each time
        // a message arrives, the activity will be notified
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadCastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        //clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getActivity());
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBroadCastReceiver);

        super.onPause();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
