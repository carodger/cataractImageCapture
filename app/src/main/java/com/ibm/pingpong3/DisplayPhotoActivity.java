package com.ibm.pingpong3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class DisplayPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // rather than read bitmap from extras, get it from main image uri
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), MainActivity.imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.pic_display);

        /*
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_PHOTO);
        //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra(MainActivity.EXTRA_PHOTO);
        //TextView textView = new TextView(this);
        */

        ImageView imageView = new ImageView(this);
        //textView.setTextSize(40);
        //textView.setText(message);
        imageView.setImageBitmap(bitmap);
        //imageView.setScaleType()


        ViewGroup layout = (ViewGroup) findViewById(R.id.pic_display);
        layout.addView(imageView);
    }
}
