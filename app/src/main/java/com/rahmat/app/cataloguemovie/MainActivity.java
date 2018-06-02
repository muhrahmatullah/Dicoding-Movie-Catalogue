package com.rahmat.app.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.rahmat.app.cataloguemovie.model.Movie;
import com.rahmat.app.cataloguemovie.model.MovieResult;
import com.rahmat.app.cataloguemovie.rest.MovieClient;
import com.rahmat.app.cataloguemovie.rest.MovieInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        MaterialSearchBar.OnSearchActionListener{

    private final static String API_KEY = BuildConfig.ApiKey;
    List<MovieResult> movieList;

    //@BindView(R.id.searchBar)
    MaterialSearchBar materialSearchBar;

    MovieInterface movieService;
    Call<Movie> movieCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife.bind(this);
        materialSearchBar = findViewById(R.id.searchBar);

        materialSearchBar.setOnSearchActionListener(this);
        materialSearchBar.inflateMenu(R.menu.main);

    }

    private void getMovies(String query){
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieCall = movieService.getMovieBySearch(query, API_KEY);

        movieList = new ArrayList<>();

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMovies(){
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieCall = movieService.getPopularMovie(API_KEY);

        movieList = new ArrayList<>();

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        String q = String.valueOf(text);
        getMovies(q);
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
