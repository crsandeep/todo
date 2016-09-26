package values;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.codepath.doit.R;
import com.codepath.doit.models.Item;
import com.codepath.doit.utils.DBUtils;
import com.codepath.doit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Item> items;
    private Context ctxt=null;

    public WidgetViewsFactory(Context ctxt) {
        this.ctxt=ctxt;
        populate();
    }

    private void populate() {
        items = new ArrayList<>();
        items = DBUtils.readAll();
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return(items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.row);

        row.setTextViewText(R.id.textSubject, items.get(position).subject);

        if(!TextUtils.isEmpty(Utils.getStringFromDate(items.get(position).dueDate))) {
            row.setViewVisibility(R.id.textDueDate, View.VISIBLE);
            row.setTextViewText(R.id.textDueDate, Utils.getStringFromDate(items.get(position).dueDate));
        } else {
            row.setViewVisibility(R.id.textDueDate, View.GONE);
        }
        Intent i=new Intent();
        row.setOnClickFillInIntent(R.id.widgetItem, i);
        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }
}