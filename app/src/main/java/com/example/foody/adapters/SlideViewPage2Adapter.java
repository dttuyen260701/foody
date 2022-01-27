package com.example.foody.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody.R;

import java.util.ArrayList;

public class SlideViewPage2Adapter extends
        RecyclerView.Adapter<SlideViewPage2Adapter.PhotoViewHolder> {
    private ArrayList<Bitmap> list_Img;

    public SlideViewPage2Adapter(ArrayList<Bitmap> list_Img) {
        this.list_Img = list_Img;
    }

    @NonNull
    @Override
    public SlideViewPage2Adapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.slide_item, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewPage2Adapter.PhotoViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return list_Img.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        ImageView img_Slide_item;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Slide_item = (ImageView) itemView.findViewById(R.id.img_Slide_item);
        }

        public void bindView(int index){
            img_Slide_item.setImageBitmap(list_Img.get(index));
        }
    }
}
