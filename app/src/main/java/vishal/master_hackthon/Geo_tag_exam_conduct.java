package vishal.master_hackthon;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static vishal.master_hackthon.R.id.count;

public class Geo_tag_exam_conduct extends AppCompatActivity {

    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_tag_exam_conduct);

        String Deanname = getIntent().getStringExtra("DeanName");
        String Examname = getIntent().getStringExtra("ExamName");

        Button time1 = (Button)findViewById(R.id.textView4);
        Button time2 = (Button)findViewById(R.id.textView3);
        Button time3 = (Button)findViewById(R.id.textView2);

        gps = new GPSTracker(Geo_tag_exam_conduct.this);
        gps.getLocation();
//        if (gps.canGetLocation())
//        {
//
//
//        }
//        else
//        {
//            // can't get location
//            // GPS or Network is not enabled
//            // Ask user to enable GPS/network in settings
//            gps.showSettingsAlert();
//
//            gps.getLocation();
//        }

        TextView DeanName = (TextView)findViewById(R.id.faculty_name1);
        TextView ExamName = (TextView)findViewById(R.id.examname1);

        ImageView img1 = (ImageView)findViewById(R.id.imageView4);
        ImageView img2 = (ImageView)findViewById(R.id.imageView3);
        ImageView img3 = (ImageView)findViewById(R.id.imageView2);

        SharedPreferences spe = getSharedPreferences("dean_exam",0);
        DeanName.setText(spe.getString("DeanName","null"));
        ExamName.setText(spe.getString("ExamName","null"));

        final String countsuccess = getIntent().getStringExtra("countsuccess");

        if(countsuccess != null){
            if(countsuccess == "1"){
                img1.setImageResource(R.drawable.right_tick);
                time1.setEnabled(false);


            }
            if(countsuccess == "2"){
                img2.setImageResource(R.drawable.right_tick);
                time2.setEnabled(false);
            }if(countsuccess == "3"){
                img3.setImageResource(R.drawable.right_tick);
                time3.setEnabled(false);
            }



        }



        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                gps.getLocation();
                if(gps.canGetLocation()) {
                    Intent myintent = new Intent(Geo_tag_exam_conduct.this, Upload_to_server.class).putExtra("count", "1");
                    startActivity(myintent);
                }
                else
                {
                    gps.showSettingsAlert();
                    return;
                }
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                gps.getLocation();
                if(gps.canGetLocation()) {
                    Intent myintent = new Intent(Geo_tag_exam_conduct.this, Upload_to_server.class).putExtra("count", "1");
                    startActivity(myintent);
                }
                else
                {
                    gps.showSettingsAlert();
                    return;
                }
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                gps.getLocation();
                if(gps.canGetLocation()) {
                    Intent myintent = new Intent(Geo_tag_exam_conduct.this, Upload_to_server.class).putExtra("count", "1");
                    startActivity(myintent);
                }
                else
                {
                    gps.showSettingsAlert();
                    return;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void notif(View view) {
        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle("Hello").setContentText("YOO").
                setContentTitle("well bitch").setSmallIcon(R.drawable.ic_android_black_24dp).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }
}
