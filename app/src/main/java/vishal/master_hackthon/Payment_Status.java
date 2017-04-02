package vishal.master_hackthon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Payment_Status extends AppCompatActivity {
    ArrayList<Custom_Model> custom_models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psyment_status);
        ListView llPayment = (ListView) findViewById(R.id.lv);
        custom_models.add(new Custom_Model("JEEE", "Examiner", "CLEARED"));
        custom_models.add(new Custom_Model("AIEEE", "Examiner", "UN-CLEARED"));
        custom_models.add(new Custom_Model("SSC", "Super.", "CLEARED"));
        Custom_Adapter custom_adapter = new Custom_Adapter(custom_models, Payment_Status.this);
        llPayment.setAdapter(custom_adapter);


    }
}
