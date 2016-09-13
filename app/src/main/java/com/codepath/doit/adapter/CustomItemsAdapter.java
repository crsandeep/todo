package com.codepath.doit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.codepath.doit.R;
import com.codepath.doit.models.Item;

import java.util.ArrayList;

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
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvItemPriority);
        tvName.setText(item.subject);
        tvPriority.setText(item.priority);
        TextView dueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        if(!TextUtils.isEmpty(item.dueDate)) {
            dueDate.setVisibility(View.VISIBLE);
            dueDate.setText("due on " + item.dueDate);
            if(!TextUtils.isEmpty(item.dueTime)) {
                dueDate.append(" " + item.dueTime);
            }
        } else {
            dueDate.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void add(Item item) {
        filteredItems.add(item);
    }

    @Override
    public void remove(Item item) {
        filteredItems.remove(item);
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
                CustomItemsAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Item> filteredResults = new ArrayList<>();

                for(int i = 0; i < items.size(); i++) {
                    if(items.get(i).subject.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredResults.add(items.get(i));
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;
                results.count = items.size();

                return results;
            }
        };
    }
}
