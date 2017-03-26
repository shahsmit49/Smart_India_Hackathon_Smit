package vishal.master_hackthon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Faculty_evaluators_OSDS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_evaluators__osds);
    }

    public void exam_name(View view) {
        Intent i = new Intent(Faculty_evaluators_OSDS.this,Geo_tag_exam_conduct.class);
        startActivity(i);
    }
}
