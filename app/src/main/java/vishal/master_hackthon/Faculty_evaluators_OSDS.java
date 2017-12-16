package vishal.master_hackthon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static vishal.master_hackthon.Faculty_evaluators_login_activity.PREFS_NAME;
import static vishal.master_hackthon.R.string.email;

public class Faculty_evaluators_OSDS extends AppCompatActivity {

    private String DeanAuthorization;
    private String token = null;
    private RequestQueue registerQueue;
    public static String DeanName, ExamName, ExamDuty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Fetching Data.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        setContentView(R.layout.activity_faculty_evaluators__osds);

        findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Faculty_evaluators_OSDS.this, Payment_Status.class));
            }
        });


        Faculty_evaluators_login_activity logo = new Faculty_evaluators_login_activity();
        final TextView examDuty = (TextView) findViewById(R.id.exam_duty);
        final TextView facultyName = (TextView) findViewById(R.id.faculty_name);
        final Button examName = (Button) findViewById(R.id.exam_name);
        String localhost = getApplicationContext().getResources().getString(R.string.Localhost);


        String email = getIntent().getStringExtra("Email"); //to get data from bundloe stored in previous activity.....
//        Toast.makeText(this,email, Toast.LENGTH_LONG).show();

        if (email != null) {
            SharedPreferences Email = getSharedPreferences("Email_save", MODE_PRIVATE);
            SharedPreferences.Editor editor = Email.edit();
            editor.putString("email", email);
            editor.commit();
        }


        SharedPreferences pref = getSharedPreferences("Email_save", Context.MODE_PRIVATE);
        email = pref.getString("email", "");

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deanEmail", email);
//            jsonObject.put("androidLat", "21.132759");
//            jsonObject.put("androidLng", "72.715848");

            Log.d("Testing", "Inside Try");
        } catch (JSONException e) {
            Log.d("Testing", "Inside Try");
            e.printStackTrace();
        }
        //----------------Login request--------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, localhost + "/backend/facultyActivity", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("response", response.toString());
                        try {
                            Log.d("maliyo_response", response.getString("status"));
                            if (!response.getString("status").equalsIgnoreCase("NoAllocation")) {
                                JSONArray jsonMainNode = response.optJSONArray("status");

                                JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

                                /******* Fetch node values **********/

                                DeanName = (jsonChildNode.optString("DeanName").toString());
                                ExamName = (jsonChildNode.optString("ExamName").toString());
                                ExamDuty = (jsonChildNode.optString("Role").toString());
                                DeanAuthorization = (jsonChildNode.optString("DeanAuthorization").toString()); //totransfer to photo.


                                facultyName.setText(jsonChildNode.optString("DeanName").toString());
                                examName.setText(jsonChildNode.optString("ExamName").toString());
                                examDuty.setText(jsonChildNode.optString("Role").toString());
                                DeanAuthorization = (jsonChildNode.optString("DeanAuthorization").toString());


//                            facultyName.setText(jsonMainNode.optString("deanEmail").toString());
                            }else{
                                finish();
                                Toast.makeText(Faculty_evaluators_OSDS.this, "No Exam center allocated!!", Toast.LENGTH_SHORT).show();
                            }

                            } catch(JSONException e){
                                e.printStackTrace();
                            }
//                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    },new Response.ErrorListener()

                    {
                        @Override
                        public void onErrorResponse (VolleyError error){
                        Toast.makeText(getApplicationContext(), error.toString() + "Error on response", Toast.LENGTH_LONG).show();
                        Log.d("Error", error.toString());
                    }
                    })

                    {
                        @Override
                        public Map<String, String> getHeaders () throws AuthFailureError {
                        Map<String, String> header = new HashMap<String, String>();
                        header.put("Content-Type", "application/json");
                        header.put("X-CSRF-TOKEN", token);
                        return header;
                    }
                    }

                    ;
                    registerQueue =Volley.newRequestQueue(

                    getApplicationContext());
        registerQueue.add(jsonObjectRequest);


                }


        @Override
        public void onBackPressed ()
        {
            Intent i1 = new Intent(Faculty_evaluators_OSDS.this, MainActivity.class);
            startActivity(i1);
        }
// tried to pass to geo tag page
    public void exam_name(View view) {


            final ProgressDialog progressDialog1 = new ProgressDialog(this);

            progressDialog1.setMessage("Checking For Biometric Authentication.....");
            progressDialog1.show();

            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("deanEmail", "smit@gmail.com");

                Log.d("Testing", "Inside Try");
            } catch (JSONException e) {
                Log.d("Testing", "Inside Try");
                e.printStackTrace();
            }
            //----------------Login request--------------------
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            String localhost1 = getApplicationContext().getResources().getString(R.string.Localhost);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, localhost1+"/backend/facultyActivity", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            Log.d("response", response.toString());
                            try {
                                Log.d("mster_response",response.getString("status"));

                                JSONArray jsonMainNode = response.optJSONArray("status");
                                JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

                                /******* Fetch node values **********/
                                DeanName = (jsonChildNode.optString("DeanName").toString());
                                ExamName = (jsonChildNode.optString("ExamName").toString());
//                            examDuty.setText(jsonChildNode.optString("DeanRole").toString());

                                DeanAuthorization = (jsonChildNode.optString("DeanAuthorization").toString());


                                SharedPreferences spe = getSharedPreferences("dean_exam",0);
                                SharedPreferences.Editor editors =spe.edit();
                                editors.putString("DeanName",DeanName);
                                editors.putString("ExamName",ExamName);
                                editors.commit();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            Toast.makeText(getApplicationContext(),DeanName+" "+ExamName, Toast.LENGTH_LONG).show();


                            if(!DeanAuthorization.equals("0")){
                                Intent i = new Intent(Faculty_evaluators_OSDS.this, Faculty_evaluators_count_down.class);
                                i.putExtra("DeanName", DeanName);
                                i.putExtra("ExamName", ExamName);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Biometric not done ",Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getApplicationContext(),DeanAuthorization, Toast.LENGTH_LONG).show();

                            progressDialog1.dismiss();

                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString() + "Error on response", Toast.LENGTH_LONG).show();
                            Log.d("Error", error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<String, String>();
                    header.put("Content-Type","application/json");
                    header.put("X-CSRF-TOKEN", token);
                    return header;
                }
            };
            registerQueue = Volley.newRequestQueue(getApplicationContext());
            registerQueue.add(jsonObjectRequest);


        }



//        Intent i1 = new Intent(Faculty_evaluators_OSDS.this, Geo_tag_exam_conduct.class);
//        startActivity(i1);

    public void logout(View view) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("logged", "logged-out");
        editor.commit();


        Intent logout = new Intent(Faculty_evaluators_OSDS.this, Faculty_evaluators_login_activity.class);
        startActivity(logout);
    }
}
