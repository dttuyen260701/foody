package com.example.foody.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foody.R;
import com.example.foody.adapters.FoodAdapter;
import com.example.foody.asyntask.Load_Favorite_Asynctask;
import com.example.foody.asyntask.Load_Food_Asynctask;
import com.example.foody.asyntask.Load_Reviews_Asynctask;
import com.example.foody.asyntask.InsertOrDelOrUpdate_Asynctask;
import com.example.foody.listeners.Favorite_for_FoodAdapter;
import com.example.foody.listeners.Check_task_listener;
import com.example.foody.listeners.Listener_for_BackFragment;
import com.example.foody.listeners.Listener_for_IncAndRedu;
import com.example.foody.listeners.Load_Data_Listener;
import com.example.foody.listeners.Load_Favorite_Listener;
import com.example.foody.listeners.Load_Reviews_Listener;
import com.example.foody.listeners.RecyclerView_Item_Listener;
import com.example.foody.models.*;
import com.example.foody.utils.Methods;
import com.example.foody.utils.Constant_Values;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.RequestBody;

public class FoodFragment extends Fragment {
    //xem có ng mới đăng nhập hay k
    private static boolean check_NewCus = true;

    private RecyclerView recycler_Food;
    private SearchView search_FoodView;
    private ProgressBar progressBar_load;
    private SwipeRefreshLayout swipRefesh_Food_Frag;
    private Methods methods;
    private static ArrayList<Foods> list_Foods_All;
    private static ArrayList<Foods> list_Foods;//hiển thị
    private static ArrayList<Favorite> list_Favorite;
    private static ArrayList<Bitmap> list_img_slide;
    private FoodAdapter adapter;
    private boolean check_In_Dev_Fav = false;

    private String search_query = "";

    private Favorite_for_FoodAdapter listener_favorite = new Favorite_for_FoodAdapter() {
        //true: insert
        //false: del
        @Override
        public void insert_or_del_Fav(Favorite favo, boolean insert_or_del,
                                      ImageView imgLike, int src, int ID_Food, boolean value) {
            if(Constant_Values.getIdCus() != -1){
                Check_task_listener listener = new Check_task_listener() {
                    @Override
                    public void onPre() {
                        if (!methods.isNetworkConnected()) {
                            Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onEnd(boolean isSucces, boolean insert_Success) {
                        if (isSucces) {
                            imgLike.setImageResource(src);
                            for(int i = 0; i < list_Foods.size(); ++i){
                                if(list_Foods.get(i).getiD_Food() == ID_Food){
                                    list_Foods.get(i).set_Favorite(value);
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Bundle bundle = new Bundle();
                bundle.putInt("ID_Food", favo.getiD_Food());
                bundle.putInt("ID_Cus", favo.getiD_Cus());
                String method_name = (insert_or_del) ? "method_insert_favorite" : "method_del_favorite";
                RequestBody requestBody = methods.getRequestBody(method_name, bundle);
                InsertOrDelOrUpdate_Asynctask asynctask = new InsertOrDelOrUpdate_Asynctask(listener, requestBody,
                        Constant_Values.URL_FAVORITE_API);
                asynctask.execute();
            } else
                Toast.makeText(getActivity(), "Please login to add favorite list", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        SetUp(view);
        if(list_Foods.isEmpty() || check_NewCus){
            load_Food_Data();
            check_NewCus = false;
        }

        adapter = new FoodAdapter(list_img_slide, list_Foods, view.getContext(), listener_favorite
            , new RecyclerView_Item_Listener() {
            @Override
            public void onClick(int ID_Food) {
                load_Review_data(getByID(ID_Food));
            }
        }, new Listener_for_IncAndRedu() {
            @Override
            public void onRedu_Click(int ID_Food) {
                Redu_Click(ID_Food);
            }

            @Override
            public void onInc_Click(int ID_Food) {
                Inc_Click(ID_Food);
            }
        });
        recycler_Food.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_Food.setAdapter(adapter);
        return view;
    }

    public static Foods getByID(int IDFood){
        for(Foods i : list_Foods_All)
            if(i.getiD_Food() == IDFood)
                return i;
        return new Foods();
    }

    public static void setCheck_NewCus(boolean check_NewCus) {
        FoodFragment.check_NewCus = check_NewCus;
    }

    private void SetUp(View view){
        recycler_Food = (RecyclerView) view.findViewById(R.id.recycler_Food);
        swipRefesh_Food_Frag = (SwipeRefreshLayout) view.findViewById(R.id.swipRefesh_Food_Frag);
        swipRefesh_Food_Frag.setColorSchemeColors(getResources().getColor(R.color.app_themes));
        swipRefesh_Food_Frag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list_Foods.clear();
                adapter.notifyDataSetChanged();
                //khóa double load
                swipRefesh_Food_Frag.setEnabled(false);
                load_Food_Data();
                swipRefesh_Food_Frag.setRefreshing(false);
            }
        });

        search_FoodView = (SearchView) view.findViewById(R.id.search_FoodView);
        progressBar_load = (ProgressBar) view.findViewById(R.id.progressBar_Food_Frag);
        methods = new Methods(view.getContext());
        search_FoodView = (SearchView) view.findViewById(R.id.search_FoodView);
        search_FoodView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                swipRefesh_Food_Frag.setEnabled(false);
                search(newText);
                if(newText.length() == 0){
                    adapter.setFoods_list(list_Foods);
                    swipRefesh_Food_Frag.setEnabled(true);
                    adapter.setFor_Search(false);
                }
                return false;
            }
        });
        if(list_img_slide == null)
            list_img_slide = new ArrayList<>();
        if(list_Foods == null)
            list_Foods = new ArrayList<>();
        if(list_Favorite == null)
            list_Favorite = new ArrayList<>();
        if(list_Foods_All == null)
            list_Foods_All = new ArrayList<>();
    }

    private void back_to_FoodFragment(ReviewsFragment reviewsFragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, reviewsFragment);
        transaction.addToBackStack("Food");
        transaction.commit();
    }

    private void search(String text){
        ArrayList<Foods> list_search = new ArrayList<>();
        for(Foods i : list_Foods){
            if(i.getName_Food().toLowerCase().contains(text.toLowerCase()))
                list_search.add(i);
        }
        if(list_search.isEmpty()) {
            if (text.length() > 0)
                Toast.makeText(getActivity(), "No Foods Founds", Toast.LENGTH_SHORT).show();
        } else {
            adapter.getSearchItem(list_search);
        }
    }

    private void load_Favorite_data(){
        Load_Favorite_Listener listener = new Load_Favorite_Listener() {
            @Override
            public void onEnd(boolean isDone, ArrayList<Favorite> list_Fav) {
                list_Favorite.addAll(list_Fav);
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Cus", Constant_Values.getIdCus());
        RequestBody requestBody = methods.getRequestBody("method_get_favorite_data", bundle);
        Load_Favorite_Asynctask asynctask = new Load_Favorite_Asynctask(listener, requestBody);
        asynctask.execute();
    }

    private void load_Food_Data(){
        list_Favorite.clear();
        list_Foods.clear();
        Load_Data_Listener listener = new Load_Data_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                load_Favorite_data();
                progressBar_load.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSussed, ArrayList<Foods> list_result) {
                progressBar_load.setVisibility(View.GONE);
                if(isSussed){
                    list_Foods_All.addAll(list_result);
                    for(Foods i : list_result)
                        if(i.is_Available())
                            list_Foods.add(i);
                    for(Foods i : list_Foods) {
                        for (Favorite k : list_Favorite)
                            if (i.getiD_Food() == k.getiD_Food())
                                i.set_Favorite(true);
                    }
                    swipRefesh_Food_Frag.setEnabled(true);
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        RequestBody requestBody = methods.getRequestBody("method_get_food_data", null);
        Load_Food_Asynctask asyntask = new Load_Food_Asynctask(listener, requestBody);
        asyntask.execute();
    }

    private void load_Review_data(Foods foods){
        Load_Reviews_Listener listener_load_review = new Load_Reviews_Listener() {
            @Override
            public void onPre() {
                if (!methods.isNetworkConnected()) {
                    Toast.makeText(getActivity(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                progressBar_load.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSussec, ArrayList<Reviews> list_result) {
                progressBar_load.setVisibility(View.GONE);
                if(isSussec){
                    Collections.reverse(list_result);//đảo ngược chuỗi
                    ReviewsFragment reviewsFragment = new ReviewsFragment(foods, list_result, new Listener_for_BackFragment() {
                        @Override
                        public void orderBill_Or_BackFragment() {
                            getFragmentManager().popBackStack();
                        }
                    }, new Listener_for_IncAndRedu() {
                        @Override
                        public void onRedu_Click(int ID_Food) {
                            Redu_Click(ID_Food);
                        }

                        @Override
                        public void onInc_Click(int ID_Food) {
                            Inc_Click(ID_Food);
                        }
                    }, listener_favorite);
                    back_to_FoodFragment(reviewsFragment);
                } else
                    Toast.makeText(getActivity(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Bundle bundle = new Bundle();
        bundle.putInt("ID_Food", foods.getiD_Food());
        RequestBody requestBody = methods.getRequestBody("method_get_reviews_data", bundle);
        Load_Reviews_Asynctask asynctask = new Load_Reviews_Asynctask(listener_load_review, requestBody);
        asynctask.execute();
    }

    private void Redu_Click(int ID_Food){
        int count_old = CartFragment.search_BillDetail_ByIDFood(ID_Food).getCount();
        if(count_old == 1){
            CartFragment.remove_from_Cart(ID_Food);
        } else {
            CartFragment.search_BillDetail_ByIDFood(ID_Food).setCount(count_old - 1);
        }
    }

    private void Inc_Click(int ID_Food){
        int count_old = CartFragment.search_BillDetail_ByIDFood(ID_Food).getCount();
        if(count_old == -1){
            Bill_Details bill_details_new = new Bill_Details(CartFragment.getID_Bill_New(),
                    ID_Food, 1, getByID(ID_Food).getPrice_Food(),
                    0.0f, "", false);
            CartFragment.add_to_Cart(bill_details_new);
        } else {
            CartFragment.search_BillDetail_ByIDFood(ID_Food).setCount(count_old + 1);
        }
    }
}