package com.example.foody.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foody.R;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.utils.Constant_Values;

public class AboutUsFragment extends Fragment {
    private ImageView img_Back_AboutUs_Frag;
    private ConstraintLayout layout_map_About_Frag;
    private TextView txt_Location_About_Frag, txt_Shipping_fee_Per_about_Frag,
            txtPhone_About_Frag, txtTerm_OU_About_Frag;
    private Listener_for_BackFragment listener_for_backFragment;

    public AboutUsFragment(Listener_for_BackFragment listener_for_backFragment) {
        this.listener_for_backFragment = listener_for_backFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);
        SetUp(view);
        return view;
    }

    private void SetUp(View view){
        img_Back_AboutUs_Frag = (ImageView) view.findViewById(R.id.img_Back_AboutUs_Frag);

        layout_map_About_Frag = (ConstraintLayout) view.findViewById(R.id.layout_map_About_Frag);

        txt_Location_About_Frag = (TextView) view.findViewById(R.id.txt_Location_About_Frag);
        txt_Shipping_fee_Per_about_Frag = (TextView) view.findViewById(R.id.txt_Shipping_fee_Per_about_Frag);
        txtPhone_About_Frag = (TextView) view.findViewById(R.id.txtPhone_About_Frag);
        txtTerm_OU_About_Frag = (TextView) view.findViewById(R.id.txtTerm_OU_About_Frag);

        MapFragment mapFragment = new MapFragment(null);
        mapFragment.searchAddress(Constant_Values.Addres_Res);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_MapFragment, mapFragment);
        transaction.addToBackStack("About");
        transaction.commit();

        txt_Location_About_Frag.setText("Our Address: " + Constant_Values.Addres_Res);
        txt_Shipping_fee_Per_about_Frag.setText("Shipping fee per 1 km: $" + Constant_Values.Shipping_Fee_Per_1Km);
        txtPhone_About_Frag.setText("Contact with us: " + Constant_Values.PHONENUMBER);
        txtPhone_About_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogContactFragment dialogContactFragment = new DialogContactFragment();
                dialogContactFragment.show(getFragmentManager(), "dialog_contact");
            }
        });

        txtTerm_OU_About_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewFragment toUFragment = new WebviewFragment(new Listener_for_BackFragment() {
                    @Override
                    public void orderBill_Or_BackFragment() {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }, true);
                back_to_AboutFragment(toUFragment);
            }
        });
    }

    private void back_to_AboutFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.addToBackStack("About");
        transaction.commit();
    }
}
