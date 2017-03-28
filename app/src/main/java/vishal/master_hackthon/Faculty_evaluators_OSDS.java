package vishal.master_hackthon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Faculty_evaluators_OSDS extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_evaluators__osds);
        Faculty_evaluators_login_activity logo = new Faculty_evaluators_login_activity();
//        String email = Yoo;
//        email = logo.getEmailVariable();
//        Log.d("Email_get",logo.getEmailVariable());
//        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
    }
    public void exam_name(View view) {
        Intent i = new Intent(Faculty_evaluators_OSDS.this,Geo_tag_exam_conduct.class);
        startActivity(i);
    }
}
