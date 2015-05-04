package com.bustime.almacorp.bustime.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bustime.almacorp.bustime.app.R;
import com.bustime.almacorp.bustime.model.BusTime;

import java.util.List;

public class BusAdapter extends ArrayAdapter<BusTime> {

    private Context context;
    private List<BusTime> stops;

    public BusAdapter(Context context, List<BusTime> stops) {
        super(context, R.layout.bus_element, stops);
        this.stops = stops;
        this.context = context;
    }

    @Override
    public BusTime getItem(int position) {
        return stops.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.bus_element, parent, false);

        BusTime busTime = getItem(position);
        String busName = busTime.getName();
        String time = busTime.getTime();

        TextView name = (TextView) rowView.findViewById(R.id.stop_name);
        name.setText(busName);
        TextView code = (TextView) rowView.findViewById(R.id.stop_code);
        code.setText(time);

        RelativeLayout element = (RelativeLayout) rowView
                .findViewById(R.id.relative_element);
        ImageView triangle = (ImageView) rowView.findViewById(R.id.triangle);
        if (busName.startsWith("B") || busName.startsWith("L")) {
            int color = context.getResources().getColor(R.color.yellow);
            element.setBackgroundColor(color);
            Drawable background = context.getResources().getDrawable(
                    R.drawable.orange_triangle);
            triangle.setImageDrawable(background);
        } else if (busName.startsWith("N") || busName.startsWith("H")) {
            int color = context.getResources().getColor(R.color.blue);
            element.setBackgroundColor(color);
            Drawable background = context.getResources().getDrawable(
                    R.drawable.blue_triangle);
            triangle.setImageDrawable(background);
        } else {
            int color = context.getResources().getColor(R.color.red);
            element.setBackgroundColor(color);
            Drawable background = context.getResources().getDrawable(
                    R.drawable.red_triangle);
            triangle.setImageDrawable(background);
        }

        return rowView;
    }

}
