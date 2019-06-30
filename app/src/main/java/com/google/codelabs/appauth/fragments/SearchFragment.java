package com.google.codelabs.appauth.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.adapters.ContactsAdapterFilterable;
import com.google.codelabs.appauth.models.Contact;
import com.google.codelabs.appauth.search.ApiClient;
import com.google.codelabs.appauth.search.ApiService;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class SearchFragment extends Fragment implements ContactsAdapterFilterable.ContactsAdapterListener {

    private OnFragmentInteractionListener mListener;
    Unbinder unbinder;

    @BindView(R.id.input_search_item)
    EditText editText;
    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;

    private static final String TAG = SearchFragment.class.getSimpleName();
    CompositeDisposable disposable = new CompositeDisposable();
    ApiService apiService;
    List<Contact> contactList = new ArrayList<>();
    ContactsAdapterFilterable adapterFilterable;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapterFilterable = new ContactsAdapterFilterable(getActivity(), contactList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapterFilterable);

        apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        disposable.add(RxTextView.textChangeEvents(editText)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContacts()));
        fetchContacts("gmail");
        return view;
    }

    private void fetchContacts(String source) {
        disposable.add(apiService.getContacts(source, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Contact>>() {

                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        contactList.clear();
                        contactList.addAll(contacts);
                        adapterFilterable.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContacts() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(TAG, "Search query: " + textViewTextChangeEvent.text());
                adapterFilterable.getFilter().filter(textViewTextChangeEvent.text());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onContactSelected(Contact contact) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroyView() {
        disposable.clear();
        unbinder.unbind();
        super.onDestroyView();

    }
}
