package com.example.foody.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody.R;
import com.example.foody.adapters.BillDetailAdapter;
import com.example.foody.asyntask.Get_Next_IDBill_Asyntask;
import com.example.foody.listeners.CartAdapter_Listenner;
import com.example.foody.listeners.Get_Next_IDBill_Listener;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.models.Bill_Details;
import com.example.foody.utils.Methods;
import com.example.foody.utils.Constant_Values;

import java.util.ArrayList;

import okhttp3.RequestBody;

public class CartFragment extends Fragment {
    //list Cart static trong 1 chuong trinh
    private static ArrayList<Bill_Details> list_Bill_details;
    // sẽ lấy bằng vị trí 2 điểm
    private float distance = 1f;
    private static int ID_Bill_New = -1;
    private float total = 0f;

    private RecyclerView recycler_Cart;
    private TextView txt_Total_Cart_Frag, txtClear_Cart_Frag,
            txtAddress_Cart_Frag, txtDistance_Cart_Frag,
            txtShippingfee_Cart_Frag, txt_Tittle_Cart_Fragment;
    private ImageView img_Back_Cart_Frag;
    private Button btnPick_address_Cart_Frag, btnOrder_Cart_Frag;
    private static Methods methods;

    //true la cho Cart, false la cho bill
    private boolean for_BillorCart;
    private Listener_for_BackFragment listener;
    private ArrayList<Bill_Details> list_Bill_details_temp;
    private BillDetailAdapter adapter;

    public CartFragment(ArrayList<Bill_Details> list_Bill_details_temp, boolean for_BillorCart, Listener_for_BackFragment listener) {
        this.listener = listener;
        this.list_Bill_details_temp = list_Bill_details_temp;
        this.for_BillorCart = for_BillorCart;
        if(this.list_Bill_details_temp == null)
            this.list_Bill_details_temp = new ArrayList<>();
        if(this.list_Bill_details == null)
            this.list_Bill_details = new ArrayList<>();
        if(methods == null)
            methods = new Methods(getActivity());
        if(ID_Bill_New == -1){
            next_ID();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        SetUp(view);
        ArrayList<Bill_Details> list_Bill_details_forAdapter =
                (for_BillorCart) ? list_Bill_details : list_Bill_details_temp;
        adapter = new BillDetailAdapter(list_Bill_details_forAdapter, view.getContext(), for_BillorCart,
                new CartAdapter_Listenner() {
                    @Override
                    public void TinhTong(float Tong, boolean check) {
                        total = Tong + distance * Constant_Values.Shipping_Fee_Per_1Km;
                        total = (float) ((float) Math.round(total*100)/100);
                        txt_Total_Cart_Frag.setText("$"+ total );
                        if(check)
                            Empty_Cart_View();
                    }
                });

        if(adapter.isEmpty_Cart())
            btnOrder_Cart_Frag.setEnabled(false);
        else
            btnOrder_Cart_Frag.setEnabled(true);
        recycler_Cart.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_Cart.setAdapter(adapter);
        return view;
    }

    private void SetUp(View view){
        recycler_Cart = (RecyclerView) view.findViewById(R.id.recycler_Cart);
        txt_Total_Cart_Frag = (TextView) view.findViewById(R.id.txt_Total_Cart_Frag);
        txtClear_Cart_Frag = (TextView) view.findViewById(R.id.txtClear_Cart_Frag);

        btnOrder_Cart_Frag = (Button) view.findViewById(R.id.btnOrder_Cart_Frag);
        btnPick_address_Cart_Frag = (Button) view.findViewById(R.id.btnPick_address_Cart_Frag);

        txtShippingfee_Cart_Frag = (TextView) view.findViewById(R.id.txtShippingfee_Cart_Frag);
        txt_Tittle_Cart_Fragment = (TextView) view.findViewById(R.id.txt_Tittle_Cart_Fragment);

        txtAddress_Cart_Frag = (TextView) view.findViewById(R.id.txtAddress_Cart_Frag);
        txtDistance_Cart_Frag = (TextView) view.findViewById(R.id.txtDistance_Cart_Frag);

        img_Back_Cart_Frag = (ImageView) view.findViewById(R.id.img_Back_Cart_Frag);
        if(for_BillorCart) {
            txt_Tittle_Cart_Fragment.setText( "Cart");
            img_Back_Cart_Frag.setVisibility(View.GONE);
            txtClear_Cart_Frag.setVisibility(View.VISIBLE);
            btnOrder_Cart_Frag.setVisibility(View.VISIBLE);
            btnPick_address_Cart_Frag.setVisibility(View.VISIBLE);
        } else {
            txt_Tittle_Cart_Fragment.setText( "Bill Detail");
            img_Back_Cart_Frag.setVisibility(View.VISIBLE);
            img_Back_Cart_Frag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.orderBill_Or_BackFragment();
                }
            });
            txtClear_Cart_Frag.setVisibility(View.GONE);
            btnOrder_Cart_Frag.setVisibility(View.GONE);
            btnPick_address_Cart_Frag.setVisibility(View.GONE);
        }

        txtClear_Cart_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empty_Cart_View();
                adapter.clear_Cart();
            }
        });
        methods = new Methods(view.getContext());
    }

    public static int getID_Bill_New() {
        return ID_Bill_New;
    }

    public static ArrayList<Bill_Details> getList_Bill_details() {
        return list_Bill_details;
    }

    public static Bill_Details search_BillDetail_ByIDFood(int ID_Food){
        Bill_Details bill_details = new Bill_Details();
        for(Bill_Details i : list_Bill_details)
            if(i.getiD_Food() == ID_Food)
                return i;
        return bill_details;
    }

    public static void remove_from_Cart(int ID_Food){
        for(int i = 0; i < list_Bill_details.size(); ++i)
            if(list_Bill_details.get(i).getiD_Food() == ID_Food)
                //tránh ConcurrentModificationException
                list_Bill_details.remove(i);
    }

    public static void add_to_Cart(Bill_Details bill_details){
        list_Bill_details.add(bill_details);
    }

    private void Empty_Cart_View(){
        recycler_Cart.setBackgroundResource(R.drawable.no_job_today);
        txt_Total_Cart_Frag.setText("$0");
        btnOrder_Cart_Frag.setEnabled(false);
    }

    private static void next_ID(){
        Get_Next_IDBill_Listener listener = new Get_Next_IDBill_Listener() {
            @Override
            public void onEnd(int next_ID) {
                ID_Bill_New = next_ID;
            }
        };
        RequestBody requestBody =  methods.getRequestBody("method_get_next_IDBll", null);
        Get_Next_IDBill_Asyntask asyntask = new Get_Next_IDBill_Asyntask(listener, requestBody);
        asyntask.execute();
    }
}