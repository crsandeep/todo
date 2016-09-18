package com.codepath.doit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.codepath.doit.R;
import com.codepath.doit.activities.MainActivity;
import com.codepath.doit.models.Item;

import java.util.ArrayList;

public class CustomItemsAdapter extends ArrayAdapter<Item> implements Filterable {

    private ArrayList<Item> original;
    private ArrayList<Item> fitems;
    private Filter filter;
    private Context context;

    public CustomItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.original = new ArrayList<>();
        this.original.addAll(items);
        this.fitems = new ArrayList<>();
        this.fitems.addAll(items);
        this.context = context;
    }

    @Override
    public void add(Item item) {
        this.fitems.add(item);
        this.original.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Item item) {
        this.fitems.remove(item);
        this.original.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fitems.size();
    }

    @Override
    public Item getItem(int position) {
        return fitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.item, null);
        Item item = fitems.get(position);
        if (item != null) {
            TextView tvName = (TextView) v.findViewById(R.id.tvItem);
            TextView tvPriority = (TextView) v.findViewById(R.id.tvItemPriority);
            CheckBox cb = (CheckBox) v.findViewById(R.id.cbItemCheck);
            cb.setOnCheckedChangeListener((MainActivity) context);
            tvName.setText(item.subject);
            tvPriority.setText(item.priority);
            TextView dueDate = (TextView) v.findViewById(R.id.tvDueDate);
            if (!TextUtils.isEmpty(item.dueDate)) {
                dueDate.setVisibility(View.VISIBLE);
                dueDate.setText(item.dueDate);
                if (!TextUtils.isEmpty(item.dueTime)) {
                    dueDate.append(" " + item.dueTime);
                }
            } else {
                dueDate.setVisibility(View.GONE);
            }
        }
        return v;
    }

    @Override
    public Filter getFilter()
    {
        if (filter == null) {
            filter = new ItemsFilter();
        }
        return filter;
    }

    private class ItemsFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix.length() == 0)
            {
                ArrayList<Item> list = new ArrayList<>(original);
                results.values = list;
                results.count = list.size();
            }
            else
            {
                final ArrayList<Item> list = new ArrayList<>(original);
                final ArrayList<Item> nlist = new ArrayList<>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final Item item = list.get(i);
                    final String value = item.subject.toLowerCase();

                    if (value.contains(prefix))
                    {
                        nlist.add(item);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            fitems = (ArrayList<Item>)results.values;
            CustomItemsAdapter.this.notifyDataSetChanged();
        }

    }
}
