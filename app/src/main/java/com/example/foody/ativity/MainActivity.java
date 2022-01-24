package com.example.foody.ativity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.*;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foody.R;
import com.example.foody.asyntask.*;
import com.example.foody.fragment.*;
import com.example.foody.listeners.*;
import com.example.foody.models.*;
import com.example.foody.utils.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout main_layout;
    private Methods methods;
    private ArrayList<Foods> list_Foods;
    private ArrayList<Favorite> list_Favorite;
    private ArrayList<Bill> list_Bill;
    private BottomNavigationView bottom_Navigation;
    private Fragment fragment_Food, fragment_Cart, fragment_Bill, fragment_Accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        methods = new Methods(this);
        list_Foods = new ArrayList<>();
        list_Favorite = new ArrayList<>();
        list_Bill = new ArrayList<>();
        AnhXa();
        setUp();
//        Customer cus = new Customer(0, "test13@gmail.com", "123", "Thêm", "0123456789");
//        insert_Cus(cus);
//        Favorite fav = new Favorite(2, 3);
//        insert_or_del_Fav(fav, false);
//        login_Cus("tuan@gmail.com", "123");
//        load_Bill();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date date = new Date(System.currentTimeMillis());
//        try {
//            Bill bill = new Bill(0, 1, 12.0f, dateFormat.parse(dateFormat.format(date)), "Da Nang");
//            insert_Bill(bill);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Bill_Details bill_details = new Bill_Details(3, 2, 2, 15.0f, 3.5f, "Review");
//        insert_Bill_Detail(bill_details);
    }

//    //Test View
//    private void Test(){
//        list_Bill_Detail = new ArrayList<>();
//        list_Bill_Detail.add(new Bill_Details(4, 1, 1, 0f, 0f, "Good"));
//        list_Bill_Detail.add(new Bill_Details(4, 2, 2, 0f, 0f, "Good"));
//        list_Bill_Detail.add(new Bill_Details(4, 3, 2, 0f, 0f, "Good"));
//        list_Bill_Detail.add(new Bill_Details(4, 4, 1, 0f, 0f, "Good"));
//    }

    private void AnhXa(){
        main_layout = (ConstraintLayout) findViewById(R.id.layout_main);
        bottom_Navigation = (BottomNavigationView) findViewById(R.id.bottom_Navigation);
        bottom_Navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void setUp(){
        fragment_Food = new FoodFragment();
        fragment_Cart = new CartFragment(new Bill() ,new ArrayList<>(), true, false,
                new Listener_for_BackFragment() {
            @Override
            public void orderBill_Or_BackFragment() {

            }
        });
        //CartFragment.setList_Bill_details(list_Bill_Detail);
        fragment_Bill = new BillFragment();
        fragment_Accounts = new AccountsFragment();
        chang_Menu(fragment_Food);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_food:
                    chang_Menu(fragment_Food);
                    return true;
                case R.id.bottom_Cart:
                    chang_Menu(fragment_Cart);
                    return true;
                case R.id.bottom_bill:
                    chang_Menu(fragment_Bill);
                    return true;
                case R.id.bottom_account:
                    chang_Menu(fragment_Accounts);
                    return true;
            }
            return false;
        }
    };

    private void chang_Menu(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.add(R.id.fragment_layout, fragment, tag);
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

    //Check Login
    private void login_Cus(String gmail, String password){
        Load_Customer_Listener listener1 = new Load_Customer_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(MainActivity.this, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEnd(boolean isSussed, Customer customer) {
                if(isSussed){
                    StringBuilder builder = new StringBuilder();
                    builder.append(customer.toString());
                    Constant_Values.setIdCus(customer.getiD_Cus());
                }
                else
                    Toast.makeText(MainActivity.this, "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putString("Gmail", gmail);
        RequestBody requestBody = methods.getRequestBody("method_get_customer_data", bundle);
        Load_Customer_Asynctask asyntask = new Load_Customer_Asynctask(listener1, requestBody);
        asyntask.execute();
    }

    private void insert_Cus(Customer cus){
        Load_Customer_Listener listener_check = new Load_Customer_Listener() {
            @Override
            public void onPre() {

            }

            @Override
            public void onEnd(boolean isSussed, Customer customer) {
                if(customer.getiD_Cus() == -1){
                    Check_task_listener listener = new Check_task_listener() {
                        @Override
                        public void onPre() {
                            if (!methods.isNetworkConnected()) {
                                Toast.makeText(MainActivity.this, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onEnd(boolean isSucces, boolean insert_Success) {
                            if(isSucces){
                                String kq = (insert_Success) ? "Thành công" : "Thất bại";
                            }
                            else
                                Toast.makeText(MainActivity.this, "Lỗi Server", Toast.LENGTH_SHORT).show();
                        }
                    };
                    Bundle bundle = new Bundle();
                    bundle.putString("Gmail", cus.getGmail());
                    bundle.putString("Password", cus.getPassword());
                    bundle.putString("Name_Cus", cus.getName_Cus());
                    bundle.putString("Phone", cus.getPhone());
                    RequestBody requestBody = methods.getRequestBody("method_insert_customer", bundle);
                    InsertOrDelOrUpdate_Asynctask asyntask = new InsertOrDelOrUpdate_Asynctask(listener, requestBody,
                            Constant_Values.URL_CUSTOMER_API);
                    asyntask.execute();
                }
                else
                    ;
            }
        };
        Bundle bundle = new Bundle();
        bundle.putString("Gmail", cus.getGmail());
        RequestBody requestBody = methods.getRequestBody("method_get_customer_data", bundle);
        Load_Customer_Asynctask asyntask = new Load_Customer_Asynctask(listener_check, requestBody);
        asyntask.execute();
    }

    private void insert_Bill_Detail(Bill_Details bill_details){
        Check_task_listener listener = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(MainActivity.this, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                if(isSucces){
                    String kq = (insert_Success) ? "Thành công" : "Thất bại";
                } else
                    Toast.makeText(MainActivity.this, "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
}