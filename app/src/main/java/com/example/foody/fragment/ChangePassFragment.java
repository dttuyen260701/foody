package com.example.foody.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foody.R;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Listener_for_ChangePass;
import com.example.foody.utils.Constant_Values;

public class ChangePassFragment extends Fragment {
    private static final String txtWARNING = "Password does not match";
    private ImageView img_back_ChangPass_Frag;
    private EditText txtPass_Change, txtNewPass_Change,
            txtNewPass_Change2;
    private TextView txtWarning_ChangePass;
    private Button btnChange_change_Pass;
    private Listener_for_BackFragment listener_Back;
    private Listener_for_ChangePass listener_for_changePass;

    private boolean check_matchPass = false;

    public ChangePassFragment(Listener_for_BackFragment listener_Back,
                              Listener_for_ChangePass listener_for_changePass) {
        this.listener_Back = listener_Back;
        this.listener_for_changePass = listener_for_changePass;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepass, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        img_back_ChangPass_Frag = (ImageView) view.findViewById(R.id.img_back_ChangPass_Frag);
        txtPass_Change = (EditText) view.findViewById(R.id.txtPass_Change);
        txtNewPass_Change= (EditText) view.findViewById(R.id.txtNewPass_Change);
        txtNewPass_Change2 = (EditText) view.findViewById(R.id.txtNewPass_Change2);
        txtWarning_ChangePass = (TextView) view.findViewById(R.id.txtWarning_ChangePass);
        txtWarning_ChangePass.setText("");

        btnChange_change_Pass = (Button) view.findViewById(R.id.btnChange_change_Pass);

        img_back_ChangPass_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_Back.orderBill_Or_BackFragment();
            }
        });

        txtNewPass_Change2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newpass = txtNewPass_Change.getText().toString();
                String newpass2 = s.toString();
                if(newpass.equals(newpass2)){
                    check_matchPass = true;
                    txtWarning_ChangePass.setText("");
                } else {
                    check_matchPass = false;
                    txtWarning_ChangePass.setText(txtWARNING);
                }
            }
        });

        btnChange_change_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPass_Change.getText().toString().equals(
                        Constant_Values.getCustomer().getPassword())){
                    if(check_matchPass){
                        listener_for_changePass.ChangePass(txtNewPass_Change.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Pass doesn't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Old pass is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
