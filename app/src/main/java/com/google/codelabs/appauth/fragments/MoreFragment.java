package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.codelabs.appauth.activities.LoginActivity;
import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.adapters.ProfileBasicAdapter;
import com.google.codelabs.appauth.models.ProfileBasic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoreFragment extends Fragment {
    Unbinder unbinder;

    @BindView(R.id.recyclerViewMoreBasic)
    RecyclerView recyclerViewMoreBasic;

    @BindView(R.id.recyclerViewMoreInfo)
    RecyclerView getRecyclerViewMoreInfo;

    List<ProfileBasic> profileMoreBasics=new ArrayList<>();
    ProfileBasicAdapter basicMoreAdapter;

    List<ProfileBasic> profileMoreInfoList=new ArrayList<>();
    ProfileBasicAdapter getInfoAdapter;

    @BindView(R.id.textViewLogin)
    TextView mtextViewLogin;

    private OnFragmentInteractionListener mListener;

    public MoreFragment() {
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
        View view= inflater.inflate(R.layout.fragment_more, container, false);
        unbinder= ButterKnife.bind(this,view);
        basicMoreAdapter=new ProfileBasicAdapter(profileMoreBasics);
        recyclerViewMoreBasic.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMoreBasic.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerViewMoreBasic.setAdapter(basicMoreAdapter);

        getInfoAdapter=new ProfileBasicAdapter(profileMoreInfoList);
        getRecyclerViewMoreInfo.setAdapter(getInfoAdapter);
        getRecyclerViewMoreInfo.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        getRecyclerViewMoreInfo.setLayoutManager(new LinearLayoutManager(getContext()));

        initBasic();
        intInfo();
        mtextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
    }

    private void intInfo() {
        ProfileBasic one=new ProfileBasic(R.drawable.ic_notifications_none_black_24dp,"Notifications");
        ProfileBasic two=new ProfileBasic(R.drawable.ic_verified_user_black_24dp,"Privacy Policy");
        ProfileBasic three=new ProfileBasic(R.drawable.ic_question_answer_black_24dp,"Frequency  Asked Questions");
        ProfileBasic four=new ProfileBasic(R.drawable.ic_event_note_black_24dp,"Legal Information ");

        profileMoreInfoList.add(one);
        profileMoreInfoList.add(two);
        profileMoreInfoList.add(three);
        profileMoreInfoList.add(four);

        getInfoAdapter.notifyDataSetChanged();

    }

    private void initBasic() {
        ProfileBasic one=new ProfileBasic(R.drawable.ic_flag_black_24dp,"Shipping Address");
        ProfileBasic two=new ProfileBasic(R.drawable.ic_payment_black_24dp,"Payment Method");
        ProfileBasic three=new ProfileBasic(R.drawable.ic_account_balance_wallet_black_24dp,"Currency");
        ProfileBasic four=new ProfileBasic(R.drawable.ic_translate_black_24dp,"Language");

        profileMoreBasics.add(one);
        profileMoreBasics.add(two);
        profileMoreBasics.add(three);
        profileMoreBasics.add(four);

        basicMoreAdapter.notifyDataSetChanged();

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
