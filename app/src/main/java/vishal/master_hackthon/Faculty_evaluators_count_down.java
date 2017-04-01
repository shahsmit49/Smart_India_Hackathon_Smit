package vishal.master_hackthon;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Faculty_evaluators_count_down extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_evaluators_count_down);
        final TextView mCountdown =(TextView)findViewById(R.id.countdown);
        new CountDownTimer(5000,1000){
            public void onTick (long millis){
                mCountdown.setText("sec-remaining"+millis/1000);
            }

            @Override
            public void onFinish() {
//                mCountdown.setText("Done!!");
                Intent countdown =new Intent (Faculty_evaluators_count_down.this,Geo_tag_exam_conduct.class);
                startActivity(countdown);
            }
        }.start();
    }
}
