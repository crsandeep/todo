package com.codepath.doit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.doit.R;
import com.codepath.doit.models.Item;

import java.util.ArrayList;

/**
 * Created by sraveesh on 9/5/16.
 */
public class CustomItemsAdapter extends ArrayAdapter<Item> {

    public CustomItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvItem);
        tvName.setText(item.subject);
        return convertView;
    }
}
