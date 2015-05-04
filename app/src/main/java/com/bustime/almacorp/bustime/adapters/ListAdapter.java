package com.bustime.almacorp.bustime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bustime.almacorp.bustime.app.R;

import java.util.ArrayList;
import java.util.Map;

public class ListAdapter extends BaseAdapter {

    private final ArrayList stops;
    private Context context;

    public ListAdapter(Context context, Map<String, ?> stops) {
        this.context = context;
        this.stops = new ArrayList();
        this.stops.addAll(stops.entrySet());
    }

    @Override
    public int getCount() {
        return stops.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) stops.get(position);
    }

    @Override
    public long getItemId(int position) {
        //We don't use itemId
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_element, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.stop_name);
        TextView code = (TextView) rowView.findViewById(R.id.stop_code);
        Map.Entry<String, String> item = getItem(position);
        name.setText(item.getKey());
        code.setText(item.getValue());
        return rowView;
    }
}
