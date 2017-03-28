package vishal.master_hackthon;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private String EmailVariable;
    boolean user_login_status = false;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Add your initialization code here


        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                if (settings.getString("logged", "").toString().equals("logged")) {
                    Intent intent = new Intent(Faculty_evaluators_login_activity.this, Faculty_evaluators_OSDS.class);
                    startActivity(intent);
                }
                else {

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
                            (Request.Method.POST, "http://192.168.222.126:8000/auth/login", jsonObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("response", response.toString());
                                    try {
                                        Log.d("CC", response.getString("status"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (response.getString("status").equals("success")) {
                                            user_login_status = true;
                                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.putString("logged", "logged");
                                            editor.commit();

                                            Intent intent = new Intent(Faculty_evaluators_login_activity.this, Faculty_evaluators_OSDS.class);
                                            startActivity(intent);
                                        } else if (response.getString("Status").equals("Failure")) {
                                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                    //                   startActivity(new Intent(Faculty_evaluators_login_activity.this, Faculty_evaluators_OSDS.class));
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString() + "Error on response", Toast.LENGTH_LONG).show();
                                    Log.d("BC", error.toString());
                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> header = new HashMap<String, String>();
                            header.put("Content-Type", "application/json");
                            header.put("X-CSRF-TOKEN", token);
                            return header;
                        }
                    };
                    registerQueue = Volley.newRequestQueue(getApplicationContext());
                    registerQueue.add(jsonObjectRequest);
                }
                }
            });


//
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

//        private void openProfile(){
//            Intent intent = new Intent(this, Faculty_evaluators_OSDS.class);
//           intent.putExtra(KEY_USERNAME, username);
//            startActivity(intent);
//        }

    }

    public void geotag(View view) {
        Intent i = new Intent(Faculty_evaluators_login_activity.this,Geo_Tag.class);
        startActivity(i);
    }

    public void bypass(View view) {
        EmailVariable = mEmailView.getText().toString();
        Intent gro = new Intent(Faculty_evaluators_login_activity.this,Faculty_evaluators_OSDS.class);
        startActivity(gro);
    }

    public String getEmailVariable() {
        return EmailVariable;
    }

    public void setEmailVariable(String emailVariable) {
        EmailVariable = emailVariable;
    }
}