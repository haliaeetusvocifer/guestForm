package com.lotusbetaanalytics.lban.guestform;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class GuestForm extends AppCompatActivity {


    private EditText etFirstName;
    private EditText etMiddleName;
    private EditText etLastName;
    private EditText etAddress;
    private EditText etState;
    private EditText etEmail;
    private EditText etPhoneNumber;
    private EditText etBeNotified;
    private EditText etFacebookName;
    private EditText etTwitterName;
    private EditText etImportantThing;
    private EditText etFirstImpression;
    private EditText etDislike;

    private Spinner etGenderSpinner, etMaritalSpinner, etAgeSpinner, etChildrenSpinner, etHowDidYouHearSpinner;


    private TextView etDateTime;


    Button postGuestRegistration;
    RequestQueue requestQueue;
    String GuestRegistrationInsertUrl = "http://hcclekki.org/firsttimerapi.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Menut button activation
        Button mainmenu = (Button) findViewById(R.id.MenuRegistration);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestForm.this, MainMenu.class);
                startActivity(intent);
            }
        });


        /*Start: Background Date to textview. Texview is invisible*/
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        String strdate = simpleDateFormat.format(newCalendar.getTime());

        TextView txtdate1 = (TextView) findViewById(R.id.newdate);
        txtdate1.setText(strdate);
        /*End: Background Date to textview. Texview is invisible*/






        //Assigning my edittext to a new variable for post
        etFirstName = (EditText)findViewById(R.id.FirstName);
        etMiddleName = (EditText)findViewById(R.id.MiddleName);
        etLastName = (EditText)findViewById(R.id.LastName);
        etAddress = (EditText)findViewById(R.id.address);
        etState = (EditText)findViewById(R.id.state);
        etEmail = (EditText)findViewById(R.id.Email);
        etPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        etBeNotified = (EditText)findViewById(R.id.BeNotified);
        etFacebookName = (EditText)findViewById(R.id.FacebookName);
        etTwitterName = (EditText)findViewById(R.id.TwitterName);
        etImportantThing = (EditText)findViewById(R.id.ImportantThing);
        etFirstImpression = (EditText)findViewById(R.id.FirstImpression);
        etDislike = (EditText)findViewById(R.id.Dislike);
        etDateTime = (TextView) findViewById(R.id.newdate);


        // Spinner to edit spinner to post
        etGenderSpinner = (Spinner) findViewById(R.id.GenderSpin);
        etMaritalSpinner = (Spinner)findViewById(R.id.MaritalSpin);
        etAgeSpinner = (Spinner)findViewById(R.id.AgeGroupSpin);
        etChildrenSpinner = (Spinner)findViewById(R.id.ChildrenSpin);
        etHowDidYouHearSpinner = (Spinner)findViewById(R.id.HowDidYouHearSpin);



        postGuestRegistration = (Button)findViewById(R.id.SaveRegistration);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        postGuestRegistration.setOnClickListener(new View.OnClickListener() {
            

            public void onClick(View view) {

                final String etGender = etGenderSpinner.getSelectedItem().toString();
                final String etMarital = etMaritalSpinner.getSelectedItem().toString();
                final String etAgegroup = etAgeSpinner.getSelectedItem().toString();
                final String etChildren = etChildrenSpinner.getSelectedItem().toString();
                final String etHowDidYouHear = etHowDidYouHearSpinner.getSelectedItem().toString();






                StringRequest request = new StringRequest(Request.Method.POST, GuestRegistrationInsertUrl, new Response.Listener<String>() {
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


                        Map<String, String> parameters = new HashMap<String, String>();

                        parameters.put("firstName", etFirstName.getText().toString());
                        parameters.put("middleInitial", etMiddleName.getText().toString());
                        parameters.put("lastname", etLastName.getText().toString());
                        parameters.put("address", etAddress.getText().toString());
                        parameters.put("state", etState.getText().toString());
                        parameters.put("emailAddress", etEmail.getText().toString());
                        parameters.put("phoneNumber", etPhoneNumber.getText().toString());
                        parameters.put("status", etMarital);
                        parameters.put("gender", etGender);
                        parameters.put("ageGroup", etAgegroup);
                        parameters.put("numberOfChildren", etChildren);
                        parameters.put("beNotified", etBeNotified.getText().toString());
                        parameters.put("facebookName", etFacebookName.getText().toString());
                        parameters.put("twitterHandle", etTwitterName.getText().toString());
                        parameters.put("howDidYouHear", etHowDidYouHear);
                        parameters.put("importantThing", etImportantThing.getText().toString());
                        parameters.put("firstImpression", etFirstImpression.getText().toString());
                        parameters.put("dislike", etDislike.getText().toString());
                        parameters.put("date", etDateTime.getText().toString());


                        /*return super.getParams();*/
                        return parameters;


                    }
                };
                requestQueue.add(request);


                Intent intentPost = new Intent(GuestForm.this, MainMenu.class);
                startActivity(intentPost);
                Toast.makeText(GuestForm.this, "Your Request has been Posted successfully", Toast.LENGTH_LONG).show();
            }


        });





    }












}
