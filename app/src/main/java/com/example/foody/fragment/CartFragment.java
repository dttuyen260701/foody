package com.example.foody.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody.R;
import com.example.foody.adapters.BillDetailAdapter;
import com.example.foody.asyntask.Get_Next_IDBill_Asyntask;
import com.example.foody.asyntask.InsertOrDelOrUpdate_Asynctask;
import com.example.foody.asyntask.Load_Bill_Detail_Asynctask;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.CartAdapter_Listenner;
import com.example.foody.listeners.Get_Next_IDBill_Listener;
import com.example.foody.listeners.Check_task_listener;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Listener_for_PickAddress;
import com.example.foody.listeners.Load_Bill_Detail_Listener;
import com.example.foody.models.Bill;
import com.example.foody.models.Bill_Details;
import com.example.foody.utils.Methods;
import com.example.foody.utils.Constant_Values;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.RequestBody;

public class CartFragment extends Fragment {
    //ten button
    private static final String btn_ORDER = "ORDER";
    private static final String btn_PICK_ADDRESS = "PICK ADDRESS";
    private static final String btn_TOO_LONG = "TOO LONG";
    private static final String btn_RECEIVED = "RECEIVED";
    private static final String btn_RE_ORDER = "RE-ORDER";
    private static final String btn_FEEDBACK = "FEEDBACK";

    private static final String txtADDRESS_PICK = "Pick your address";

    //list Cart static trong 1 chuong trinh
    private static ArrayList<Bill_Details> list_Bill_details;
    private static int ID_Bill_New = -1;
    // sẽ lấy bằng vị trí 2 điểm
    private float total = 0f, distance = 0f, shipping_fee = 0f, total_in_address = 0f;
    private Bill bill_holder;
    private static String address_cus = txtADDRESS_PICK;

    private RecyclerView recycler_Cart;
    private TextView txt_Total_Cart_Frag, txtClear_Cart_Frag,
            txtAddress_Cart_Frag, txtDistance_Cart_Frag,
            txtShippingfee_Cart_Frag, txt_Tittle_Cart_Fragment,
            txtDistanceText_Cart_Frag;
    private ImageView img_Back_Cart_Frag;
    private Button btnPick_address_Cart_Frag, btnOrder_Cart_Frag;
    private static Methods methods;

    private ProgressBar progressBar_Cart_frag;

    //true la cho Cart, false la cho bill
    private boolean for_BillorCart, is_done_dill;
    private Listener_for_BackFragment listener_for_backFragment;
    private ArrayList<Bill_Details> list_Bill_details_temp;
    private BillDetailAdapter adapter;

    private long mLastClick_Order = 0, mLastClick_PickAddress = 0;

    public CartFragment(Bill bill_holder,ArrayList<Bill_Details> list_Bill_details_temp,
                        boolean for_BillorCart1, boolean is_done_dill, Listener_for_BackFragment listener) {
        this.bill_holder = (for_BillorCart1) ? new Bill() : bill_holder;
        this.listener_for_backFragment = listener;
        this.list_Bill_details_temp = list_Bill_details_temp;
        this.for_BillorCart = for_BillorCart1;
        this.is_done_dill = is_done_dill;
        this.list_Bill_details_temp = new ArrayList<>();
        if(list_Bill_details == null)
            list_Bill_details = new ArrayList<>();
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
        if(!for_BillorCart){
            load_Bill_Detail_data(bill_holder.getiD_Bill());
        }
        ArrayList<Bill_Details> list_Bill_details_forAdapter =
                (for_BillorCart) ? list_Bill_details : list_Bill_details_temp;
        adapter = new BillDetailAdapter(list_Bill_details_forAdapter, view.getContext(), for_BillorCart,
                new CartAdapter_Listenner() {
                    @Override
                    public void TinhTong(float Tong, boolean check) {
                        total = Tong;
                        total_in_address = (for_BillorCart) ? Tong + distance * Constant_Values.Shipping_Fee_Per_1Km
                                    : Tong + bill_holder.getShipping_fee();
                        total_in_address = (float) ((float) Math.round(total_in_address*100)/100);
                        txt_Total_Cart_Frag.setText("$"+ total_in_address );
                        if(check)
                            Empty_Cart_View();
                    }
                });
        btnOrder_Cart_Frag.setEnabled(!adapter.isEmpty_Cart());
        recycler_Cart.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_Cart.setAdapter(adapter);
        return view;
    }

    private void SetUp(View view){
        methods = new Methods(view.getContext());

        recycler_Cart = (RecyclerView) view.findViewById(R.id.recycler_Cart);
        txt_Total_Cart_Frag = (TextView) view.findViewById(R.id.txt_Total_Cart_Frag);
        txtClear_Cart_Frag = (TextView) view.findViewById(R.id.txtClear_Cart_Frag);

        btnOrder_Cart_Frag = (Button) view.findViewById(R.id.btnOrder_Cart_Frag);
        btnPick_address_Cart_Frag = (Button) view.findViewById(R.id.btnPick_address_Cart_Frag);

        txtShippingfee_Cart_Frag = (TextView) view.findViewById(R.id.txtShippingfee_Cart_Frag);
        txt_Tittle_Cart_Fragment = (TextView) view.findViewById(R.id.txt_Tittle_Cart_Fragment);

        txtAddress_Cart_Frag = (TextView) view.findViewById(R.id.txtAddress_Cart_Frag);
        txtDistance_Cart_Frag = (TextView) view.findViewById(R.id.txtDistance_Cart_Frag);
        txtDistanceText_Cart_Frag = (TextView) view.findViewById(R.id.txtDistanceText_Cart_Frag);

        progressBar_Cart_frag = (ProgressBar) view.findViewById(R.id.progressBar_Cart_frag);

        img_Back_Cart_Frag = (ImageView) view.findViewById(R.id.img_Back_Cart_Frag);
        if(for_BillorCart) {
            txt_Tittle_Cart_Fragment.setText("Cart");

            shipping_fee = Constant_Values.Shipping_Fee_Per_1Km*distance;

            txtAddress_Cart_Frag.setText(address_cus);

            txtDistance_Cart_Frag.setText(distance + " Km");

            img_Back_Cart_Frag.setVisibility(View.GONE);
            txtClear_Cart_Frag.setVisibility(View.VISIBLE);

            btnOrder_Cart_Frag.setText(btn_ORDER);
            btnPick_address_Cart_Frag.setText(btn_PICK_ADDRESS);
        } else {
            txt_Tittle_Cart_Fragment.setText("Bill Detail");

            shipping_fee = bill_holder.getShipping_fee();
            txtDistance_Cart_Frag.setText("");
            txtDistanceText_Cart_Frag.setText("");
            txtAddress_Cart_Frag.setText(bill_holder.getAddress());

            img_Back_Cart_Frag.setVisibility(View.VISIBLE);
            img_Back_Cart_Frag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener_for_backFragment.orderBill_Or_BackFragment();
                }
            });
            txtClear_Cart_Frag.setVisibility(View.GONE);
            if(is_done_dill){
                btnOrder_Cart_Frag.setText(btn_FEEDBACK);
                btnPick_address_Cart_Frag.setText(btn_RE_ORDER);
            } else {
                btnOrder_Cart_Frag.setText(btn_RECEIVED);
                btnPick_address_Cart_Frag.setText(btn_TOO_LONG);
            }
        }

        shipping_fee = ((float) Math.round(shipping_fee*100)/100);
        txtShippingfee_Cart_Frag.setText("$" + shipping_fee);

        txtClear_Cart_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empty_Cart_View();
                adapter.clear_Cart();
            }
        });

        btnOrder_Cart_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClick_Order < 1000){
                    return;
                }
                mLastClick_Order = SystemClock.elapsedRealtime();
                on_click(btnOrder_Cart_Frag.getText().toString());
            }
        });

        btnPick_address_Cart_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClick_PickAddress < 1000){
                    return;
                }
                mLastClick_PickAddress = SystemClock.elapsedRealtime();
                on_click(btnPick_address_Cart_Frag.getText().toString());
            }
        });


    }

    private void on_click(String name){
        switch (name){
            case btn_ORDER:
                if(Constant_Values.getIdCus() != -1) {
                    boolean check_address = true;//kiểm tra có address chưa
                    if(address_cus.equals(txtADDRESS_PICK) || distance == 0f){
                        check_address = false;
                    } else {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date date =new Date();
                        try {
                            date = formatter.parse(formatter.format(new Date().getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Bill bill_delivery = new Bill(getID_Bill_New(), Constant_Values.getIdCus(), total_in_address,
                                date, address_cus, shipping_fee, false);
                        insert_Bill(bill_delivery);
                        next_ID();
                    }
                    if(check_address)
                        break;
                } else {
                    Toast.makeText(getActivity(), "Please login to order food", Toast.LENGTH_SHORT).show();
                    break;
                }
            case btn_PICK_ADDRESS:
                MapFragment_Parent mapFragment = new MapFragment_Parent(new Listener_for_BackFragment() {
                    @Override
                    public void orderBill_Or_BackFragment() {
                        if(getActivity() != null){
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("map");
                            if(fragment != null)
                                transaction.remove(fragment);
                            transaction.commit();
                        }
                    }
                }, new Listener_for_PickAddress() {
                    @Override
                    public void onClick_pick(String address, float distance_1) {
                        address_cus = address;
                        txtAddress_Cart_Frag.setText(address_cus);
                        distance = ((float) Math.round(distance_1*100)/100);
                        shipping_fee = distance*Constant_Values.Shipping_Fee_Per_1Km;
                        shipping_fee = ((float) Math.round(shipping_fee*100)/100);
                        total_in_address = total + shipping_fee;
                        total_in_address = ((float) Math.round(total_in_address*100)/100);
                        txtDistance_Cart_Frag.setText(distance + " Km");
                        txtShippingfee_Cart_Frag.setText("$" + shipping_fee );
                        txt_Total_Cart_Frag.setText("$" + total_in_address);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("map");
                        transaction.remove(fragment);
                        transaction.commit();
                    }
                });
                back_toCartFragment(mapFragment, "map");
                break;
            case btn_RECEIVED:
                update_Bill_to_done(bill_holder.getiD_Bill());
                call_Feedback_Frag(true);
                break;
            case btn_TOO_LONG:
                DialogTooLongFragment dialogTooLongFragment = new DialogTooLongFragment();
                dialogTooLongFragment.show(getFragmentManager(), "dialog_too_long");
                break;
            case btn_RE_ORDER:
                list_Bill_details.clear();
                for(Bill_Details i : list_Bill_details_temp){
                    i.setiD_Bill(ID_Bill_New);
                    i.setRate(0f);
                    i.setReviews("");
                    list_Bill_details.add(i);
                }
                break;
            case btn_FEEDBACK:
                call_Feedback_Frag(false);
                break;
            default:
                break;
        }
    }

    private void call_Feedback_Frag(boolean is_first_time){
        FeedbackFragment feedbackFragment = new FeedbackFragment(getActivity(), is_first_time, list_Bill_details_temp,
                new Listener_for_BackFragment() {
                    @Override
                    public void orderBill_Or_BackFragment() {
                        if(getActivity() != null){
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("feedback");
                            if(fragment != null)
                                transaction.remove(fragment);
                            transaction.commit();
                        }
                    }
                });
        back_toCartFragment(feedbackFragment, "feedback");
    }

    private void back_toCartFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        //add để trạng thái trước được lưu
        transaction.add(R.id.fragment_layout, fragment, tag);
        transaction.addToBackStack("Bill_Details");
        transaction.commit();
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
        total = 0f;
        txt_Total_Cart_Frag.setText("$" + total);
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

    private void insert_Bill(Bill bill){
        Check_task_listener listener_insert = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                txtClear_Cart_Frag.setVisibility(View.GONE);
                btnOrder_Cart_Frag.setEnabled(false);
                MainActivity.Navi_disable();
                progressBar_Cart_frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                btnOrder_Cart_Frag.setEnabled(true);
                MainActivity.Navi_enable();
                progressBar_Cart_frag.setVisibility(View.GONE);
                txtClear_Cart_Frag.setVisibility(View.VISIBLE);
                if(isSucces){
                    for(int i = 0; i < list_Bill_details.size(); ++i){
                        insert_Bill_Detail(list_Bill_details.get(i), i);
                    }
                } else
                    Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Bundle bundle = new Bundle();
        bundle.putInt("ID_Bill", bill.getiD_Bill());
        bundle.putInt("ID_Cus", bill.getiD_Cus());
        bundle.putFloat("Total", bill.getTotal());
        bundle.putString("Time", dateFormat.format(bill.getTime()));
        bundle.putString("Address", bill.getAddress());
        bundle.putFloat("Shipping_fee", bill.getShipping_fee());
        bundle.putBoolean("done", bill.isDone());
        RequestBody requestBody = methods.getRequestBody("method_insert_bill", bundle);
        InsertOrDelOrUpdate_Asynctask asynctask = new InsertOrDelOrUpdate_Asynctask(listener_insert, requestBody,
                Constant_Values.URL_BILL_API);
        asynctask.execute();
    }

    private void insert_Bill_Detail(Bill_Details bill_details, int position) {
        Check_task_listener listener = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                MainActivity.Navi_disable();
                btnPick_address_Cart_Frag.setEnabled(false);
                btnOrder_Cart_Frag.setEnabled(false);
                progressBar_Cart_frag.setVisibility(View.VISIBLE);
                txtClear_Cart_Frag.setVisibility(View.GONE);
            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                MainActivity.Navi_enable();
                if (isSucces) {
                    txtClear_Cart_Frag.setVisibility(View.GONE);
                    if(position == list_Bill_details.size()-1){
                        txtClear_Cart_Frag.setVisibility(View.VISIBLE);
                        btnPick_address_Cart_Frag.setEnabled(true);
                        btnOrder_Cart_Frag.setEnabled(true);
                        BillFragment.setCheck_NewBill(true);//load lại bill
                        Empty_Cart_View();
                        adapter.clear_Cart();
                        progressBar_Cart_frag.setVisibility(View.GONE);
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
        RequestBody requestBody = methods.getRequestBody("method_insert_bill_detail", bundle);
        InsertOrDelOrUpdate_Asynctask asynctask = new InsertOrDelOrUpdate_Asynctask(listener, requestBody,
                Constant_Values.URL_BILL_DETAIL_API);
        asynctask.execute();
    }

    private void update_Bill_to_done(int ID_Bill){
        Check_task_listener listener_for_Update = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                MainActivity.Navi_disable();
            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                MainActivity.Navi_enable();
                if(isSucces){
                    btnOrder_Cart_Frag.setText(btn_FEEDBACK);
                    btnPick_address_Cart_Frag.setText(btn_RE_ORDER);
                    BillFragment.setDoneBillbyID(bill_holder.getiD_Bill());
                    //BillFragment.setCheck_NewBill(true);
                } else
                    Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Bill", ID_Bill);
        RequestBody requestBody = methods.getRequestBody("method_update_bill", bundle);
        InsertOrDelOrUpdate_Asynctask asynctask = new InsertOrDelOrUpdate_Asynctask(listener_for_Update,
                requestBody, Constant_Values.URL_BILL_API);
        asynctask.execute();
    }

    //có phải bill tạm thời kh
    private void load_Bill_Detail_data(int ID_Bill){
        Load_Bill_Detail_Listener listener = new Load_Bill_Detail_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                progressBar_Cart_frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSucces, ArrayList<Bill_Details> bill_details_List) {
                progressBar_Cart_frag.setVisibility(View.GONE);
                if(isSucces){
                    list_Bill_details_temp.addAll(bill_details_List);
                    btnOrder_Cart_Frag.setEnabled(true);
                    adapter.notifyDataSetChanged();
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