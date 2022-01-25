package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foody.R;
import com.example.foody.asyntask.Load_Customer_Asynctask;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Load_Customer_Listener;
import com.example.foody.models.Customer;
import com.example.foody.utils.Constant_Values;
import com.example.foody.utils.Methods;

import okhttp3.RequestBody;

public class LoginFragment extends Fragment {
    private Listener_for_BackFragment listener_backFrag;
    private ImageView img_Back_Login_Frag;
    private EditText txtEmail_login, txtPass_login;
    private TextView txtforgetpass_login;
    private Button btnLogin_login, btnRegister_login;

    public LoginFragment(Listener_for_BackFragment listener_backFrag) {
        this.listener_backFrag = listener_backFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        img_Back_Login_Frag = (ImageView) view.findViewById(R.id.img_Back_Login_Frag);
        txtEmail_login = (EditText) view.findViewById(R.id.txtEmail_login);
        txtPass_login = (EditText) view.findViewById(R.id.txtPass_login);
        txtforgetpass_login = (TextView) view.findViewById(R.id.txtforgetpass_login);
        btnLogin_login = (Button) view.findViewById(R.id.btnLogin_login);
        btnRegister_login = (Button) view.findViewById(R.id.btnRegister_login);

        img_Back_Login_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_backFrag.orderBill_Or_BackFragment();
            }
        });

        txtforgetpass_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewFragment forget_pass = new WebviewFragment(new Listener_for_BackFragment() {
                    @Override
                    public void orderBill_Or_BackFragment() {
                        getFragmentManager().popBackStack();
                    }
                }, false);
                back_to_AccountsFragment(forget_pass);
            }
        });

        btnLogin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = txtEmail_login.getText().toString();
                String pass = txtPass_login.getText().toString();
                if(gmail.equals("") || pass.equals("")){
                    Toast.makeText(getActivity(),
                            "Please enter enough information.", Toast.LENGTH_SHORT).show();
                } else {
                    login_Cus(gmail, pass);
                }
            }
        });
    }

    private void back_to_AccountsFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_layout, fragment);
        transaction.addToBackStack("Login");
        transaction.commit();
    }

    //Check Login
    private void login_Cus(String gmail, String password){
        Methods methods = new Methods(getContext());
        Load_Customer_Listener listener1 = new Load_Customer_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onEnd(boolean isSussed, Customer customer) {
                if(isSussed){
                    if(gmail.equals(customer.getGmail())
                            && password.equals(customer.getPassword())){
                        Constant_Values.setIdCus(customer.getiD_Cus());
                        listener_backFrag.orderBill_Or_BackFragment();
                        Toast.makeText(getActivity(), "Login Succes.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getContext(), "Wrong mail or password!" , Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putString("Gmail", gmail);
        RequestBody requestBody = methods.getRequestBody("method_get_customer_data", bundle);
        Load_Customer_Asynctask asyntask = new Load_Customer_Asynctask(listener1, requestBody);
        asyntask.execute();
    }
}
