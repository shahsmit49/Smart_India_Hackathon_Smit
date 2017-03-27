package vishal.master_hackthon;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********** get Gps location service LocationManager object ***********/
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("GPS","Turned On");
//        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }
    
    public void evaluators_faculty_login(View view) {
        Intent i = new Intent(MainActivity.this,Faculty_evaluators_login_activity.class);
        startActivity(i);
    }

    public void college_school_login(View view) {
        Intent i = new Intent(MainActivity.this,exam_center_university_School_Name.class);
        startActivity(i);

    }
}
