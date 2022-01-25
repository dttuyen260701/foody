package com.example.foody.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;
import com.example.foody.fragment.CartFragment;
import com.example.foody.listeners.Favorite_for_FoodAdapter;
import com.example.foody.listeners.Listener_for_IncAndRedu;
import com.example.foody.listeners.RecyclerView_Item_Listener;
import com.example.foody.models.Bill_Details;
import com.example.foody.models.Favorite;
import com.example.foody.models.Foods;
import com.example.foody.utils.Constant_Values;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Foods> foods_list;
    private Context context;
    private Favorite_for_FoodAdapter listener;
    private static boolean for_Search;
    private RecyclerView_Item_Listener listener_item_Click;
    private Listener_for_IncAndRedu listener_for_incAndRedu;

    public static final int TYPE1= 0;
    public static final int TYPE2= 1;

    public FoodAdapter(ArrayList<Foods> foods_list, Context context, Favorite_for_FoodAdapter listener,
                       RecyclerView_Item_Listener listener_item_Click, Listener_for_IncAndRedu listener_for_incAndRedu) {
        this.foods_list = foods_list;
        this.context = context;
        this.listener = listener;
        this.listener_item_Click = listener_item_Click;
        this.listener_for_incAndRedu =listener_for_incAndRedu;
        for_Search = false;
    }

    public void setFoods_list(ArrayList<Foods> foods_list) {
        this.foods_list = foods_list;
        notifyDataSetChanged();
    }

    public ArrayList<Foods> getFoods_list() {
        return foods_list;
    }

    public static boolean isFor_Search() {
        return for_Search;
    }

    public static void setFor_Search(boolean for_Search) {
        FoodAdapter.for_Search = for_Search;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return (for_Search) ? TYPE2 : TYPE1;
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
                itemView = layoutInflater.inflate(R.layout.first_food_row, parent, false);
                return new FoodViewHolder(itemView);
            default:
                itemView = layoutInflater.inflate(R.layout.food_row, parent, false);
                return new FoodViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int index = (for_Search) ? position : position - 1;
        //khi search thì k có điều kiện, khi không search index > -1 tránh slide là phần tử đầu
        if(!for_Search && index > -1 || for_Search){
            ((FoodViewHolder) holder).bindView(index);
        }
    }

    @Override
    public int getItemCount() {
        return (for_Search) ? foods_list.size() : foods_list.size()+1;
    }

    public void getSearchItem(ArrayList<Foods> list_result){
        foods_list = list_result;
        for_Search = true;
        notifyDataSetChanged();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView imgFood_item;
        ImageView imgFavorite_Food_item;
        TextView txtName_Food_item, txtDescri_Food_item,
                txtPrice_Food_item, txtRate_Food_item,
                txtTime_Cook_Food_item, txt_count_FoodRow;
        Button btnRedu_FoodRow, btnInc_FoodRow;
        ConstraintLayout layout_Food_Row;

        public ImageView getImgFavorite_Food_item() {
            return imgFavorite_Food_item;
        }

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood_item = (ImageView) itemView.findViewById(R.id.imgFood_item);
            imgFavorite_Food_item = (ImageView) itemView.findViewById(R.id.imgFavorite_Review_First_item);

            txtName_Food_item = (TextView) itemView.findViewById(R.id.txtName_Review_First_item);
            txtDescri_Food_item = (TextView) itemView.findViewById(R.id.txtDescri_Food_item);
            txtPrice_Food_item = (TextView) itemView.findViewById(R.id.txtPrice_Review_First_item);
            txtRate_Food_item = (TextView) itemView.findViewById(R.id.txtRate_Food_item);
            txtTime_Cook_Food_item = (TextView) itemView.findViewById(R.id.txtTime_Cook_Review_First_item);
            txt_count_FoodRow = (TextView) itemView.findViewById(R.id.txt_count_FoodRow);

            btnRedu_FoodRow = (Button) itemView.findViewById(R.id.btnRedu_FoodRow);
            btnInc_FoodRow = (Button) itemView.findViewById(R.id.btnInc_FoodRow);

            layout_Food_Row = (ConstraintLayout) itemView.findViewById(R.id.layout_Food_Row);
        }

        public void bindView(int index){
            if(foods_list.get(index).isStatus())
                imgFood_item.setImageBitmap(foods_list.get(index).getImage_Food());
            else
                imgFood_item.setImageResource(R.drawable.sold_out);
            int hinh = (foods_list.get(index).is_Favorite()) ? R.drawable.liked_icon : R.drawable.like_icon;
            txtName_Food_item.setText(foods_list.get(index).getName_Food());
            imgFavorite_Food_item.setImageResource(hinh);
            String descrip = (foods_list.get(index).getDescription_Food().length() > 45)
                    ? String.copyValueOf(foods_list.get(index).getDescription_Food().toCharArray(), 0, 35) + "..."
                    : foods_list.get(index).getDescription_Food();
            txtDescri_Food_item.setText(descrip);

            txtTime_Cook_Food_item.setText(
                    foods_list.get(index).getTime_Cooking() + " min");
            txtPrice_Food_item.setText(
                    "$" +foods_list.get(index).getPrice_Food());
            txtRate_Food_item.setText("" + foods_list.get(index).getRate());

            imgFavorite_Food_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Favorite fav = new Favorite(foods_list.get(index).getiD_Food(), Constant_Values.getIdCus());
                    if(foods_list.get(index).is_Favorite()){
//                            //xóa
                        listener.insert_or_del_Fav(fav, false, imgFavorite_Food_item,
                                R.drawable.like_icon, foods_list.get(index).getiD_Food(), false);
                    } else {
                        //thêm
                        listener.insert_or_del_Fav(fav, true, imgFavorite_Food_item,
                                R.drawable.liked_icon, foods_list.get(index).getiD_Food(), true);
                    }
                }
            });

            Bill_Details bill_details = CartFragment.search_BillDetail_ByIDFood(
                    foods_list.get(index).getiD_Food());
            if(bill_details.getCount() < 1){
                btnRedu_FoodRow.setVisibility(View.GONE);
                txt_count_FoodRow.setText("");
            } else {
                btnRedu_FoodRow.setVisibility(View.VISIBLE);
                txt_count_FoodRow.setText("" + bill_details.getCount());
            }

            btnRedu_FoodRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener_for_incAndRedu.onRedu_Click(foods_list.get(index).getiD_Food());
                    int count = CartFragment.search_BillDetail_ByIDFood(
                            foods_list.get(index).getiD_Food()).getCount();
                    if(count == -1){
                        btnRedu_FoodRow.setVisibility(View.GONE);
                        txt_count_FoodRow.setText("");
                    } else {
                        txt_count_FoodRow.setText("" + (count));
                    }
                }
            });

            btnInc_FoodRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener_for_incAndRedu.onInc_Click(foods_list.get(index).getiD_Food());
                    int count = CartFragment.search_BillDetail_ByIDFood(
                            foods_list.get(index).getiD_Food()).getCount();
                    if(count == 1)
                        btnRedu_FoodRow.setVisibility(View.VISIBLE);
                    txt_count_FoodRow.setText("" + count);
                }
            });

            layout_Food_Row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener_item_Click.onClick(foods_list.get(index).getiD_Food());
                }
            });
        }
    }
}
