package com.rahmat.app.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.rahmat.app.cataloguemovie.adapter.ItemMovieAdapter;
import com.rahmat.app.cataloguemovie.model.Movie;
import com.rahmat.app.cataloguemovie.model.MovieResult;
import com.rahmat.app.cataloguemovie.rest.MovieClient;
import com.rahmat.app.cataloguemovie.rest.MovieInterface;
import com.rahmat.app.cataloguemovie.utils.UtilsConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.API_KEY;

public class MainActivity extends AppCompatActivity implements
        MaterialSearchBar.OnSearchActionListener{


    List<MovieResult> movieList;
    ItemMovieAdapter movieAdapter;
    boolean isPopular = true;
    int totalResult = 0;
    String q = "";

    @BindView(R.id.searchBar)
    MaterialSearchBar materialSearchBar;
    @BindView(R.id.recycler_movie)
    RecyclerView recyclerView;
    @BindView(R.id.txt_hint)
    TextView txthint;
    MovieInterface movieService;
    Call<Movie> movieCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupSearchBar();
        setupList();
        if(savedInstanceState != null){
            movieList = savedInstanceState.
                    getParcelableArrayList(UtilsConstant.MOVIE_LIST_INSTANCE);
            movieAdapter.setMovieResult(movieList);
            if(!savedInstanceState.getBoolean(UtilsConstant.MOVIE_POPULAR_BOOL)){
            txthint.setText(getApplicationContext().getResources().getString(
                    R.string.texthintresult, ((savedInstanceState.getInt(
                            UtilsConstant.MOVIE_LIST_TOTAL)== 0) ? "0" :
                            String.valueOf(savedInstanceState.getInt(
                            UtilsConstant.MOVIE_LIST_TOTAL))),
                            savedInstanceState.getString(UtilsConstant.MOVIE_LIST_QUERY)));
            }else{
                txthint.setText(R.string.texthintpopular);
            }
            recyclerView.setAdapter(movieAdapter);
        }else{
            getMovies();
        }

    }

    void setupList(){
        movieAdapter = new ItemMovieAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
    void setupSearchBar(){
        materialSearchBar.setOnSearchActionListener(this);
        materialSearchBar.inflateMenu(R.menu.main);
    }

    private void getMovies(String query){
        q = query;
        isPopular = false;
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieCall = movieService.getMovieBySearch(q, API_KEY);

        movieList = new ArrayList<>();

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
                totalResult = response.body().getTotalResults().intValue();
                txthint.setText(getApplicationContext().getResources().getString(
                        R.string.texthintresult, ((totalResult== 0) ? "0" :
                                String.valueOf(totalResult)), q));
                movieAdapter.setMovieResult(movieList);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMovies(){
        txthint.setText(R.string.texthintpopular);
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieCall = movieService.getPopularMovie(API_KEY);

        movieList = new ArrayList<>();

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
                movieAdapter.setMovieResult(movieList);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<MovieResult> movie = (ArrayList<MovieResult>) movieList;
        outState.putParcelableArrayList(UtilsConstant.MOVIE_LIST_INSTANCE, movie);
        outState.putInt(UtilsConstant.MOVIE_LIST_TOTAL, totalResult);
        outState.putString(UtilsConstant.MOVIE_LIST_QUERY, q);
        outState.putBoolean(UtilsConstant.MOVIE_POPULAR_BOOL, isPopular);
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
