package vishal.master_hackthon;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Falgun on 4/2/2017.
 */

public class Custom_Adapter extends BaseAdapter {
    private ArrayList<Custom_Model> custom_models;
    Context context;

    public Custom_Adapter(ArrayList<Custom_Model> custom_models, Context context) {
        this.custom_models = custom_models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return custom_models.size();
    }

    @Override
    public Object getItem(int position) {
        return custom_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lf = LayoutInflater.from(context);
        View v = lf.inflate(R.layout.payment_item, null);
        TextView examName = (TextView) v.findViewById(R.id.exam_name);
        TextView role = (TextView) v.findViewById(R.id.role);
        TextView status = (TextView) v.findViewById(R.id.status);

        examName.setText(custom_models.get(position).getName());
        role.setText(custom_models.get(position).getRole());
        status.setText(custom_models.get(position).getPayment_status());


        return v;
    }
}
