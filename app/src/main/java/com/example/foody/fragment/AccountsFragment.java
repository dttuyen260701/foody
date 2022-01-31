package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foody.R;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.utils.Constant_Values;

public class AccountsFragment extends Fragment {
    private static final String btn_INFORMATION = "Your Information";
    private static final String btn_LOG_OUT = "Log Out";
    private static final String btn_SIGN_IN = "Sign In";
    private static final String btn_SIGN_UP = "Sign Up";

    Button btn_SignIn_Account_Frag, btn_SignUp_Account_Frag,
            btn_AboutUs_Account_Login_Frag, btn_TermofU_Account_Frag;
    TextView txtAccount_Frag;

    private Listener_for_BackFragment listener_for_backFragment = new Listener_for_BackFragment() {
        @Override
        public void orderBill_Or_BackFragment() {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    private Listener_for_BackFragment listener_login_succes_or_logout = new Listener_for_BackFragment() {
        @Override
        public void orderBill_Or_BackFragment() {
            BillFragment.setCheck_NewBill(true);
            FoodFragment.setCheck_NewCus(true);
            MainActivity.selecFoodMenu();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        btn_SignIn_Account_Frag = (Button) view.findViewById(R.id.btn_Setting_Account_Logined_Frag);
        btn_SignUp_Account_Frag = (Button) view.findViewById(R.id.btn_changePass_Account_Login_Frag);
        btn_AboutUs_Account_Login_Frag = (Button) view.findViewById(R.id.btn_AboutUs_Account_Login_Frag);
        btn_TermofU_Account_Frag = (Button) view.findViewById(R.id.btn_TermofU_Account_Login_Frag);

        txtAccount_Frag = (TextView) view.findViewById(R.id.txtAccount_Frag);

        if(Constant_Values.getIdCus() == -1){
            btn_SignIn_Account_Frag.setText(btn_SIGN_IN);
            btn_SignUp_Account_Frag.setText(btn_SIGN_UP);
        } else {
            String name = (Constant_Values.getCustomer().getName_Cus().equals(""))
                    ? "Your Information can help you change some thing."
                    : Constant_Values.getCustomer().getName_Cus();
            txtAccount_Frag.setText(name);
            btn_SignIn_Account_Frag.setText(btn_INFORMATION);
            btn_SignUp_Account_Frag.setText(btn_LOG_OUT);
        }

        btn_SignIn_Account_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_Button(btn_SignIn_Account_Frag.getText().toString());
            }
        });

        btn_SignUp_Account_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_Button(btn_SignUp_Account_Frag.getText().toString());
            }
        });

        btn_AboutUs_Account_Login_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsFragment aboutUsFragment = new AboutUsFragment(listener_for_backFragment);
                back_to_AccountsFragment(aboutUsFragment);
            }
        });

        btn_TermofU_Account_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewFragment toUFragment = new WebviewFragment(listener_for_backFragment, true);
                back_to_AccountsFragment(toUFragment);
            }
        });
    }

    private void onClick_Button(String btn_name){
        Fragment fragment = null;
        switch (btn_name){
            case btn_SIGN_IN:
                fragment = new SignInFragment(listener_for_backFragment,
                        listener_login_succes_or_logout, "", "", getContext());
                back_to_AccountsFragment(fragment);
                break;
            case btn_SIGN_UP:
                fragment = new SignUpFragment(listener_for_backFragment, getContext());
                back_to_AccountsFragment(fragment);
                break;
            case btn_INFORMATION:
                fragment = new InformationFragment(getContext(), listener_for_backFragment);
                back_to_AccountsFragment(fragment);
                break;
            case btn_LOG_OUT:
                DialogLogOutFragment dialogLogOutFragment = new DialogLogOutFragment(listener_login_succes_or_logout);
                dialogLogOutFragment.show(getFragmentManager(), "logout");
                break;
            default:
                break;
        }
    }

    private void back_to_AccountsFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.addToBackStack("Account");
        transaction.commit();
    }
}
