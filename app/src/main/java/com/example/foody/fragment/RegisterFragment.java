package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.foody.R;
import com.example.foody.listeners.Listener_for_BackFragment;

public class RegisterFragment extends Fragment {
    private Listener_for_BackFragment listener_back;

    public RegisterFragment(Listener_for_BackFragment listener_back) {
        this.listener_back = listener_back;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){

    }
}
