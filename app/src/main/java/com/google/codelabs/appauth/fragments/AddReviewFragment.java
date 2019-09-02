package com.google.codelabs.appauth.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.codelabs.appauth.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddReviewFragment extends Fragment {
    Unbinder unbinder;


    @BindView(R.id.review_cancel)
    ImageView mCancel;
    @BindView(R.id.review_ok)
    ImageView mOk;
    @BindView(R.id.rating_bar)
    RatingBar mRatingBar;

    @BindView(R.id.submit_review)
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_add_review, container, false);
       unbinder= ButterKnife.bind(this,view);



       button.setOnClickListener(v->initFields());
       return view;
    }

    private void initFields() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
