package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.Room.adapters.MessageItemAdapter;
import com.google.codelabs.appauth.Room.entities.MessageEntity;
import com.google.codelabs.appauth.Room.viewmodel.MessageViewModel;
import com.google.codelabs.appauth.models.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MessagesFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    Unbinder unbinder;


    @BindView(R.id.RvMessages)
    RecyclerView RvMessages;

    MessageViewModel messageViewModel;
    List<MessageEntity> messageEntityList = new ArrayList<>();
    MessageItemAdapter messageItemAdapter;

    public MessagesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        unbinder = ButterKnife.bind(this, view);
        populateMessages();
        messageItemAdapter = new MessageItemAdapter(messageEntityList);

        messageViewModel = ViewModelProviders.of(getActivity()).get(MessageViewModel.class);
        messageViewModel.getmAllMessage().observe(getActivity(), messageEntities -> {
           messageItemAdapter.setmCart(messageEntities);

        });
        RvMessages.setAdapter(messageItemAdapter);
        RvMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
        RvMessages.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return view;
    }

    private void populateMessages() {
        MessageEntity messageEntity1 = new MessageEntity( "Welcome to The World", "John Doe", "Tuesday,9:20 AM");
        MessageEntity messageEntity2 = new MessageEntity( "Awesome masterpiece", "Tim Buchalka", "Friday,10:00 PM");
        MessageEntity messageEntity3 = new MessageEntity( "Room helps me write poetic code", "Beryn Maina", "Monday,12:20 AM");

        MessageViewModel messageViewModel = new MessageViewModel(getActivity().getApplication());
        messageViewModel.addMessage(messageEntity1);
        messageViewModel.addMessage(messageEntity2);
        messageViewModel.addMessage(messageEntity3);



    }

    private void loadFrag(ConversationFragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
