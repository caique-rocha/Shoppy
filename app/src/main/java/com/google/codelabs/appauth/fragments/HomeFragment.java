package com.google.codelabs.appauth.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        topItemModelArrayList.add(new Product(("1"),"Shirt", "77", "https://image.kilimall.com/kenya/shop/store/goods/2375/2018/04/2375_05782736773153857_720.jpg","Blue","Clothes","12"));
        topItemModelArrayList.add(new Product(("2"),"Phone", "78", "https://image.kilimall.com/kenya/shop/store/goods/4935/2019/05/4935_06108150282193526_720.jpg" ,"Black","Clothes","12"));
        topItemModelArrayList.add(new Product(("3"),"Clothes", "79", "https://image.kilimall.com/kenya/shop/store/goods/2559/2018/07/2559_05847214974665595_720.jpg","Green","Shoes","13"));
        topItemModelArrayList.add(new Product(("4"),"Blender", "80", "https://image.kilimall.com/kenya/shop/store/goods/5169/2019/05/5169_06106272450264719_720.jpg","White","Electronics","14"));
        topItemModelArrayList.add(new Product(("5"),"Heels", "79", "https://image.kilimall.com/kenya/shop/store/goods/2415/2018/04/2415_05773850115860735_720.jpg","Yellow","Furniture","15"));
        topItemModelArrayList.add(new Product(("6"),"Rubbers", "77", "https://image.kilimall.com/kenya/shop/store/goods/2415/2018/04/2415_05773049282117352_720.jpg","Blue","Clothes","16"));


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

        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right
        );
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();


    };

    private ItemClickListener mItemClickListener=(int position,String id, String name, String price, String image)->{
        Intent intent=new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("id",id);
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
                , R.drawable.ic_face_black_24dp, "Apparel"));
        items.add(new MainCategory(getRandomMaterialColor("500")
                , R.drawable.ic_brush_black_24dp, "Beauty"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_directions_walk_black_24dp, "Shoes"));
        items.add(new MainCategory(getRandomMaterialColor("500")
                , R.drawable.ic_desktop_mac_black_24dp, "Computing"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_event_seat_black_24dp, "Furniture"));
        items.add(new MainCategory(getRandomMaterialColor("500")
                , R.drawable.ic_library_books_black_24dp, "Books"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_restaurant_menu_black_24dp, "Food"));
        items.add(new MainCategory(getRandomMaterialColor("500")
                , R.drawable.ic_local_grocery_store_black_24dp, "Grocery"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_videogame_asset_black_24dp, "Gaming"));
        items.add(new MainCategory(getRandomMaterialColor("500")
                , R.drawable.ic_local_hospital_black_24dp, "Health"));
        items.add(new MainCategory(getRandomMaterialColor("400")
                , R.drawable.ic_golf_course_black_24dp, "Sporting"));
        items.add(new MainCategory(getRandomMaterialColor("500")
                , R.drawable.ic_smartphone_black_24dp, "Phones"));


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
