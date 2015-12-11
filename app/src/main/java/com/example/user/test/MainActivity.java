package com.example.user.test;
import android.annotation.TargetApi;
import android.app.Activity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaMetadata;
import android.media.MediaMetadataEditor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.media.RemoteController;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.media.MediaMetadataEditor;
import android.os.Environment;
import android.os.RemoteCallbackList;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import static android.media.RemoteControlClient.*;

public class MainActivity extends Activity {

    public static int VIDEO_CAPTURED = 1;
    private EditText  username=null;
    private EditText  password=null;
    private TextView attempts;
    private Button login;
    int counter = 3;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
        attempts = (TextView)findViewById(R.id.textView5);
        attempts.setText(Integer.toString(counter));
        login = (Button)findViewById(R.id.button1);
    }

    public void login(View view){
        if(username.getText().toString().equals("ad") &&
                password.getText().toString().equals("ad")){

            //Intent captureVideoIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
            Intent captureVideoIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(captureVideoIntent, VIDEO_CAPTURED);






        }

        else{
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            attempts.setBackgroundColor(Color.RED);
            counter--;
            attempts.setText(Integer.toString(counter));
            if(counter==0)
                login.setEnabled(false);
            }
}

            protected void onActivityResult( int requestCode,int resultCode,Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            try {
                Uri videoFileUri = data.getData();
              LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
                Criteria c=new Criteria();
                //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                String provider=lm.getBestProvider(c, false);
                //now you have best provider
                //get location
                String lng,lat;
               Location l=lm.getLastKnownLocation(provider);
                if(l!=null)
                {
                    //get latitude and longitude of the location
                     lng=String.valueOf(l.getLongitude());
                     lat=String.valueOf(l.getLatitude());

                }
                else
                {
                    lat="10.7610900";
                    lng="78.8139290";
                }
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
               // dlgAlert.setMessage("hii");
                dlgAlert.setMessage("Longitude      "+lng+"            Latitude "+lat+"          Date-Time  "+currentDateTimeString);
                dlgAlert.setTitle("Location");
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

               /* ContentValues values = new ContentValues(1);
                values.put(MediaStore.Video.VideoColumns.LATITUDE,"111");
                values.put(MediaStore.Video.VideoColumns.LONGITUDE,"123");
                if (getContentResolver().update(videoFileUri, values, null, null) == 1) {
              Toast t = Toast.makeText(this,"Updated",Toast.LENGTH_SHORT);
                    t.show();

                } else {

                    Toast t = Toast.makeText(this, "Error", Toast.LENGTH_SHORT);
                    t.show();
                }*/



            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

   @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

    }