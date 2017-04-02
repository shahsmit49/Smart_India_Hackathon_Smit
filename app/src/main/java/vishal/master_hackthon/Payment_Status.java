package vishal.master_hackthon;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Payment_Status extends AppCompatActivity {
    ArrayList<Custom_Model> custom_models = new ArrayList<>();


    private String token = null;
    private RequestQueue registerQueue;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psyment_status);
        final ListView llPayment = (ListView) findViewById(R.id.lv);


        final JSONObject jsonObject = new JSONObject();
        try {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            String email=settings.getString("email", "").toString();
            jsonObject.put("deanEmail","vishal@gmail.com");

            Log.d("Testing", "Inside Try");
        } catch (JSONException e) {
            Log.d("Testing", "Inside Try");
            e.printStackTrace();
        }

        String localhost = getApplicationContext().getResources().getString(R.string.Localhost);


        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, localhost + "/androidPayment", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());

                        try {
                            JSONObject json = new JSONObject(response.toString());
                            JSONArray jrr = json.getJSONArray("status");
                            String status = ((JSONObject) jrr.get(0)).optString("PaymentStatus");

                            custom_models.add(new Custom_Model(Faculty_evaluators_OSDS.ExamName, Faculty_evaluators_OSDS.ExamDuty, status));


                            Custom_Adapter custom_adapter = new Custom_Adapter(custom_models, Payment_Status.this);
                            llPayment.setAdapter(custom_adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            Log.d("maliyo_response", response.getString("status"));
                            loading.dismiss();
                            /***************succefully uploaded**************************/

//                            if(response.getString("status").equals("status")){
//                                Intent myintent=new Intent(Upload_to_server.this, Geo_tag_exam_conduct.class);
//                                startActivity(myintent);
//                            }
                            /**/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString() + "Error on response", Toast.LENGTH_LONG).show();
                        Log.d("Error", error.toString());
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
