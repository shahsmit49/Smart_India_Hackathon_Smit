package vishal.master_hackthon;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by smit on 01-04-2017.
 */

public class FingerPrintPrank extends Activity implements View.OnClickListener {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_prank);
        iv = (ImageView) findViewById(R.id.fingerr);
        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
