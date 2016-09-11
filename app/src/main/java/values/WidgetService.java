package values;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        System.out.println("onGetViewFactory........................................");
        return(new WidgetViewsFactory(this.getApplicationContext(),
                intent));
    }
}