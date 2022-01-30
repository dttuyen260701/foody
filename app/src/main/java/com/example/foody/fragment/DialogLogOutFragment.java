package com.example.foody.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.models.Customer;
import com.example.foody.utils.Constant_Values;

public class DialogLogOutFragment extends DialogFragment {
    Listener_for_BackFragment listener;

    public DialogLogOutFragment(Listener_for_BackFragment listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Confirm");
        dialog.setMessage("Do you want log out?");

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Constant_Values.setIdCus(-1);
                Constant_Values.setCustomer(new Customer());
                Constant_Values.save_ID_Cus = getContext().getSharedPreferences("save_ID_Cus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = Constant_Values.save_ID_Cus.edit();
                editor.putInt("ID_Cus", -1);
                editor.commit();
                listener.orderBill_Or_BackFragment();
            }
        });

        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog dialog_descrip = dialog.create();

        return dialog_descrip;
    }
}
