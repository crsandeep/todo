package values;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.codepath.doit.R;
import com.codepath.doit.models.Item;
import com.codepath.doit.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Item> items;
    private Context ctxt=null;
    private int appWidgetId;

    public WidgetViewsFactory(Context ctxt, Intent intent) {
        this.ctxt=ctxt;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
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

        row.setTextViewText(android.R.id.text1, items.get(position).subject);

        Intent i=new Intent();
        Bundle extras=new Bundle();

        extras.putString(WidgetProvider.EXTRA_WORD, items.get(position).subject);
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

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