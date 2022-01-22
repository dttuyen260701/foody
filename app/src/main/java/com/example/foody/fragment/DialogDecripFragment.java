package com.example.foody.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.foody.models.Foods;

public class DialogDecripFragment extends DialogFragment {
    private Foods foods;

    public DialogDecripFragment(Foods foods) {
        this.foods = foods;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(foods.getName_Food());
        dialog.setMessage(foods.getDescription_Food());

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog dialog_descrip = dialog.create();

        return dialog_descrip;
    }
}
