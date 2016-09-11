package values;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.codepath.doit.R;
import com.codepath.doit.activities.MainActivity;
import com.codepath.doit.models.Item;
import com.codepath.doit.utils.DBUtils;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class ToDoWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText;
        List<Item> items = DBUtils.readAll();

        String shareBody = "";
        for(int i = 0; i < items.size(); i++) {
            shareBody += i+1 + ". " + items.get(i).subject + "\n";
        }

        widgetText = shareBody;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.to_do_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent openApp = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, openApp, 0);

        views.setOnClickPendingIntent(R.id.appwidget_text, pIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

