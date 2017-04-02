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
    private int selectedPos=0;
    private String token = null;
    private RequestQueue registerQueue;


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


        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, localhost+"/deanAadhar/verify", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            Log.d("maliyo_response",response.getString("status"));
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
                header.put("Content-Type","application/json");
                header.put("X-CSRF-TOKEN", token);
                return header;
            }
        };
        registerQueue = Volley.newRequestQueue(getApplicationContext());
        registerQueue.add(jsonObjectRequest);






        //init person list
        personModels.add(new PersonModel("Meet Shah", false));
        personModels.add(new PersonModel("Chirag Desai", false));
        personModels.add(new PersonModel("Vishal sheth", false));
        personModels.add(new PersonModel("Vishav", false));
        personModels.add(new PersonModel("Vimal Gajera", false));
        personAdapter = new PersonAdapter(CeoVerification.this, personModels);
        personlistview.setAdapter(personAdapter);
        personlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPos=i;
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
            }
            else if(requestCode==REQ_PERSON_AUTH)
            {
                personModels.get(selectedPos).setIsvarified(true);
                personAdapter.notifyDataSetChanged();
            }
        }
    }
}
