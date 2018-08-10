package com.rahmat.app.cataloguemovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by muhrahmatullah on 07/08/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "catalogue_movie";

    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            MovieContract.MovieColumns.TABLE_MOVIE,
            MovieContract.MovieColumns._ID,
            MovieContract.MovieColumns.MOVIE_ID,
            MovieContract.MovieColumns.MOVIE_TITLE
    );



    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieColumns.TABLE_MOVIE);
        onCreate(db);
    }
}
