package com.lotusbetaanalytics.lban.guestform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DepartmentReport extends AppCompatActivity {


    private EditText etDepartmentName;
    private EditText etDepartmentNumber;
    private EditText etDepartmentOfficiating;
    private EditText etDepartmentIssue;
    private EditText etDepartmentSolution;
    private EditText etDepartmentRecommendation;
    private TextView etDateTime;

    private Spinner etReasonSpinner;



    Button postDepartmentReport;
    RequestQueue requestQueue;
    String DepartmentReportInsertUrl = "http://hcclekki.org/departmentreportapi.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*Start: Background Date to textview. Texview is invisible*/
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        String strdate = simpleDateFormat.format(newCalendar.getTime());

        TextView txtdate1 = (TextView) findViewById(R.id.newdate);
        txtdate1.setText(strdate);
        /*End: Background Date to textview. Texview is invisible*/

        //Menut button activation
        Button mainmenu = (Button) findViewById(R.id.MenuRegistration);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepartmentReport.this, MainMenu.class);
                startActivity(intent);
            }
        });




        //Assigning my edittext to a new variable for post
        etDepartmentName = (EditText)findViewById(R.id.DepartmentName);
        etDepartmentNumber = (EditText)findViewById(R.id.DepartmentNumber);
        etDepartmentOfficiating = (EditText)findViewById(R.id.DepartmentOfficiating);
        etDepartmentIssue = (EditText)findViewById(R.id.DepartmentIssue);
        etDepartmentSolution = (EditText)findViewById(R.id.DepartmentSolution);
        etDepartmentRecommendation = (EditText)findViewById(R.id.DepartmentRecommendation);

        etDateTime = (TextView) findViewById(R.id.newdate);


        // Spinner to edit spinner to post
        etReasonSpinner = (Spinner) findViewById(R.id.DepartmentOfficialReason);



        postDepartmentReport = (Button)findViewById(R.id.SaveDepartment);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        postDepartmentReport.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {

                final String etReason = etReasonSpinner.getSelectedItem().toString();




                StringRequest request = new StringRequest(Request.Method.POST, DepartmentReportInsertUrl, new Response.Listener<String>() {
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

                        parameters.put("departmentName", etDepartmentName.getText().toString());
                        parameters.put("departmentNumber", etDepartmentNumber.getText().toString());
                        parameters.put("departmentOfficiating", etDepartmentOfficiating.getText().toString());
                        parameters.put("departmentIssue", etDepartmentIssue.getText().toString());
                        parameters.put("departmentSolution", etDepartmentSolution.getText().toString());
                        parameters.put("departmentRecommendation", etDepartmentRecommendation.getText().toString());
                        parameters.put("departmentReason", etReason);
                        parameters.put("date", etDateTime.getText().toString());


                        /*return super.getParams();*/
                        return parameters;


                    }
                };
                requestQueue.add(request);


                Intent intentPost = new Intent(DepartmentReport.this, MainMenu.class);
                startActivity(intentPost);
                Toast.makeText(DepartmentReport.this, "Your Request has been Posted successfully", Toast.LENGTH_LONG).show();
            }


        });








    }

}
