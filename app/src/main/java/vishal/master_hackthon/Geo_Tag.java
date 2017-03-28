package vishal.master_hackthon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.bitmap;

public class Geo_Tag extends ActionBarActivity {

    Button btpic, btnup;
    String ba1;
    public static String URL = "https://vishallog.000webhostapp.com/upload_image.php";
    String mCurrentPhotoPath;
    ImageView mImageView;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private Bitmap bitmap;
    private String token = null;
    private RequestQueue registerQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo__tag);
    }

    public void clickkk(View view) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deanEmail", "MayankJ@gmail.com");
            jsonObject.put("androidLat", "21.132759");
            jsonObject.put("androidLng", "72.715848");

            Log.d("Testing", "Inside Try");
        } catch (JSONException e) {
            Log.d("Testing", "Inside Try");
            e.printStackTrace();
        }
        //----------------Login request--------------------

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, "http://192.168.222.126:8000/location/getPlace", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            Log.d("yoo",response.getString("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
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

}

