package com.ibm.pingpong3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    // camera tut;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static String EXTRA_PHOTO = "com.example.myfirstapp.PHOTO";

    // http://stackoverflow.com/questions/2729267/android-camera-intent
    //private Uri imageUri;
    public static Uri imageUri;

    // http://stackoverflow.com/questions/34761844/error-on-upgrading-from-api-22-to-23-filenotfoundexception-file-full-path
    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button

        // create activity DisplayMessageActivity (defined by me);
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        // get contents of text box;
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        // add a key-value pair to the intent;
        intent.putExtra(EXTRA_MESSAGE, message);

        // start activity;
        startActivity(intent);
    }

    // camera tut;
    public void dispatchTakePictureIntent(View view) {

        // get permission from user
        if((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))

        {
            ActivityCompat.requestPermissions
                    (MainActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // http://stackoverflow.com/questions/2729267/android-camera-intent
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            // adding this extra breaks the data.getExtras() call below;
        imageUri = Uri.fromFile(photo);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //ImageView mImageView = new ImageView(this);
            //setContentView(R.layout.pic_display);
            //ImageView mImageView = (ImageView) findViewById(R.id.camera_pic);

            // http://stackoverflow.com/questions/2729267/android-camera-intent
            // inform the image uri of the update;
            getContentResolver().notifyChange(imageUri, null);

            // create activity;
            Intent intent = new Intent(this, DisplayPhotoActivity.class);

            // get picture content to pass to activity;
            // this works if we pass the message with intents;
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");

            /*
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

            // add a key-value pair to the intent;
            //intent.putExtra(EXTRA_PHOTO, imageBitmap);

            // rather than pass the bitmap, pass the uri?

            // start activity;
            startActivity(intent);

            //mImageView.setImageBitmap(imageBitmap);
        }
    }
}
