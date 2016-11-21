package com.lotusbetaanalytics.lban.guestform;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Biometrics extends AppCompatActivity {

    private TextView etFirstName;
    private TextView etMiddleName;
    private TextView etLastName;
    private TextView etAddress;

    public TextView showFirstName, showMiddleName, showLastName, showAddress;



    ImageView takenPicture;
    ImageView signShow;
    Bitmap bm;

    private static final int CAM_REQUEST = 1313;

    Button postRegistration;
    RequestQueue requestQueue;
    String registrationInsertUrl = "http://hcclekki.org/firsttimerapi.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometrics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        if(getIntent().hasExtra("Bitmap")) {

            ImageView signedImage = (ImageView) findViewById(R.id.signature);
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("Bitmap"), 0, getIntent().getByteArrayExtra("Bitmap").length
            );
            signedImage.setImageBitmap(b);
        }




        //Initialize TextViews for driverInfo
        initializeViews();

        //get the Intent that started this Activity
        Intent driverIntent = getIntent();

        //get the Bundle that stores the data of this Activity
        Bundle driverBundle = driverIntent.getExtras();

        //getting data from bundle
        String clientNameString = driverBundle.getString("infoFirstName");
        String detailsString = driverBundle.getString("infoMiddleName");
        String KmInString = driverBundle.getString("infoLastName");
        String TimeInString = driverBundle.getString("infoAddress");

        //show data to textview
        showFirstName.setText(clientNameString);
        showMiddleName.setText(detailsString);
        showLastName.setText(KmInString);
        showAddress.setText(TimeInString);




       /* etFirstName = (TextView) findViewById(R.id.clientcompanyname);
        etMiddleName = (TextView) findViewById(R.id.detail);
        etLastName = (TextView) findViewById(R.id.kmin);
        etAddress = (TextView) findViewById(R.id.timein);*/






        /*Signature Pad Pop up window*/
        signShow = (ImageView)findViewById(R.id.signature);
        signShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent signIntent = new Intent(Biometrics.this, Pop.class);
                startActivity(signIntent);

            }
        });


        /*Camera/ picture button*/
        takenPicture= (ImageView) findViewById(R.id.pictureTaken);
        takenPicture.setOnClickListener(new takeButtonClicker());



        /*Psoting to DB using Volley*/
        postRegistration = (Button)findViewById(R.id.SaveBiometrics);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        postRegistration.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                takenPicture = (ImageView) findViewById(R.id.pictureTaken);
                BitmapDrawable pictureDrawable = (BitmapDrawable) takenPicture.getDrawable();
                Bitmap pictureBitmap = pictureDrawable.getBitmap();
                ByteArrayOutputStream boasPicture = new ByteArrayOutputStream();
                pictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, boasPicture);
                byte[] pp = boasPicture.toByteArray();
                final String pictureSnap = Base64.encodeToString(pp, Base64.DEFAULT);


                signShow = (ImageView)findViewById(R.id.signature);
                BitmapDrawable drawable = (BitmapDrawable) signShow.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                byte[] bb = bos.toByteArray();
                final String signImage = Base64.encodeToString(bb, 1);


                StringRequest request = new StringRequest(Request.Method.POST, registrationInsertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {



                        //get data from textview
                        final String firstName =showFirstName.getText().toString();
                        final String middleName = showMiddleName.getText().toString();
                        final String lastName = showLastName.getText().toString();
                        final String address = showAddress.getText().toString();


                        Map<String, String> parameters = new HashMap<String, String>();


                        parameters.put("firstName",firstName);
                        parameters.put("middleInitial",middleName);
                        parameters.put("lastname", lastName);
                        parameters.put("address", address);
                        parameters.put("picture", pictureSnap.toString());
                        parameters.put("signature", signImage.toString());






                        /*return super.getParams();*/
                        return parameters;


                    }
                };
                requestQueue.add(request);


                Intent intentPost = new Intent(Biometrics.this, MainMenu.class);
                startActivity(intentPost);
                Toast.makeText(Biometrics.this, "Your Request has been Posted successfully", Toast.LENGTH_LONG).show();
            }


        });





    }




    public void initializeViews(){

        showFirstName = (TextView)findViewById(R.id.clientcompanyname);
        showMiddleName = (TextView)findViewById(R.id.detail);
        showLastName = (TextView)findViewById(R.id.kmin);
        showAddress = (TextView)findViewById(R.id.timein);
    }



    class takeButtonClicker implements Button.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraintent, CAM_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST)
        {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            takenPicture.setImageBitmap(thumbnail);
        }

    }


}
