package com.lotusbetaanalytics.lban.guestform;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by haziz on 3/17/2016.
 */
public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Start:Code for popup windows*/
        setContentView(R.layout.signaturepop);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        /*End:Code for popup windows*/

    }

    public void saveSig(View view) {

        try {

            GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);

            gestureView.setDrawingCacheEnabled(true);

            Bitmap bm = Bitmap.createBitmap(gestureView.getDrawingCache());

            //code to pass image to intent
            /*ByteArrayOutputStream bstream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bstream);
            byte[] byteArray = bstream.toByteArray();
            Intent signIntent = new Intent();
            signIntent.setClass(Biometric.this, Register.class);
            signIntent.putExtra("Bitmap", byteArray);
            startActivity(signIntent);
            finish();*/
            //end of code


            File f = new File(Environment.getExternalStorageDirectory()

                    + File.separator + "signature.png");

            f.createNewFile();

            FileOutputStream os;

            os = new FileOutputStream(f);

            //compress to specified format (PNG), quality - which is ignored for PNG, and out stream

            bm.compress(Bitmap.CompressFormat.PNG, 100, os);


            Intent signIntent = new Intent();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,50,bs);
            signIntent.setClass(Pop.this, GuestForm.class);
            signIntent.putExtra("Bitmap", bs.toByteArray());
            startActivity(signIntent);

            os.close();
            //ImageView signing = (ImageView)findViewById(R.id.signature);
            //signing.setImageBitmap(bm);








        } catch (Exception e) {

            Log.w("Gestures", e.getMessage());

            e.printStackTrace();

        }

    }



}
