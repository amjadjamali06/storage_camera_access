package com.example.testcamera.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testcamera.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private final Context mContext;
    List<Bitmap> images;

    // Constructor
    public ImageAdapter(Context c, List<Bitmap> images) {
        mContext = c;
        this.images = images;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_image, null);
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {

//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


//        convertView =  inflater.inflate(R.layout.grid_image, parent);
        ImageView imageView;

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(320, 320));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
//            Toast.makeText(mContext, "View Null", Toast.LENGTH_SHORT).show();

        imageView.setImageBitmap(images.get(position));



        return imageView;
    }
}