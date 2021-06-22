package com.example.testcamera;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testcamera.adapter.ImageAdapter;
import com.example.testcamera.camera.Camera;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Camera camera;

    List<Bitmap> images;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        images = new ArrayList<>();

        GridView gridview = findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this, images);
        gridview.setAdapter(imageAdapter);

    }

    void takePicture(Activity context) {
        camera = new Camera.Builder()
                .setDirectory("pics")
                .setName("IMG" + System.currentTimeMillis())
                .setImageFormat(Camera.ImageFormat.JPEG)
                .setCompression(75)
                .resetToCorrectOrientation(true)
                .setImageHeight(1000)
                .build(context);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap image = camera.getCameraBitmap();
            if (image != null) {
                images.add(image);
                imageAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this.getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void saveImage(Bitmap bitmap) {
        try {
            ContentResolver contentResolver = getContentResolver();

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG" + System.currentTimeMillis() + ".jpg");
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

            Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            FileOutputStream fos = (FileOutputStream) contentResolver.openOutputStream(Objects.requireNonNull(imageUri));

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Failed to save image!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!camera.deleteImage())
            Toast.makeText(this, "Failed to delete image", Toast.LENGTH_SHORT).show();
    }

    public void takePicture(View view) {
        takePicture(this);
    }

    public void savePicture(View view) {
        for (Bitmap image : images)
            saveImage(image);
        if (images.size() > 0)
            Toast.makeText(this, "Images Saved Successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No Image to Save", Toast.LENGTH_SHORT).show();

    }
}
