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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.adapters.FeedbackAdapter;
import com.example.foody.asyntask.InsertOrDelOrUpdate_Asynctask;
import com.example.foody.listeners.Check_task_listener;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.models.Bill_Details;
import com.example.foody.utils.Constant_Values;
import com.example.foody.utils.Methods;

import java.util.ArrayList;

import okhttp3.RequestBody;

public class FeedbackFragment extends Fragment {
    private ArrayList<Bill_Details> list_bill_details_feedback;
    private TextView txtSkip_Feedback_Frag;
    private Button btn_Send_Feeling_Frag;
    private RecyclerView recycler_Feedback;
    private ImageView img_Back_Feedback_Frag;
    private Listener_for_BackFragment listener_for_backFragment;
    private ProgressBar progressBar_feedback_frag;
    private FeedbackAdapter adapter;
    private static Methods methods;
    private Context context;//truyền để khởi tạo method vì ở trước là 1 fragment

    //kiểm tra xem gọi lần đầu, nếu k phải ẩn skip hiện arrow
    private boolean fist_time;

    public FeedbackFragment(Context context, boolean fist_time, ArrayList<Bill_Details> list_feedback,
                            Listener_for_BackFragment listener_for_backFragment) {
        this.fist_time = fist_time;
        this.list_bill_details_feedback = list_feedback;
        this.listener_for_backFragment = listener_for_backFragment;
        if(this.methods == null)
            this.methods = new Methods(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        SetUp(view);
        adapter = new FeedbackAdapter(list_bill_details_feedback, getContext());
        recycler_Feedback.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_Feedback.setAdapter(adapter);
        return view;
    }

    private void SetUp(View view){
        txtSkip_Feedback_Frag = (TextView) view.findViewById(R.id.txtSkip_Feedback_Frag);

        btn_Send_Feeling_Frag = (Button) view.findViewById(R.id.btn_Send_Feedback_Frag);
        recycler_Feedback = (RecyclerView) view.findViewById(R.id.recycler_Feedback);

        img_Back_Feedback_Frag = (ImageView) view.findViewById(R.id.img_Back_Feedback_Frag);

        progressBar_feedback_frag = (ProgressBar) view.findViewById(R.id.progressBar_feedback_frag);

        txtSkip_Feedback_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_backFragment.orderBill_Or_BackFragment();
            }
        });

        img_Back_Feedback_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_backFragment.orderBill_Or_BackFragment();
            }
        });

        btn_Send_Feeling_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < list_bill_details_feedback.size(); ++i) {
                    if(list_bill_details_feedback.get(i).getRate() > 0 ||
                        i == list_bill_details_feedback.size() - 1){
                        update_RateAndReview(list_bill_details_feedback.get(i), i);
                    }
                }
            }
        });

        if(fist_time){
            img_Back_Feedback_Frag.setVisibility(View.GONE);
            txtSkip_Feedback_Frag.setVisibility(View.VISIBLE);
        } else {
            img_Back_Feedback_Frag.setVisibility(View.VISIBLE);
            txtSkip_Feedback_Frag.setVisibility(View.GONE);
            if(check_Full_Rate()){
                btn_Send_Feeling_Frag.setVisibility(View.GONE);
            } else {
                btn_Send_Feeling_Frag.setVisibility(View.VISIBLE);
            }
        }
    }

    //check đã rate và rivew đủ
    private boolean check_Full_Rate(){
        for(Bill_Details i : list_bill_details_feedback){
            if(i.getRate() == 0f && i.getReviews().equals(""))
                return false;
        }
        return true;
    }

    private void update_RateAndReview(Bill_Details bill_details, int position){
        Check_task_listener listener_update_review = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                progressBar_feedback_frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                if(isSucces){
                    update_Rate_Food(bill_details.getiD_Food());
                    if(position == list_bill_details_feedback.size() - 1){
                        progressBar_feedback_frag.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Thanks for you feedback!", Toast.LENGTH_SHORT).show();
                        listener_for_backFragment.orderBill_Or_BackFragment();
                    }
                } else
                    Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Bill", bill_details.getiD_Bill());
        bundle.putInt("ID_Food", bill_details.getiD_Food());
        bundle.putInt("Count", bill_details.getCount());
        bundle.putFloat("Price_Total", bill_details.getPrice_Total());
        bundle.putFloat("Rate", bill_details.getRate());
        bundle.putString("Reviews", bill_details.getReviews());
        RequestBody requestBody = methods.getRequestBody("method_update_review",bundle);
        InsertOrDelOrUpdate_Asynctask asynctask = new InsertOrDelOrUpdate_Asynctask(listener_update_review,
                requestBody, Constant_Values.URL_BILL_DETAIL_API);
        asynctask.execute();
    }

    private void update_Rate_Food(int ID_food){
        Check_task_listener listener_update_food = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Error Update Rate Food", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                if(isSucces){
                } else
                    Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Food", ID_food);
        RequestBody requestBody = methods.getRequestBody("method_get_update_food_data", bundle);
        InsertOrDelOrUpdate_Asynctask asynctask = new InsertOrDelOrUpdate_Asynctask(listener_update_food, requestBody, Constant_Values.URL_FOOD_API);
        asynctask.execute();
    }
}
