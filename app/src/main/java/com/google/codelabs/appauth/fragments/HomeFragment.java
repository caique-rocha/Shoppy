package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.activities.ProductDetailsActivity;
import com.google.codelabs.appauth.adapters.TopItemAdapter;
import com.google.codelabs.appauth.adapters.TopRatedAdapter;
import com.google.codelabs.appauth.adapters.MainCategoryAdapter;
import com.google.codelabs.appauth.interfaces.FragmentCommunication;
import com.google.codelabs.appauth.interfaces.ItemClickListener;
import com.google.codelabs.appauth.models.Product;
import com.google.codelabs.appauth.models.TopItemModel;
import com.google.codelabs.appauth.models.Latest;
import com.google.codelabs.appauth.models.MainCategory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {
    Unbinder unbinder;

    Fragment fragment;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.recycler_top_rated)
    RecyclerView recyclerViewTopRated;

    @BindView(R.id.rvImages)
    RecyclerView recyclerViewBottom;

    ArrayList<MainCategory> items = new ArrayList<>();
    MainCategoryAdapter adapter;

    ArrayList<Latest> latestArrayList = new ArrayList<>();
    ArrayList<Product> topItemModelArrayList = new ArrayList<>();

    TopRatedAdapter topRatedAdapter;
    TopItemAdapter topItemAdapter;


    private OnFragmentInteractionListener mListener;
    int orientation;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientation = getResources().getConfiguration().orientation;


    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        bindDataToAdapter();
        initLatest();
        initBottom();
        getSimpleArrayList();

        adapter = new MainCategoryAdapter(items, getActivity(), communication);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        topRatedAdapter = new TopRatedAdapter(latestArrayList);
        recyclerViewTopRated.setAdapter(topRatedAdapter);
        recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTopRated.setNestedScrollingEnabled(false);

        topItemAdapter = new TopItemAdapter(topItemModelArrayList,mItemClickListener);
        recyclerViewBottom.setAdapter(topItemAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerViewBottom.setLayoutManager(gridLayoutManager);

        return view;
    }

    private void initBottom() {
        topItemModelArrayList.add(new Product("Shirt", "77", "https://image.kilimall.com/kenya/shop/store/goods/2375/2018/04/2375_05782736773153857_720.jpg"));
        topItemModelArrayList.add(new Product("Phone", "78", "https://image.kilimall.com/kenya/shop/store/goods/4935/2019/05/4935_06108150282193526_720.jpg"));
        topItemModelArrayList.add(new Product("Clothes", "79", "https://image.kilimall.com/kenya/shop/store/goods/2559/2018/07/2559_05847214974665595_720.jpg"));
        topItemModelArrayList.add(new Product("Blender", "80", "https://image.kilimall.com/kenya/shop/store/goods/5169/2019/05/5169_06106272450264719_720.jpg"));
        topItemModelArrayList.add(new Product("Heels", "79", "https://image.kilimall.com/kenya/shop/store/goods/2415/2018/04/2415_05773850115860735_720.jpg"));
        topItemModelArrayList.add(new Product("Rubbers", "77", "https://image.kilimall.com/kenya/shop/store/goods/2415/2018/04/2415_05773049282117352_720.jpg"));
        topItemModelArrayList.add(new Product("Shirt", "77", "https://image.kilimall.com/kenya/shop/store/goods/2375/2018/04/2375_05782736773153857_720.jpg"));
        topItemModelArrayList.add(new Product("Phone", "78", "https://image.kilimall.com/kenya/shop/store/goods/4935/2019/05/4935_06108150282193526_720.jpg"));
        topItemModelArrayList.add(new Product("Clothes", "79", "https://image.kilimall.com/kenya/shop/store/goods/2559/2018/07/2559_05847214974665595_720.jpg"));
        topItemModelArrayList.add(new Product("Blender", "80", "https://image.kilimall.com/kenya/shop/store/goods/5169/2019/05/5169_06106272450264719_720.jpg"));
        topItemModelArrayList.add(new Product("Heels", "79", "https://image.kilimall.com/kenya/shop/store/goods/2415/2018/04/2415_05773850115860735_720.jpg"));
        topItemModelArrayList.add(new Product("Rubbers", "77", "https://image.kilimall.com/kenya/shop/store/goods/2415/2018/04/2415_05773049282117352_720.jpg"));


    }

    private void initLatest() {
//        latestArrayList.add(new Latest(BitmapFactory.decodeResource(getResources(), R.drawable.catone)));
        latestArrayList.add(new Latest("https://www.freegreatpicture.com/files/75/24496-hd-women-shopping.jpg"));
        latestArrayList.add(new Latest("https://www.freegreatpicture.com/files/75/24538-hd-women-shopping.jpg"));
        latestArrayList.add(new Latest("https://www.freegreatpicture.com/files/75/24558-hd-women-shopping.jpg"));
        latestArrayList.add(new Latest("https://s2.best-wallpaper.net/wallpaper/1920x1200/1204/The-mood-of-the-shopping-girl-joy_1920x1200.jpg"));
    }

    private void bindDataToAdapter() {
        adapter = new MainCategoryAdapter(items, getActivity(), communication);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
    }

    FragmentCommunication communication = (int position, String category) -> {
        AllCategoriesFragment fragment = new AllCategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category",  category);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right
        );
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();


    };

    ItemClickListener mItemClickListener=(int position, String name, String price, String image)->{
        Intent intent=new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("price",price);
        intent.putExtra("image",image);
        startActivity(intent);

    };


    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier
                ("mdcolor_" + typeColor, "array", getActivity().getPackageName());
        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();

        }
        return returnColor;
    }


    @OnClick()
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.notificationButton):
                loadFragment(new NotificationFragment());
                break;
            case (R.id.messageButton):
                loadFragment(new MessagesFragment());

        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private ArrayList<MainCategory> getSimpleArrayList() {
        items = new ArrayList<>();
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Apparel"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Beauty"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Shoes"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Electronics"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Furniture"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Home"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Aparell"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Aparell"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Aparell"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Aparell"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Aparell"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_home_white_24dp, "Aparell"));


        return items;

    }


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
