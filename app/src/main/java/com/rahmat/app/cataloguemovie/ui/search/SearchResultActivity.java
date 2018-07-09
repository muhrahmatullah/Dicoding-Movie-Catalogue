package com.rahmat.app.cataloguemovie.ui.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.rahmat.app.cataloguemovie.R;
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

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.recycler_search)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_search)
    Toolbar toolbar;

    int columns = 0;
    ItemMovieAdapter movieAdapter;

    List<MovieResult> movieList;
    MovieInterface movieService;
    Call<Movie> movieCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent() != null){
            String q = getIntent().getStringExtra(UtilsConstant.INTENT_SEARCH);
            initView();
            getMovies(q);
        }
    }

    void initView(){
        columns = getResources().getInteger(R.integer.collumn_count);
        movieAdapter = new ItemMovieAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getMovies(final String q){
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieCall = movieService.getMovieBySearch(q, API_KEY);

        movieList = new ArrayList<>();

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
                getSupportActionBar().setSubtitle(getString(R.string.texthintresult, response.body()
                        .getTotalResults().toString(), q));
                movieAdapter.setMovieResult(movieList);
                recyclerView.setAdapter(movieAdapter);
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(SearchResultActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
