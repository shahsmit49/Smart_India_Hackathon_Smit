package vishal.master_hackthon;//package vishal.hackathon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Upload_to_server extends AppCompatActivity implements View.OnClickListener {

    private Button buttonChoose;
    private Button buttonUpload, btpic;
    private ImageView mImageView;
    private EditText editTextName;
    private Bitmap bitmap;
    String sss = null;
    GPSTracker gps = new GPSTracker(Upload_to_server.this);
    private int PICK_IMAGE_REQUEST = 1;

    //    private String UPLOAD_URL ="http://simplifiedcoding.16mb.com/VolleyUpload/upload.php";
//    private String UPLOAD_URL ="https://vishallog.000webhostapp.com/upload_image.php";
    private String KEY_IMAGE = "image";

    private String token = null;
    private RequestQueue registerQueue;
    private String KEY_NAME = "name";
    private String KEY_DESC = "desc";
    String imageFileName;


    String mCurrentPhotoPath;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_to_server);
//        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
//        editTextName = (EditText) findViewById(R.id.editText);
        mImageView = (ImageView) findViewById(R.id.imageView);
        TextView counttxt = (TextView) findViewById(R.id.count);
//        buttonChoose.setOnClickListener((View.OnClickListener)this);
        buttonUpload.setOnClickListener((View.OnClickListener) this);

        SharedPreferences pref = getSharedPreferences("dean_exam", 0);
        String email = pref.getString("email", "");
        Log.d("Email", email);
        final String count = getIntent().getStringExtra("count");
        counttxt.setText(count);


        /************************************************/
        //         GPS
        /*************************************************/
        gps.getLocation();
        // check if GPS enabled
//            while (sss == null)
//            {
        //if (gps.canGetLocation()) {
        sss = gps.getLatitude();
//                  String lat = gps.latitude();

        String abc[] = sss.split(",");
        latitude = abc[0];
        longitude = abc[1];
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        Log.d("lat", latitude);
        Log.d("long", longitude);
    }


    /*****************************/
    //  Upload pic

    /******************************/


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadImage();
        }
    }

    private void uploadImage() {

        final JSONObject jsonObject = new JSONObject();
        try {
            String image = getStringImage(bitmap);

//            jsonObject.put("deanEmail", "vishal@gmail.com");
//            jsonObject.put("androidLat", "21.132759");
//            jsonObject.put("androidLng", "72.715848");

            jsonObject.put("deanEmail", "shahmeet285@gmail.com");
            jsonObject.put("androidLat", latitude);
            jsonObject.put("androidLng", longitude);
            jsonObject.put("fileName", imageFileName);


            jsonObject.put(KEY_IMAGE, image);
            Log.d("bitmap_image", image);
            Log.d("Testing", "Inside Try");
            Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(this,"upppppppp",Toast.LENGTH_SHORT).show();
            Log.d("Testing", "Inside Try");
            e.printStackTrace();
        }

        String localhost = getApplicationContext().getResources().getString(R.string.Localhost);


        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, localhost + "/location/getPlace", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
                        try {
                            Log.d("got_response", response.getString("status"));
                            loading.dismiss();

                            /***************succefully uploaded**************************/

//                            if(response.getString("status").equals("status")){
//                                Intent myintent=new Intent(Upload_to_server.this, Geo_tag_exam_conduct.class);
//                                startActivity(myintent);
//                            }
                            /**/
                            setResult(RESULT_OK);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(),"pan kai nai malyu",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setPic();
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("encoded_image", encodedImage);
        return encodedImage;
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void camera(View view) {
        captureImage();
    }


    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = timeStamp + "_" + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("Getpath", "Cool" + mCurrentPhotoPath);
        return image;
    }

}
