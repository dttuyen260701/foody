package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Listener_for_IncAndRedu;
import com.example.foody.models.Foods;
import com.example.foody.models.Reviews;

import java.util.ArrayList;

public class ReviewsFragment extends Fragment {
    private ArrayList<Reviews> list_review;

    private ImageView img_Back_Review_Frag, img_Descrip_Review_Frag;
    private TextView txt_Tittle_Reviews_Fragment, txtCount_ReviewFrag;
    private RecyclerView recycler_Reviews;

    private Button btnRedu_ReviewFrag, btnInc_ReviewFrag;

    private Listener_for_BackFragment listener_for_backFragment;
    private Listener_for_IncAndRedu listener_for_incAndRedu;

    private Foods first_item;

    public ReviewsFragment(Foods first_item, ArrayList<Reviews> list_review,
                           Listener_for_BackFragment listener_for_backFragment,
                           Listener_for_IncAndRedu listener_for_incAndRedu) {
        this.first_item = first_item;
        this.list_review = list_review;
        this.listener_for_backFragment = listener_for_backFragment;
        this.listener_for_incAndRedu = listener_for_incAndRedu;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_reviews, container, false);
        SetUp(view);
        return view;
    }

    private void SetUp(View view){
        img_Back_Review_Frag = (ImageView) view.findViewById(R.id.img_Back_Review_Frag);
        img_Descrip_Review_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Review_Frag);
        txt_Tittle_Reviews_Fragment = (TextView) view.findViewById(R.id.txt_Tittle_Reviews_Fragment);
        txt_Tittle_Reviews_Fragment.setText(first_item.getName_Food());

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
                DialogDecripFragment decripFragment = new DialogDecripFragment(first_item);
                decripFragment.show(getFragmentManager(), "dialog");
            }
        });

        txtCount_ReviewFrag = (TextView) view.findViewById(R.id.txtCount_ReviewFrag);
        int count = CartFragment.search_BillDetail_ByIDFood(first_item.getiD_Food()).getCount();
        txtCount_ReviewFrag.setText("" + ((count > 0) ? count : 0));

        btnRedu_ReviewFrag = (Button) view.findViewById(R.id.btnRedu_ReviewFrag);
        btnInc_ReviewFrag = (Button) view.findViewById(R.id.btnInc_ReviewFrag);

        btnRedu_ReviewFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_incAndRedu.onRedu_Click(first_item.getiD_Food());
                if(CartFragment.search_BillDetail_ByIDFood(first_item.getiD_Food()).getCount() > 0){
                    txtCount_ReviewFrag.setText("" + CartFragment.search_BillDetail_ByIDFood(
                            first_item.getiD_Food()).getCount());
                } else {
                    txtCount_ReviewFrag.setText("" + 0);
                }
            }
        });

        btnInc_ReviewFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_incAndRedu.onInc_Click(first_item.getiD_Food());
                txtCount_ReviewFrag.setText("" + CartFragment.search_BillDetail_ByIDFood(
                        first_item.getiD_Food()).getCount());
            }
        });
    }
}
