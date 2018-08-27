package com.rahmat.app.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.rahmat.app.cataloguemovie.R;
import com.rahmat.app.cataloguemovie.database.MovieContract;
import com.rahmat.app.cataloguemovie.model.MovieFavorite;
import com.rahmat.app.cataloguemovie.utils.UtilsConstant;

import java.util.concurrent.ExecutionException;

/**
 * Created by muhrahmatullah on 24/08/18.
 */
public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;

    int mAppWidgetId;

    private Cursor cursor;

    public MovieRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private MovieFavorite getFav(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

      return new MovieFavorite(cursor.getString(cursor.getColumnIndexOrThrow(
                MovieContract.MovieColumns.MOVIE_ID)),
              cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieColumns.MOVIE_IMAGE)),
              cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieColumns.MOVIE_TITLE)));
    }


    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                MovieContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                MovieContract.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieFavorite movieFavorite = getFav(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_item);

        Log.d("Widgetku",movieFavorite.getTitle());

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(UtilsConstant.BASE_BACKDROP_URL_WIDGET+movieFavorite.getImage())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rv.setImageViewBitmap(R.id.img_widget,bmp);
            rv.setTextViewText(R.id.tv_movie_title, movieFavorite.getTitle());
            Log.d("Widgetku","Yessh");
        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }
        Bundle extras = new Bundle();
        extras.putInt(MovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
