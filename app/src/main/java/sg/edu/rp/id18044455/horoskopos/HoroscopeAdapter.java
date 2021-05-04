package sg.edu.rp.id18044455.horoskopos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class HoroscopeAdapter extends ArrayAdapter {


    Context parent_context;
    int layout_id;
    ArrayList<Horoscope> horoscopeList;

    public HoroscopeAdapter(Context context, int resource, ArrayList<Horoscope> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        horoscopeList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        ImageView horoscopeView = rowView.findViewById(R.id.horoscopeView);
        TextView tvHoroscope =  rowView.findViewById(R.id.tvHoroscope);
        TextView tvHoroscopeDate =  rowView.findViewById(R.id.tvHoroscopeDate);

        Horoscope currItem = horoscopeList.get(position);
        horoscopeView.setImageResource(currItem.getHoroscopeImage());
        tvHoroscope.setText(currItem.getHoroscopeName());
        tvHoroscopeDate.setText(currItem.getHoroscopeDate());

        return rowView;
    }//end of getView



}//end of class
