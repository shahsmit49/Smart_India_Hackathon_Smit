package vishal.master_hackthon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



/**
 * A login screen that offers login via email/password.
 */
public class Faculty_evaluators_login_activity extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;
    private EditText mEmailView;
    private EditText mPasswordView;
    private RequestQueue registerQueue;
    private View mProgressView;
    private View mLoginFormView;
    private String KEY_EMAIL = "email";
    private String KEY_PASSWORD = "password";
    private String token=null;
    String response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Add your initialization code here


        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button geotag = (Button) findViewById(R.id.geotag);
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View view) {


                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(KEY_EMAIL, mEmailView.getText().toString());
                    jsonObject.put(KEY_PASSWORD, mPasswordView.getText().toString());
                    Log.d("Testing", "Inside Try");
                } catch (JSONException e) {
                    Log.d("Testing", "Inside Try");
                    e.printStackTrace();
                }
                //----------------Login request--------------------

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, "http://192.168.0.6:8000/auth/login", jsonObject,new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("response", response.toString());
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {
                                    response = jsonObject.getString("status");
                                    Log.d("response",jsonObject.getString("status"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), error.toString()+"Error on response", Toast.LENGTH_LONG).show();
                                Log.d("Error", error.toString());
                                NetworkResponse response = error.networkResponse;
                                if(response != null && response.data != null){
                                    Log.d("ERROR_MESSAGE", String.valueOf(response.data));
                                }
                            }
                        })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<String, String>();
                        header.put("Content-Type", "application/json");
                        header.put("X-CSRF-TOKEN", token);
                        return header;
                    }
                };

//               {
//                    @Override
//                    protected Map<String,String> getParams(){
//                        Map<String,String> params = new HashMap<String, String>();
// //                       params.put(KEY_PASSWORD,password);
//                        params.put(KEY_EMAIL, email);
//                        return params;
//                    }
//                };

                registerQueue = Volley.newRequestQueue(getApplicationContext());
                registerQueue.add(jsonObjectRequest);
                if(response=="success"){
                    Intent login = new Intent(Faculty_evaluators_login_activity.this,Faculty_evaluators_OSDS.class);
                    startActivity(login);
                }
            }
        });
//
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

//    public void geo(View view) {
//        Intent gro = new Intent(LoginActivity.this,Geo_Tag.class);
//        startActivity(gro);
//    }

//    public void uparloading(View view) {
//        Intent gro1 = new Intent(LoginActivity.this,Upload_to_server.class);
//        startActivity(gro1);
//    }
}