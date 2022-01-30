package com.example.foody.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foody.R;
import com.example.foody.asyntask.InsertOrDelOrUpdate_Asynctask;
import com.example.foody.asyntask.Load_Customer_Asynctask;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.Check_task_listener;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Load_Customer_Listener;
import com.example.foody.models.Customer;
import com.example.foody.utils.Constant_Values;
import com.example.foody.utils.Methods;

import okhttp3.RequestBody;

public class SignUpFragment extends Fragment {
    private static String txtWARNING = "Password does not match";
    private static String txtACCEPTCHECK = "You don't accept with term of use.";

    private Listener_for_BackFragment listener_back;
    private EditText txtID_register, txtPhone_Register,
            txtPass_register, txtPass2_register;
    private TextView txtWarning_register, txtLinkRules_register, txtAcceptCheck_register;
    private CheckBox cbaccept_register;
    private Button btnRegister_register;
    private ImageView img_Back_SignUp_Frag;
    private Context context;

    private boolean check_matchPass = false;

    public SignUpFragment(Listener_for_BackFragment listener_back, Context context) {
        this.listener_back = listener_back;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        img_Back_SignUp_Frag = (ImageView) view.findViewById(R.id.img_Back_Register_Frag);
        img_Back_SignUp_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_back.orderBill_Or_BackFragment();
            }
        });

        txtID_register = (EditText) view.findViewById(R.id.txtID_register);
        txtPhone_Register = (EditText) view.findViewById(R.id.txtPhone_Register);
        txtPass_register = (EditText) view.findViewById(R.id.txtPass_register);
        txtPass2_register = (EditText) view.findViewById(R.id.txtPass2_register);

        txtWarning_register = (TextView) view.findViewById(R.id.txtWarning_register);
        txtWarning_register.setText("");
        txtLinkRules_register = (TextView) view.findViewById(R.id.txtLinkRules_register);
        txtLinkRules_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewFragment ToU = new WebviewFragment(new Listener_for_BackFragment() {
                    @Override
                    public void orderBill_Or_BackFragment() {
                        if(getActivity() != null){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    }
                }, true);
                back_to_RegisterFragment(ToU);
            }
        });

        txtAcceptCheck_register = (TextView) view.findViewById(R.id.txtAcceptCheck_register);
        txtAcceptCheck_register.setText("");

        cbaccept_register = (CheckBox) view.findViewById(R.id.cbaccept_register);

        txtPass2_register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass1 = txtPass_register.getText().toString();
                String pass2 = s.toString();
                if(pass1.equals(pass2))
                    txtWarning_register.setText("");
                else
                    txtWarning_register.setText(txtWARNING);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass1 = txtPass_register.getText().toString();
                String pass2 = s.toString();
                if(pass1.equals(pass2)){
                    check_matchPass = true;
                    txtWarning_register.setText("");
                }
                else{
                    check_matchPass = false;
                    txtWarning_register.setText(txtWARNING);
                }
            }
        });

        btnRegister_register = (Button) view.findViewById(R.id.btnRegister_register);
        btnRegister_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = cbaccept_register.isChecked();
                if(txtID_register.getText().toString().length() == 0 ||
                    txtPass_register.getText().toString().length() == 0 ||
                    txtPass2_register.getText().toString().length() == 0 ||
                    txtPhone_Register.getText().toString().length() == 0) {
                    Toast.makeText(context, "Please enter enough information.", Toast.LENGTH_SHORT).show();
                } else {
                    if(check){
                        if(check_matchPass){
                            txtAcceptCheck_register.setText("");
                            Customer customer = new Customer(0, txtID_register.getText().toString(),
                                    txtPass_register.getText().toString(), "", txtPhone_Register.getText().toString());
                            insert_Cus(customer);
                        } else {
                            Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        txtAcceptCheck_register.setText(txtACCEPTCHECK);
                }
            }
        });
    }

    private void back_to_RegisterFragment(Fragment fragment){
        if(getActivity() != null){
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_layout, fragment);
            transaction.addToBackStack("Register");
            transaction.commit();
        }
    }

    //sau khi check mới insert
    private void insert_Cus_after_check(Customer cus, Methods methods){
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
                                Toast.makeText(context, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                            }
                            MainActivity.Navi_disable();
                        }

                        @Override
                        public void onEnd(boolean isSucces, boolean insert_Success) {
                            MainActivity.Navi_enable();
                            if(isSucces){
                                Toast.makeText(context, "Sign Up Success", Toast.LENGTH_SHORT).show();
                                SignInFragment signInFragment = new SignInFragment(listener_back,
                                        new Listener_for_BackFragment() {
                                            @Override
                                            public void orderBill_Or_BackFragment() {
                                                BillFragment.setCheck_NewBill(true);
                                                FoodFragment.setCheck_NewCus(true);
                                                MainActivity.selecFoodMenu();
                                            }
                                        }, txtID_register.getText().toString(), txtPass_register.getText().toString(), context);
                                if(getActivity() != null){
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragment_layout, signInFragment);
                                    transaction.commit();
                                }
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
                    RequestBody requestBody = methods.getRequestBody("method_insert_customer", bundle);
                    InsertOrDelOrUpdate_Asynctask asyntask = new InsertOrDelOrUpdate_Asynctask(listener, requestBody,
                            Constant_Values.URL_CUSTOMER_API);
                    asyntask.execute();
                }
                else
                    Toast.makeText(context, "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };
        Bundle bundle = new Bundle();
        bundle.putString("Gmail", cus.getGmail());
        RequestBody requestBody = methods.getRequestBody("method_get_customer_data", bundle);
        Load_Customer_Asynctask asyntask = new Load_Customer_Asynctask(listener_check, requestBody);
        asyntask.execute();
    }

    private void insert_Cus(Customer cus){
        Methods methods = new Methods(getContext());
        Check_task_listener check_task_listener = new Check_task_listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(context, "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                MainActivity.Navi_disable();
            }

            @Override
            //can_insert = true insert đưuọc
            public void onEnd(boolean isSucces, boolean can_insert) {
                MainActivity.Navi_enable();
                if(isSucces){
                    if(can_insert){
                        insert_Cus_after_check(cus, methods);
                    } else {
                        Toast.makeText(context, "Existed Gmail", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(context, "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };
        Bundle bundle = new Bundle();
        bundle.putString("Gmail", cus.getGmail());
        RequestBody requestBody = methods.getRequestBody("method_check_mail_exist", bundle);
        InsertOrDelOrUpdate_Asynctask asynctask = new InsertOrDelOrUpdate_Asynctask(check_task_listener,
                requestBody, Constant_Values.URL_CUSTOMER_API);
        asynctask.execute();
    }
}
