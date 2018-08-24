package com.rahmat.app.cataloguemovie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by muhrahmatullah on 24/08/18.
 */
public class MovieWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieRemoteViewsFactory(this.getApplicationContext(), intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
