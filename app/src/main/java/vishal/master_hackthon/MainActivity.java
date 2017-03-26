package vishal.master_hackthon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void evaluators_faculty_login(View view) {
        Intent i = new Intent(MainActivity.this,Faculty_evaluators_login_activity.class);
        startActivity(i);
    }

    public void college_school_login(View view) {
        
    }
}
