package vishal.master_hackthon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by smit on 01-04-2017.
 */

public class CeoVerification extends AppCompatActivity {
    private static final int REQ_CEO_AUTH = 512;
    private static final int REQ_PERSON_AUTH = 513;
    private ImageView ceoFG;
    private LinearLayout LLpersonList;
    private ListView personlistview;
    PersonAdapter personAdapter;
    ArrayList<PersonModel> personModels = new ArrayList<>();
    private int selectedPos = 0;
    private String token = null;
    private RequestQueue registerQueue;
    private String response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceo);
        ceoFG = (ImageView) findViewById(R.id.ceofg);
        LLpersonList = (LinearLayout) findViewById(R.id.person_list);
        personlistview = (ListView) findViewById(R.id.person_lst);

        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("deanEmail", "vishal@gmail.com");
//            jsonObject.put("androidLat", "21.132759");
//            jsonObject.put("androidLng", "72.715848");

            Log.d("Testing", "Inside Try");
        } catch (JSONException e) {
            Log.d("Testing", "Inside Try");
            e.printStackTrace();
        }

        String localhost = getApplicationContext().getResources().getString(R.string.Localhost);




//person list
        response = exam_center_university_School_Login.strresponse;
        try {
            JSONObject json = new JSONObject(response);
            if (json.optString("status").equalsIgnoreCase("success")) {
                JSONArray arr = json.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject j = arr.getJSONObject(i);
                    if (j.getInt("DeanAuthorization") == 1) {
                        personModels.add(new PersonModel(j.getString("DeanName"), true,j.getString("DeanAadharNo"),j.getString("DeanEmail")));
                    }
                    else
                        personModels.add(new PersonModel(j.getString("DeanName"), false,j.getString("DeanAadharNo"),j.getString("DeanEmail")));

                }
                personAdapter = new PersonAdapter(CeoVerification.this, personModels);
                personlistview.setAdapter(personAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //init person list


        personlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPos = i;
                startActivityForResult(new Intent(CeoVerification.this, FingerPrintPrank.class), REQ_PERSON_AUTH);

            }
        });
        ceoFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CeoVerification.this, FingerPrintPrank.class), REQ_CEO_AUTH);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CEO_AUTH) {
                ceoFG.setImageResource(R.drawable.right_tick);
                LLpersonList.setVisibility(View.VISIBLE);
            } else if (requestCode == REQ_PERSON_AUTH) {
                personModels.get(selectedPos).setIsvarified(true);
                personAdapter.notifyDataSetChanged();
                //send data

                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("deanEmail",personModels.get(selectedPos).getEmail());
                    jsonObject.put("aadharNo",personModels.get(selectedPos).getAadhar());
                    Log.d("Testing", "Inside Try");
                } catch (JSONException e) {
                    Log.d("Testing", "Inside Try");
                    e.printStackTrace();
                }
                String localhost=getApplicationContext().getResources().getString(R.string.Localhost);


                //----------------Login request--------------------

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, localhost+"/deanAadhar/verify", jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("response", response.toString());
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
    }
}
