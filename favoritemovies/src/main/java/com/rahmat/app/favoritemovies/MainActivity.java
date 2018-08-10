package com.rahmat.app.favoritemovies;

import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rahmat.app.favoritemovies.database.DatabaseContract;
import com.rahmat.app.favoritemovies.model.MovieFavorite;
import com.rahmat.app.favoritemovies.model.MovieResult;
import com.rahmat.app.favoritemovies.rest.MovieClient;
import com.rahmat.app.favoritemovies.rest.MovieInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rahmat.app.favoritemovies.database.DatabaseContract.CONTENT_URI;
import static com.rahmat.app.favoritemovies.rest.UtilsConstant.API_KEY;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<Cursor> {

    @BindView(R.id.recycler_favorite)
    RecyclerView recyclerView;

    Call<MovieResult> movieResultCall;
    MovieAdapter adapter;
    ArrayList<MovieResult> movieResults;
    ArrayList<MovieFavorite> movieFavorites;
    MovieInterface movieService;

    private final int MOVIE_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new MovieAdapter(getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getSupportLoaderManager().initLoader(MOVIE_ID, null, this);
    }


    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        movieFavorites = new ArrayList<>();
        movieResults = new ArrayList<>();
        return new CursorLoader(this, CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        movieFavorites = getItem(data);
        for (MovieFavorite m : movieFavorites) {
            getFavoriteMovies(m.getId());
            adapter.notifyDataSetChanged();
            Log.v("Matt1", "List"+movieFavorites.size());
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        movieFavorites = getItem(null);
    }

    private ArrayList<MovieFavorite> getItem(Cursor cursor){
        ArrayList<MovieFavorite> movieFavoriteArrayList = new ArrayList<>();
        cursor.moveToFirst();
        MovieFavorite favorite;
        Log.v("Matt1", "Listc"+cursor.getCount());
        if(cursor.getCount() > 0){
            do{
                favorite = new MovieFavorite(cursor.getString(cursor.getColumnIndexOrThrow(
                        DatabaseContract.MovieColumns.MOVIE_ID)));
                movieFavoriteArrayList.add(favorite);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        return movieFavoriteArrayList;
    }

    private void getFavoriteMovies(String id){
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieResultCall = movieService.getMovieById(id, API_KEY);

        movieResultCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                Log.v("Matt", ""+response.body().getId());
                movieResults.add(response.body());
                adapter.setMovieResult(movieResults);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                movieResults = null;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (movieResults != null) {
            movieResults.clear();
            Log.v("Matt1", "List"+movieResults.size());
            adapter.setMovieResult(movieResults);
            recyclerView.setAdapter(adapter);
        }
        getSupportLoaderManager().restartLoader(MOVIE_ID, null, this);
    }
}
