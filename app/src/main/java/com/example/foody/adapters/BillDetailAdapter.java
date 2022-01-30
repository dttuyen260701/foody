package com.example.foody.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.fragment.FoodFragment;
import com.example.foody.listeners.CartAdapter_Listenner;
import com.example.foody.models.Bill_Details;
import com.example.foody.models.Foods;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder> {
    private ArrayList<Bill_Details> bill_details_list;
    private Context context;
    private CartAdapter_Listenner listener;
    //true la cho Cart, false la cho bill
    private boolean for_BillorCart;

    public BillDetailAdapter(ArrayList<Bill_Details> bill_details_list, Context context, boolean for_BillorCart,
                             CartAdapter_Listenner listener) {
        this.listener = listener;
        this.context = context;
        this.bill_details_list = bill_details_list;
        this.for_BillorCart = for_BillorCart;
        //Check co trống hay không
        listener.TinhTong(calculate_Total(), bill_details_list.isEmpty());
    }

    @NonNull
    @Override
    public BillDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.bill_detail_row, parent, false);
        return new BillDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailViewHolder holder, int position) {
        int index = position;
        Foods foods = FoodFragment.getByID(bill_details_list.get(index).getiD_Food());
        holder.img_Food_BillDetail_row.setImageBitmap(foods.getImage_Food());
        holder.txtName_food_Cart_row.setText(foods.getName_Food());
        holder.txt_count_Cart_row.setText("x" + bill_details_list.get(index).getCount());
        Foods food = FoodFragment.getByID(bill_details_list.get(index).getiD_Food());
        bill_details_list.get(index).setPrice_Total(Floor(foods.getPrice_Food()
                *bill_details_list.get(index).getCount()));
        holder.txtPrice_Cart_row.setText("$" +
                bill_details_list.get(index).getPrice_Total());

        if(for_BillorCart){
            holder.btnInc_billDetail_row.setVisibility(View.VISIBLE);
            holder.btnRedu_billDetail_row.setVisibility(View.VISIBLE);
        } else {
            //set WeightSum để count sang bên phải
            holder.layoutCount_billDetail_row.setWeightSum(14);
            holder.btnInc_billDetail_row.setVisibility(View.GONE);
            holder.btnRedu_billDetail_row.setVisibility(View.GONE);
        }

        listener.TinhTong(calculate_Total(), bill_details_list.isEmpty());
        //giảm
        holder.btnRedu_billDetail_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bill_details_list.get(index).getCount() == 1){
                    bill_details_list.remove(index);
                    notifyDataSetChanged();
                } else {
                    bill_details_list.get(index).setCount(bill_details_list.get(index).getCount() - 1);
                    calculate_Price(holder, index);
                }
                listener.TinhTong(calculate_Total(), bill_details_list.isEmpty());
            }
        });
        //tăng
        holder.btnInc_billDetail_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill_details_list.get(index).setCount(bill_details_list.get(index).getCount() + 1);
                calculate_Price(holder, index);
                listener.TinhTong(calculate_Total(), false);
            }
        });
    }

    public float calculate_Total(){
        float tong = 0f;
        for(Bill_Details i : bill_details_list)
            tong += i.getPrice_Total();
        return Floor(tong);
    }

    private float Floor(float a){
        return (float) ((float) Math.round(a * 100.0) / 100.0);
    }

    private void calculate_Price(@NonNull BillDetailViewHolder holder, int index){
        DecimalFormat df = new DecimalFormat("##.##");
        Foods foods = FoodFragment.getByID(bill_details_list.get(index).getiD_Food());
        bill_details_list.get(index).setPrice_Total(foods.getPrice_Food()
                *bill_details_list.get(index).getCount());
        holder.txt_count_Cart_row.setText("x" + bill_details_list.get(index).getCount());
        holder.txtPrice_Cart_row.setText("$" +
                Floor(bill_details_list.get(index).getPrice_Total()));
    }

    public void clear_Cart() {
        bill_details_list.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty_Cart(){
        return bill_details_list.isEmpty();
    }

    @Override
    public int getItemCount() {
        return bill_details_list.size();
    }

    public class BillDetailViewHolder extends RecyclerView.ViewHolder{
        ImageView img_Food_BillDetail_row;
        TextView txtName_food_Cart_row, txt_count_Cart_row,
                txtPrice_Cart_row;
        //Redu la giảm, Inc là tăng
        LinearLayout layoutCount_billDetail_row;
        Button btnRedu_billDetail_row, btnInc_billDetail_row;

        public BillDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutCount_billDetail_row = (LinearLayout) itemView.findViewById(R.id.layoutCount_billDetail_row);

            img_Food_BillDetail_row = (ImageView) itemView.findViewById(R.id.img_Food_BillDetail_row);
            txtName_food_Cart_row = (TextView) itemView.findViewById(R.id.txtName_food_Cart_row);
            txt_count_Cart_row = (TextView) itemView.findViewById(R.id.txt_count_Cart_row);
            txtPrice_Cart_row = (TextView) itemView.findViewById(R.id.txtPrice_Cart_row);
            btnInc_billDetail_row = (Button) itemView.findViewById(R.id.btnInc_billDetail_row);
            btnRedu_billDetail_row = (Button) itemView.findViewById(R.id.btnRedu_billDetail_row);
        }
    }
}
