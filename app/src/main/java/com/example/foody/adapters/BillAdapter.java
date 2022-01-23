package com.example.foody.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.asyntask.Load_Bill_Detail_Asynctask;
import com.example.foody.ativity.MainActivity;
import com.example.foody.listeners.Load_Bill_Detail_Listener;
import com.example.foody.listeners.RecyclerView_Item_Listener;
import com.example.foody.models.Bill;
import com.example.foody.models.Bill_Details;
import com.example.foody.utils.Methods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.RequestBody;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private ArrayList<Bill> list_bill;
    private ArrayList<Bill_Details> list_Bill_Detail;
    private Context context;
    private RecyclerView_Item_Listener recy_item_listener;

    public BillAdapter(ArrayList<Bill> list_bill, Context context, RecyclerView_Item_Listener recy_item_listener) {
        this.list_bill = list_bill;
        this.context = context;
        this.recy_item_listener = recy_item_listener;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bill_row, parent, false);
        list_Bill_Detail = new ArrayList<>();
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        int index = position;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        int src_delivery = (list_bill.get(position).isDone()) ? R.drawable.bill_row : R.drawable.food_delivery;
        holder.img_Src_bill_row.setImageResource(src_delivery);

        String isDone = (list_bill.get(position).isDone()) ? "Received" : "Delivering";
        holder.txtIDBill_Bill_row.setText(isDone +"");
        holder.txtTime_Bill_row.setText(dateFormat.format(list_bill.get(position).getTime()));
        holder.txtTotal_Bill_row.setText("$" + list_bill.get(position).getTotal());
        holder.layout_bill_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recy_item_listener.onClick(list_bill.get(index).getiD_Bill());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_bill.size();
    }

    class BillViewHolder extends RecyclerView.ViewHolder{
        TextView txtIDBill_Bill_row, txtTime_Bill_row, txtTotal_Bill_row;
        ConstraintLayout layout_bill_row;
        ImageView img_Src_bill_row;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIDBill_Bill_row = (TextView) itemView.findViewById(R.id.txtIDBill_Bill_row);
            txtTime_Bill_row = (TextView) itemView.findViewById(R.id.txtTime_Bill_row);
            txtTotal_Bill_row = (TextView) itemView.findViewById(R.id.txtTotal_Bill_row);
            layout_bill_row = (ConstraintLayout) itemView.findViewById(R.id.layout_bill_row);
            img_Src_bill_row = (ImageView) itemView.findViewById(R.id.img_Src_bill_row);
        }
    }
}
