package com.example.foody.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.fragment.FoodFragment;
import com.example.foody.listeners.Favorite_for_FoodAdapter;
import com.example.foody.models.Favorite;
import com.example.foody.models.Foods;
import com.example.foody.models.Reviews;
import com.example.foody.utils.Constant_Values;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter {

    private Foods food_fist_item;
    private Context context;
    private ArrayList<Reviews> list_reviews;
    private Favorite_for_FoodAdapter listener_favorite;

    public static final int TYPE1= 0;
    public static final int TYPE2= 1;

    public ReviewAdapter(Foods food_fist_item, Context context, ArrayList<Reviews> list_reviews,
                         Favorite_for_FoodAdapter listener_favorite) {
        this.food_fist_item = food_fist_item;
        this.context = context;
        this.list_reviews = list_reviews;
        this.listener_favorite = listener_favorite;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE1;
        else
            return TYPE2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View itemView = null;
        switch (viewType){
            case TYPE1:
                itemView = layoutInflater.inflate(R.layout.first_review_row, parent, false);
                return new ReviewAdapter.FirstItemViewHolder(itemView);
            default:
                itemView = layoutInflater.inflate(R.layout.review_row, parent, false);
                return new ReviewAdapter.ReviewViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            ((FirstItemViewHolder) holder).bindView();
        }
        else{
            ((ReviewViewHolder) holder).bindView(position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return list_reviews.size() + 1;
    }

    class FirstItemViewHolder extends RecyclerView.ViewHolder{
        ImageView img_Food_reivew_Frag, imgFavorite_Review_First_item;
        TextView txtName_Review_First_item, txtPrice_Review_First_item, txtTime_Cook_Review_First_item;
        RatingBar ratingBar_review_first_row;

        public FirstItemViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Food_reivew_Frag = (ImageView) itemView.findViewById(R.id.img_Food_reivew_Frag);
            imgFavorite_Review_First_item = (ImageView) itemView.findViewById(R.id.imgFavorite_Review_First_item);

            txtName_Review_First_item = (TextView) itemView.findViewById(R.id.txtName_Review_First_item);
            txtPrice_Review_First_item = (TextView) itemView.findViewById(R.id.txtPrice_Review_First_item);
            txtTime_Cook_Review_First_item = (TextView) itemView.findViewById(R.id.txtTime_Cook_Review_First_item);

            ratingBar_review_first_row = (RatingBar) itemView.findViewById(R.id.ratingBar_review_first_row);
        }

        //gán view
        public void bindView(){
           img_Food_reivew_Frag.setImageBitmap(food_fist_item.getImage_Food());
           int src_Favo = (food_fist_item.is_Favorite()) ? R.drawable.liked_icon : R.drawable.like_icon;
           imgFavorite_Review_First_item.setImageResource(src_Favo);

           txtName_Review_First_item.setText(food_fist_item.getName_Food());
           txtPrice_Review_First_item.setText("$" + food_fist_item.getPrice_Food());
           txtTime_Cook_Review_First_item.setText(food_fist_item.getTime_Cooking() + "min");

           ratingBar_review_first_row.setRating(food_fist_item.getRate());

           imgFavorite_Review_First_item.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Favorite fav = new Favorite(food_fist_item.getiD_Food(), Constant_Values.getIdCus());
                   if(food_fist_item.is_Favorite()){
//                            //xóa
                       listener_favorite.insert_or_del_Fav(fav, false, imgFavorite_Review_First_item,
                               R.drawable.like_icon, food_fist_item.getiD_Food(),false);
                   } else {
                       //thêm
                       listener_favorite.insert_or_del_Fav(fav, true, imgFavorite_Review_First_item,
                               R.drawable.liked_icon, food_fist_item.getiD_Food(),true);
                   }
               }
           });
        }
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameUser_Review_row, txtTime_Riview_row,
                txtReview_Review_row;
        RatingBar ratingBar_Review_row;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameUser_Review_row = (TextView) itemView.findViewById(R.id.txtNameUser_Review_row);
            txtTime_Riview_row = (TextView) itemView.findViewById(R.id.txtTime_Riview_row);
            txtReview_Review_row = (TextView) itemView.findViewById(R.id.txtReview_Review_row);

            ratingBar_Review_row = (RatingBar) itemView.findViewById(R.id.ratingBar_Review_row);
        }

        //gán view
        public void bindView(int position){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            txtNameUser_Review_row.setText(list_reviews.get(position).getName_Cus());
            txtTime_Riview_row.setText(dateFormat.format(list_reviews.get(position).getTime()));
            txtReview_Review_row.setText(list_reviews.get(position).getReviews());

            ratingBar_Review_row.setRating(list_reviews.get(position).getRate());
        }
    }
}
