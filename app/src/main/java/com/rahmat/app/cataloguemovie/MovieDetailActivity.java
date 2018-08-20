package com.rahmat.app.cataloguemovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahmat.app.cataloguemovie.database.MovieContract;
import com.rahmat.app.cataloguemovie.model.MovieResult;
import com.rahmat.app.cataloguemovie.utils.DateFormator;
import com.rahmat.app.cataloguemovie.utils.UtilsConstant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_overview_tv)
    TextView tvOverview;
    @BindView(R.id.image_detail)
    ImageView backDrop;
    @BindView(R.id.item_date_detail)
    TextView tvDate;
    @BindView(R.id.item_title_detail)
    TextView tvTitle;
    @BindView(R.id.movie_poster_detail)
    ImageView poster;
    @BindView(R.id.item_rating_detial)
    TextView tvRating;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    String id, title;

    MovieResult movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        movie = getIntent().getParcelableExtra(UtilsConstant.MOVIE_DETAIL);
        updateUI(movie);
        id = movie.getId().toString();
        title = movie.getTitle();

        if (isRecordExists(movie.getId().toString())) {
            if (floatingActionButton != null) {
                floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                Log.v("MovieDetail", "" + movie.getId());
            }
        }

        if(floatingActionButton != null){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRecordExists(movie.getId().toString())){
                    ContentValues contentValues = new ContentValues();
                    // Put the task description and selected mPriority into the ContentValues
                    contentValues.put(MovieContract.MovieColumns.MOVIE_ID, id);
                    contentValues.put(MovieContract.MovieColumns.MOVIE_TITLE, title);
                    // Insert the content values via a ContentResolver
                    getContentResolver().insert(MovieContract.CONTENT_URI, contentValues);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Snackbar.make(v, "This movie has been add to your favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Uri uri = MovieContract.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(id).build();
                    Log.v("MovieDetail", ""+uri);

                    getContentResolver().delete(uri, null, null);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Log.v("MovieDetail", uri.toString());
                    Snackbar.make(v, "This movie has been remove from your favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        }

    }

    void updateUI(MovieResult movie){
        getSupportActionBar().setTitle(movie.getTitle());
        Picasso.with(this)
                .load(UtilsConstant.BASE_POSTER_URL+movie.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(poster);

        Picasso.with(this)
                .load(UtilsConstant.BASE_BACKDROP_URL+movie.getBackdropPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(backDrop);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvDate.setText(getResources().getString(R.string.release_date,
                DateFormator.getDateDay(movie.getReleaseDate())));
        tvRating.setText(getResources().getString(R.string.rating,
                movie.getVoteAverage().toString()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            super.onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isRecordExists(String id) {
        String selection = " movie_id = ?";
        String[] selectionArgs = { id };
        String[] projection = {MovieContract.MovieColumns.MOVIE_ID};
        Uri uri = MovieContract.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        Cursor cursor = getContentResolver().query(uri, projection ,
                selection, selectionArgs, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        Log.v("isi", Boolean.toString(exists));
        return exists;
    }
}
