package com.google.codelabs.appauth.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.activities.ProductDetailsActivity;
import com.google.codelabs.appauth.models.Product;
import com.google.codelabs.appauth.product.ProductClient;
import com.google.codelabs.appauth.product.ProductInterface;

import com.google.codelabs.appauth.adapters.ProductsAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class SearchFragment extends Fragment implements ProductsAdapter.ContactsAdapterListener {

    private OnFragmentInteractionListener mListener;
    Unbinder unbinder;

    @BindView(R.id.input_search_item)
    EditText editText;
    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;

    private static final String TAG = SearchFragment.class.getSimpleName();
    CompositeDisposable disposable=new CompositeDisposable();
    private PublishSubject<String> publishSubject=PublishSubject.create();
    ProductInterface apiService;
    ProductsAdapter mAdapter;
    List<Product> contactList=new ArrayList<>();

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
        mAdapter=new ProductsAdapter(contactList,getActivity(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        apiService= ProductClient.getProductClient().create(ProductInterface.class);
        DisposableObserver<List<Product>> observer=getSearchObserver();

        disposable.add(publishSubject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle((Function<String, Single<List<Product>>>) s -> apiService.getContacts(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))
                .subscribeWith(observer));

        // skipInitialValue() - skip for the first time when EditText empty
        disposable.add(RxTextView.textChangeEvents(editText)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));

        disposable.add(observer);

        // passing empty string fetches all the contacts
        publishSubject.onNext("");
        return view;
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(TAG, "Search query: " + textViewTextChangeEvent.text());
                publishSubject.onNext(textViewTextChangeEvent.text().toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<List<Product>> getSearchObserver() {
        return new DisposableObserver<List<Product>>() {
            @Override
            public void onNext(List<Product> products) {
                contactList.clear();
                contactList.addAll(products);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }


    @Override
    public void onContactSelected(Product product) {
         String id, name,category,image, price;
         id=product.getmProductId();
         name=product.getProductName();
         category=product.getProductCategory();
         image=product.getProductImage();
         price=product.getProductPrice();

         Intent intent=new Intent(getActivity(), ProductDetailsActivity.class);
         intent.putExtra("id",id);
         intent.putExtra("name",name);
         intent.putExtra("category",category);
         intent.putExtra("image",image);
         intent.putExtra("price",price);

         startActivity(intent);

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
