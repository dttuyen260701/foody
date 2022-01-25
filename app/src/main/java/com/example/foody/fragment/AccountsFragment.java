package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foody.R;
import com.example.foody.listeners.Listener_for_BackFragment;

public class AccountsFragment extends Fragment {
    Button btn_SignIn_Account_Frag, btn_SignUp_Account_Frag,
            btn_Contact_Account_Frag, btn_TermofU_Account_Frag;
    private Listener_for_BackFragment listener_for_backFragment = new Listener_for_BackFragment() {
        @Override
        public void orderBill_Or_BackFragment() {
            getFragmentManager().popBackStack();
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
        btn_SignIn_Account_Frag = (Button) view.findViewById(R.id.btn_SignIn_Account_Frag);
        btn_SignUp_Account_Frag = (Button) view.findViewById(R.id.btn_SignUp_Account_Frag);
        btn_Contact_Account_Frag = (Button) view.findViewById(R.id.btn_Contact_Account_Frag);
        btn_TermofU_Account_Frag = (Button) view.findViewById(R.id.btn_TermofU_Account_Frag);

        btn_SignIn_Account_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment(listener_for_backFragment);
                back_to_AccountsFragment(loginFragment);
            }
        });

        btn_SignUp_Account_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment(listener_for_backFragment);
                back_to_AccountsFragment(registerFragment);
            }
        });

        btn_Contact_Account_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogContactFragment dialogContactFragment = new DialogContactFragment();
                dialogContactFragment.show(getFragmentManager(), "dialog_contact");
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

    private void back_to_AccountsFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_layout, fragment, "webview");
        transaction.addToBackStack("Account");
        transaction.commit();
    }
}
