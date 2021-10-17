package com.daniahnaf.formpendaftan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daniahnaf.formpendaftan.R;
import com.daniahnaf.formpendaftan.model.Data;
import java.util.List;

public class Adapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<Data> items;

    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView==null){
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        TextView id = convertView.findViewById(R.id.id);
        TextView name = convertView.findViewById(R.id.name);
        TextView address = convertView.findViewById(R.id.address);
        TextView noHp = convertView.findViewById(R.id.nohp);
        TextView jk = convertView.findViewById(R.id.jk);


        Data data = items.get(position);
        id.setText(data.getId());
        name.setText(data.getName());
        address.setText(data.getAddress());
        noHp.setText(data.getNohp());
        jk.setText(data.getJk());
        return convertView;
    }
}