package com.codepath.doit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.codepath.doit.R;
import com.codepath.doit.models.Item;

import java.util.ArrayList;

/**
 * Created by sraveesh on 9/5/16.
 */
public class CustomItemsAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> items;
    private ArrayList<Item> filteredItems;

    public CustomItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.items = items;
        this.filteredItems = items;
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

    @Override
    public Item getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems = (ArrayList<Item>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Item> filteredResults = new ArrayList<Item>();

                for(int i = 0; i < items.size(); i++) {
                    if(items.get(i).subject.contains(constraint)) {
                        filteredResults.add(items.get(i));
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }
}
