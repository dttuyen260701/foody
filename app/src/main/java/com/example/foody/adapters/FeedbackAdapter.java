package com.example.foody.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.fragment.FoodFragment;
import com.example.foody.models.Bill_Details;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedBackViewHolder> {
    private ArrayList<Bill_Details> list_bill_detail;
    private Context context;

    public FeedbackAdapter(ArrayList<Bill_Details> list_bill_detail, Context context) {
        this.list_bill_detail = list_bill_detail;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedbackAdapter.FeedBackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.feedback_row, parent, false);
        return new FeedBackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.FeedBackViewHolder holder, int position) {
        int index = position;
        holder.imgFood_feedback_row.setImageBitmap(
                FoodFragment.getByID(list_bill_detail.get(index).getiD_Food()).getImage_Food());
        holder.txtFoodName_feedback_row.setText(
                FoodFragment.getByID(list_bill_detail.get(index).getiD_Food()).getName_Food());
        holder.ratingBar_feedback_row.setRating(list_bill_detail.get(index).getRate());

        String review_text = (list_bill_detail.get(index).getReviews().length() > 0)
                ? list_bill_detail.get(index).getReviews() : " ";
        holder.txtfeedback_row.setText(review_text);

        holder.ratingBar_feedback_row.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                list_bill_detail.get(index).setRate(rating);
            }
        });

        holder.txtfeedback_row.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list_bill_detail.get(index).setReviews(s.toString() + "");
            }
        });

        if(list_bill_detail.get(index).getRate() >= 1.0f){
            //không cho người dùng đánh giá lại
            holder.ratingBar_feedback_row.setIsIndicator(true);
            holder.txtfeedback_row.setEnabled(false);
        } else {
            //cho người dùng đánh giá
            holder.ratingBar_feedback_row.setIsIndicator(false);
            holder.txtfeedback_row.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return list_bill_detail.size();
    }


    public class FeedBackViewHolder extends RecyclerView.ViewHolder{
        ImageView imgFood_feedback_row;
        TextView txtFoodName_feedback_row;
        RatingBar ratingBar_feedback_row;
        EditText txtfeedback_row;

        public FeedBackViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood_feedback_row = (ImageView) itemView.findViewById(R.id.imgFood_feedback_row);
            txtFoodName_feedback_row = (TextView) itemView.findViewById(R.id.txtFoodName_feedback_row);
            ratingBar_feedback_row = (RatingBar) itemView.findViewById(R.id.ratingBar_feedback_row);
            txtfeedback_row = (EditText) itemView.findViewById(R.id.txtfeedback_row);
        }
    }
}
