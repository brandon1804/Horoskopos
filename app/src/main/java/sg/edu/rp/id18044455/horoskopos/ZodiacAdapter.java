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


public class ZodiacAdapter extends ArrayAdapter {


    Context parent_context;
    int layout_id;
    ArrayList<Zodiac> zodiacList;

    public ZodiacAdapter(Context context, int resource, ArrayList<Zodiac> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        zodiacList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        ImageView zodiacView = rowView.findViewById(R.id.zodiacView);
        TextView tvZodiac =  rowView.findViewById(R.id.tvZodiac);
        TextView tvzodiacYears =  rowView.findViewById(R.id.tvzodiacYears);

        Zodiac currItem = zodiacList.get(position);
        zodiacView.setImageResource(currItem.getZodiacImage());
        tvZodiac.setText(currItem.getZodiacName());
        tvzodiacYears.setText(currItem.getZodiacYears());

        return rowView;
    }//end of getView



}//end of class
