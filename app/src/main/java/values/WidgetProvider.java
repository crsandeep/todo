package values;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.codepath.doit.R;
import com.codepath.doit.activities.MainActivity;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent svcIntent = new Intent(ctxt, WidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            RemoteViews widget = new RemoteViews(ctxt.getPackageName(),
                    R.layout.to_do_widget);

            //noinspection deprecation
            widget.setRemoteAdapter(appWidgetId, R.id.to_do_widget,
                    svcIntent);

            Intent clickIntent = new Intent(ctxt, MainActivity.class);
            PendingIntent clickPI = PendingIntent
                    .getActivity(ctxt, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.to_do_widget, clickPI);

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }

        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
    }
}