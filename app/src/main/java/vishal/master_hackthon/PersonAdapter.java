package vishal.master_hackthon;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import static android.R.attr.name;

/**
 * Created by smit on 01-04-2017.
 */

public class PersonAdapter extends BaseAdapter {
    private final ArrayList<PersonModel> personlist;
    Activity c;

    public PersonAdapter(Activity con, ArrayList<PersonModel> personlist) {
        c = con;
        this.personlist = personlist;
    }

    public int getCount() {
        return personlist.size();
    }

    public View getView(int pos, View arg44, ViewGroup arg22) {
        LayoutInflater lf = LayoutInflater.from(c);
        View v = lf.inflate(R.layout.person_item, null);
        TextView name = (TextView) v.findViewById(R.id.person_name);
        ImageView ivFG = (ImageView) v.findViewById(R.id.ceofg);
        if (personlist.get(pos).isvarified())
            ivFG.setImageResource(R.drawable.right_tick);
        else
            ivFG.setImageResource(R.drawable.fingerprint_small);

        name.setText(personlist.get(pos).getName());

        return v;
    }

    public Object getItem(int arg) {
        return 0;
    }

    public long getItemId(int arg) {
        return 0;
    }
}
