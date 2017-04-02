package vishal.master_hackthon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceo);
        ceoFG = (ImageView) findViewById(R.id.ceofg);
        LLpersonList = (LinearLayout) findViewById(R.id.person_list);
        personlistview = (ListView) findViewById(R.id.person_lst);

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
