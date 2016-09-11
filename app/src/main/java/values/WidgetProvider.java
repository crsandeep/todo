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
    public static String EXTRA_WORD=
            "com.commonsware.android.appwidget.lorem.WORD";

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        System.out.println("onUpdate........................................");
        for (int i=0; i<appWidgetIds.length; i++) {
            System.out.println("for loop........................................" + appWidgetIds[i]);
            Intent svcIntent = new Intent(ctxt, WidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            RemoteViews widget = new RemoteViews(ctxt.getPackageName(),
                    R.layout.to_do_widget);

            widget.setRemoteAdapter(appWidgetIds[i], R.id.to_do_widget,
                    svcIntent);

            Intent clickIntent = new Intent(ctxt, MainActivity.class);
            PendingIntent clickPI = PendingIntent
                    .getActivity(ctxt, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.to_do_widget, clickPI);

            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }

        super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
    }
}