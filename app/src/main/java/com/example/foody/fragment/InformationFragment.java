package com.example.foody.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foody.R;
import com.example.foody.asyntask.InsertOrDelOrUpdate_Asynctask;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.Check_task_listener;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Listener_for_ChangePass;
import com.example.foody.models.Customer;
import com.example.foody.utils.Constant_Values;
import com.example.foody.utils.Methods;

import okhttp3.RequestBody;

public class InformationFragment extends Fragment {
    private EditText txtGmail_Infor_Frag, txtName_Infor_Frag,
            txtPhone_Infor_Frag, txtPass_Infor_Frag;
    private Button btn_Change_Pass, btnUpdate_Infor_Frag;
    private ImageView img_Back_Infor_Frag;
    private Listener_for_BackFragment listener_for_backFragment;
    private Context context;
    private ProgressBar progressBar_Infor_Frag;

    public InformationFragment(Context context, Listener_for_BackFragment listener_for_backFragment) {
        this.context = context;
        this.listener_for_backFragment = listener_for_backFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        txtGmail_Infor_Frag = (EditText) view.findViewById(R.id.txtGmail_Infor_Frag);
        txtName_Infor_Frag = (EditText) view.findViewById(R.id.txtName_Infor_Frag);
        txtPhone_Infor_Frag = (EditText) view.findViewById(R.id.txtPhone_Infor_Frag);
        txtPass_Infor_Frag = (EditText) view.findViewById(R.id.txtPass_Infor_Frag);
        progressBar_Infor_Frag = (ProgressBar) view.findViewById(R.id.progressBar_Infor_Frag);

        btn_Change_Pass = (Button) view.findViewById(R.id.btn_Change_Pass);
        btnUpdate_Infor_Frag = (Button) view.findViewById(R.id.btnUpdate_Infor_Frag);
        img_Back_Infor_Frag = (ImageView) view.findViewById(R.id.img_Back_Register_Frag);

        txtGmail_Infor_Frag.setText(Constant_Values.getCustomer().getGmail());
        txtName_Infor_Frag.setText(Constant_Values.getCustomer().getName_Cus());
        txtPhone_Infor_Frag.setText(Constant_Values.getCustomer().getPhone());
        txtPass_Infor_Frag.setText(Constant_Values.getCustomer().getPassword());

        img_Back_Infor_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_for_backFragment.orderBill_Or_BackFragment();
            }
        });

        btn_Change_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassFragment changePassFragment = new ChangePassFragment(new Listener_for_BackFragment() {
                    @Override
                    public void orderBill_Or_BackFragment() {
                        getFragmentManager().popBackStack();
                    }
                }, new Listener_for_ChangePass() {
                    @Override
                    public void ChangePass(String newpass) {
                        Customer customer = new Customer(Constant_Values.getIdCus(),
                                Constant_Values.getCustomer().getGmail(), newpass,
                                Constant_Values.getCustomer().getName_Cus(), Constant_Values.getCustomer().getPhone());
                        update_Cus(customer, false);
                    }
                });
                backInforFragment(changePassFragment);
            }
        });

        btnUpdate_Infor_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = new Customer(Constant_Values.getIdCus(),
                        txtGmail_Infor_Frag.getText().toString(), txtPass_Infor_Frag.getText().toString(),
                        txtName_Infor_Frag.getText().toString(), txtPhone_Infor_Frag.getText().toString());
                update_Cus(customer, false);
            }
        });
    }

    private void backInforFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.addToBackStack("Information");
        transaction.commit();
    }

    //dành cho changpass hay không
    private void update_Cus(Customer cus, boolean is_ChangPass){
        Methods methods = new Methods(getContext());
        Check_task_listener check_update = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(context, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                MainActivity.Navi_disable();
                Constant_Values.setDoing_task(true);
                progressBar_Infor_Frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSucces, boolean insert_Success) {
                progressBar_Infor_Frag.setVisibility(View.GONE);
                MainActivity.Navi_enable();
                Constant_Values.setDoing_task(false);
                if(isSucces){
                    Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show();
                    Constant_Values.setCustomer(cus);
                    listener_for_backFragment.orderBill_Or_BackFragment();
                }
                else
                    Toast.makeText(context, "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Cus", cus.getiD_Cus());
        bundle.putString("Gmail", cus.getGmail());
        bundle.putString("Password", cus.getPassword());
        bundle.putString("Name_Cus", cus.getName_Cus());
        bundle.putString("Phone", cus.getPhone());
        RequestBody requestBody = methods.getRequestBody("method_update_customer", bundle);
        InsertOrDelOrUpdate_Asynctask asyntask = new InsertOrDelOrUpdate_Asynctask(check_update, requestBody,
                Constant_Values.URL_CUSTOMER_API);
        asyntask.execute();
    }
}
