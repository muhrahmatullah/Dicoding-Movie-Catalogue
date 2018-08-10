package com.rahmat.app.cataloguemovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by muhrahmatullah on 07/08/18.
 */
public class MovieHelper {

    private static String DATABASE_TABLE = MovieContract.MovieColumns.TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase sqliteDatabase;

    public MovieHelper(Context context){
        this.context = context;
    }

    public MovieHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        sqliteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqliteDatabase.close();
    }

    public Cursor queryProvider() {
        return sqliteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                 MovieContract.MovieColumns._ID+ " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return sqliteDatabase.query(DATABASE_TABLE, null
                , MovieContract.MovieColumns.MOVIE_ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(ContentValues values) {
        return sqliteDatabase.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sqliteDatabase.update(DATABASE_TABLE, values,
                MovieContract.MovieColumns.MOVIE_ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqliteDatabase.delete(DATABASE_TABLE,
                MovieContract.MovieColumns.MOVIE_ID + " = ?", new String[]{id});
    }

}
