package com.example.foody.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.adapters.ReviewAdapter;
import com.example.foody.asyntask.Load_Reviews_Asynctask;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.Favorite_for_FoodAdapter;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Listener_for_IncAndRedu;
import com.example.foody.listeners.Load_Reviews_Listener;
import com.example.foody.models.Foods;
import com.example.foody.models.Reviews;
import com.example.foody.utils.Methods;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.RequestBody;

public class ReviewsFragment extends Fragment {
    private ArrayList<Reviews> list_review;

    private ImageView img_Back_Review_Frag, img_Descrip_Review_Frag;
    private TextView txt_Tittle_Reviews_Fragment, txtCount_ReviewFrag;
    private RecyclerView recycler_Reviews;
    private ProgressBar progressBar_review_Frag;

    private Button btnRedu_ReviewFrag, btnInc_ReviewFrag;

    private Listener_for_BackFragment listener_for_backFragment;
    private Listener_for_IncAndRedu listener_for_incAndRedu;
    private Favorite_for_FoodAdapter list_favorite;

    private Context context;

    private ReviewAdapter adapter;

    private Foods first_food_item;

    public ReviewsFragment(Context context, Foods first_item,
                           Listener_for_BackFragment listener_for_backFragment,
                           Listener_for_IncAndRedu listener_for_incAndRedu,
                           Favorite_for_FoodAdapter list_favorite) {
        this.context = context;
        this.list_review = new ArrayList<>();
        this.first_food_item = first_item;
        this.listener_for_backFragment = listener_for_backFragment;
        this.listener_for_incAndRedu = listener_for_incAndRedu;
        this.list_favorite = list_favorite;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_reviews, container, false);
        SetUp(view);
        load_Review_data(first_food_item);
        adapter = new ReviewAdapter(first_food_item, getContext(), list_review, list_favorite);
        recycler_Reviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_Reviews.setAdapter(adapter);
        return view;
    }

    private void SetUp(View view){
        progressBar_review_Frag = (ProgressBar) view.findViewById(R.id.progressBar_review_Frag);

        img_Back_Review_Frag = (ImageView) view.findViewById(R.id.img_Back_Review_Frag);
        img_Descrip_Review_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Review_Frag);
        txt_Tittle_Reviews_Fragment = (TextView) view.findViewById(R.id.txt_Tittle_Reviews_Fragment);
        txt_Tittle_Reviews_Fragment.setText(first_food_item.getName_Food());

        recycler_Reviews = (RecyclerView) view.findViewById(R.id.recycler_Reviews);

        img_Back_Review_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_backFragment.orderBill_Or_BackFragment();
            }
        });

        img_Descrip_Review_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDecripFragment decripFragment = new DialogDecripFragment(first_food_item);
                decripFragment.show(getFragmentManager(), "dialog");
            }
        });

        txtCount_ReviewFrag = (TextView) view.findViewById(R.id.txtCount_ReviewFrag);
        int count = CartFragment.search_BillDetail_ByIDFood(first_food_item.getiD_Food()).getCount();
        txtCount_ReviewFrag.setText("" + ((count > 0) ? count : 0));

        btnRedu_ReviewFrag = (Button) view.findViewById(R.id.btnRedu_ReviewFrag);
        btnInc_ReviewFrag = (Button) view.findViewById(R.id.btnInc_ReviewFrag);

        btnRedu_ReviewFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_incAndRedu.onRedu_Click(first_food_item.getiD_Food());
                if(CartFragment.search_BillDetail_ByIDFood(first_food_item.getiD_Food()).getCount() > 0){
                    txtCount_ReviewFrag.setText("" + CartFragment.search_BillDetail_ByIDFood(
                            first_food_item.getiD_Food()).getCount());
                } else {
                    txtCount_ReviewFrag.setText("" + 0);
                }
            }
        });

        btnInc_ReviewFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_incAndRedu.onInc_Click(first_food_item.getiD_Food());
                txtCount_ReviewFrag.setText("" + CartFragment.search_BillDetail_ByIDFood(
                        first_food_item.getiD_Food()).getCount());
            }
        });
    }

    private void load_Review_data(Foods foods){
        Methods methods = new Methods(context);
        Load_Reviews_Listener listener_load_review = new Load_Reviews_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(context, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                progressBar_review_Frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSussec, ArrayList<Reviews> list_result) {
                progressBar_review_Frag.setVisibility(View.GONE);
                if(isSussec){
                    Collections.reverse(list_result);//đảo ngược chuỗi
                    list_review.addAll(list_result);
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(context, "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Food", foods.getiD_Food());
        RequestBody requestBody = methods.getRequestBody("method_get_reviews_data", bundle);
        Load_Reviews_Asynctask asynctask = new Load_Reviews_Asynctask(listener_load_review, requestBody);
        asynctask.execute();
    }
}
