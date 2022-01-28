package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.adapters.BillAdapter;
import com.example.foody.asyntask.Load_Bill_Asynctask;
import com.example.foody.asyntask.Load_Bill_Detail_Asynctask;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Load_Bill_Detail_Listener;
import com.example.foody.listeners.Load_Bill_Listener;
import com.example.foody.listeners.RecyclerView_Item_Listener;
import com.example.foody.models.Bill;
import com.example.foody.models.Bill_Details;
import com.example.foody.utils.Methods;
import com.example.foody.utils.Constant_Values;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.RequestBody;

public class BillFragment extends Fragment {
    //xem có bill mới hay k
    private static boolean check_NewBill;

    private RecyclerView recycler_Bill;
    private ProgressBar progressBar_Bill_Frag;
    private static ArrayList<Bill> list_Bill;
    private ArrayList<Bill_Details> list_Bill_detail;//for bill delivery
    private Methods methods;
    private BillAdapter adapter;
    private BottomNavigationView bottom_Navigation;

    public BillFragment() {
        if(list_Bill == null)
            list_Bill = new ArrayList<>();
        check_NewBill = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        SetUp(view);
        bottom_Navigation.setVisibility(View.VISIBLE);
        if(check_NewBill){
            load_Bill_data();
            check_NewBill = false;
        }
        adapter = new BillAdapter(list_Bill, getActivity(), new RecyclerView_Item_Listener() {
            @Override
            public void onClick(int ID) {
                load_Bill_Detail_data(ID);
            }
        });
        recycler_Bill.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_Bill.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        recycler_Bill = (RecyclerView) view.findViewById(R.id.recycler_Bill);
        progressBar_Bill_Frag = (ProgressBar) view.findViewById(R.id.progressBar_Bill_Frag);
        bottom_Navigation = (BottomNavigationView) getActivity().findViewById(R.id.bottom_Navigation);
        methods = new Methods(getContext());
    }

    public static void setDoneBillbyID(int ID_Bill){
        if(list_Bill != null)
            for(Bill i : list_Bill)
                if(i.getiD_Bill() == ID_Bill)
                    i.setDone(true);
    }

    public static void setCheck_NewBill(boolean check_NewBill) {
        BillFragment.check_NewBill = check_NewBill;
    }

    private void load_Bill_data(){
        list_Bill.clear();
        Load_Bill_Listener listener = new Load_Bill_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                progressBar_Bill_Frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSucces, ArrayList<Bill> list_Bill_result) {
                if(isSucces){
                    progressBar_Bill_Frag.setVisibility(View.GONE);
                    list_Bill.addAll(list_Bill_result);
                    Collections.reverse(list_Bill);
                    if(list_Bill.isEmpty())
                        recycler_Bill.setBackgroundResource(R.drawable.no_job_today);
                } else{
                    Toast.makeText(getContext(), "Lỗi Server", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Cus", Constant_Values.getIdCus());
        RequestBody requestBody = methods.getRequestBody("method_get_bill_data", bundle);
        Load_Bill_Asynctask asynctask = new Load_Bill_Asynctask(listener, requestBody);
        asynctask.execute();
    }

    private Bill getBillbyID(int ID_Bill){
        Bill bill = new Bill();{
            for (Bill i : list_Bill)
                if(i.getiD_Bill() == ID_Bill)
                    return i;
        }
        return bill;
    }

    private void back_to_BillFragmet(CartFragment cartFragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, cartFragment);
        transaction.addToBackStack("Bill");
        transaction.commit();
    }

    //có phải bill tạm thời kh
    private void load_Bill_Detail_data(int ID_Bill){
        Load_Bill_Detail_Listener listener = new Load_Bill_Detail_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                MainActivity.Navi_disable();
                progressBar_Bill_Frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSucces, ArrayList<Bill_Details> bill_details_List) {
                MainActivity.Navi_enable();
                progressBar_Bill_Frag.setVisibility(View.GONE);
                if(isSucces){
                    boolean is_done_bill = false;
                    for(Bill bill : list_Bill)
                        if(bill.getiD_Bill() == ID_Bill)
                            is_done_bill = bill.isDone();
                    CartFragment cartFragment = new CartFragment(getBillbyID(ID_Bill), bill_details_List,
                            false, is_done_bill,
                            new Listener_for_BackFragment() {
                        @Override
                        public void orderBill_Or_BackFragment() {
                            getFragmentManager().popBackStack();
                        }
                    });
                    back_to_BillFragmet(cartFragment);
                } else
                    Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Bill", ID_Bill);
        RequestBody requestBody = methods.getRequestBody("method_get_bill_detail_data", bundle);
        Load_Bill_Detail_Asynctask asynctask = new Load_Bill_Detail_Asynctask(listener, requestBody);
        asynctask.execute();
    }
}
