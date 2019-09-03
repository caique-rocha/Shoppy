package com.google.codelabs.appauth.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.codelabs.appauth.R;
import com.google.codelabs.appauth.adapters.ShopReviewAdapter;
import com.google.codelabs.appauth.models.Review;
import com.google.codelabs.appauth.product.ProductClient;
import com.google.codelabs.appauth.product.ProductInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopReviewsFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.fab_add_reviews)
    FloatingActionButton mAddReview;
    @BindView(R.id.recycler_view_shop_reviews)
    RecyclerView mReviewsRecycler;

    View view;
    ShopReviewAdapter mAdapter;
    Context context;
    Review mReview;
    List<Review> mReviewList=new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_shop_reviews, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadAllReviews();

        mAddReview.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("Give Shoppy a five star rating... ");
            final RatingBar ratingBar = new RatingBar(getActivity());
            ratingBar.setNumStars(5);
            ratingBar.setStepSize(1);
            ratingBar.setRating(2);
            alertDialog.setView(ratingBar);

            alertDialog.setPositiveButton(android.R.string.ok, ((dialog, which) -> {
                Review review = new Review();
                int rate = (int) ratingBar.getRating();
                String rating = String.valueOf(rate);
                String message = "";
                switch (rate) {
                    case 1:
                    case 2:
                        message = "Poor App";
                        break;
                    case 3:
                    case 4:
                        message = "Good App";
                        break;
                    case 5:
                        message = "Best App, Excellent";
                        break;
                    default:
                        message = "Awesome App";
                        break;

                }
                review.setReviewMessage(message);
                review.setReviewStars(rate);

                Toast.makeText(getActivity()
                        , rating,
                        Toast.LENGTH_SHORT).show();
                sendToServer(review);

            }));
            alertDialog.setNegativeButton("NOT NOW", ((dialog, which) -> {
                dialog.cancel();
            }));
            alertDialog.show();

        });
        mReviewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter=new ShopReviewAdapter(mReviewList,mReview);
        return view;
    }

    private void loadAllReviews() {
        ProductInterface productInterface=ProductClient.getProductClient().create(ProductInterface.class);
        Call<List<Review>> call=productInterface.getAllShopReviews();
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                loadDataList(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });
    }

    private void loadDataList(List<Review> userList) {
        //create adapter
        mAdapter=new ShopReviewAdapter(userList,mReview);
        mReviewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mReviewsRecycler.setAdapter(mAdapter);


    }

    private void sendToServer(Review review) {
        ProductInterface productInterface= ProductClient.getProductClient().create(ProductInterface.class);
        Call<ResponseBody> call=productInterface.addShopReview(review);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(view,"Thanks for your Response",Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               Snackbar.make(view,"Not Sent",Snackbar.LENGTH_SHORT);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
