package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
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
    private static Methods methods;

    //kiểm tra xem gọi lần đầu, nếu k phải ẩn skip hiện arrow
    private boolean fist_time;

    public FeedbackFragment(boolean fist_time, ArrayList<Bill_Details> list_feedback, Listener_for_BackFragment listener_for_backFragment) {
        this.fist_time = fist_time;
        this.list_bill_details_feedback = list_feedback;
        this.listener_for_backFragment = listener_for_backFragment;
        methods = new Methods(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        SetUp(view);
        return view;
    }

    private void SetUp(View view){
        txtSkip_Feedback_Frag = (TextView) view.findViewById(R.id.txtSkip_Feedback_Frag);
        btn_Send_Feeling_Frag = (Button) view.findViewById(R.id.btn_Send_Feedback_Frag);
        recycler_Feedback = (RecyclerView) view.findViewById(R.id.recycler_Feedback);

        img_Back_Feedback_Frag = (ImageView) view.findViewById(R.id.img_Back_Feedback_Frag);
        img_Back_Feedback_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_backFragment.orderBill_Or_BackFragment();
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

    private void update_Rate_Food(int ID_food){
        Check_task_listener listener_update_food = new Check_task_listener() {
            @Override
            public void onPre() {

            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                if(isSucces){
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
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
