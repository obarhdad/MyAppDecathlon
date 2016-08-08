package com.decathlon.obarhdad.myappdecathlon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by obarhdad on 08/08/2016.
 */
public class MyAdapter extends BaseAdapter {
    ArrayList<Stores> myList = new ArrayList();
    Context context;
    LayoutInflater inflater;

    public MyAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);

    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Stores getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.stores_row_custom_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_MainText);
        RadioButton rb_Choice = (RadioButton) convertView.findViewById(R.id.rb_Choice);
        if (getItem(position).isFavorite()) //OFF->ON
            rb_Choice.setChecked(true);
        else
            rb_Choice.setChecked(false);
        textView.setText(getItem(position).getName());
        return convertView;
    }
}
