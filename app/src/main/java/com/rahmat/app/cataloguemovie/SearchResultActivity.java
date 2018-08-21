package com.rahmat.app.cataloguemovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.rahmat.app.cataloguemovie.adapter.ItemMovieAdapter;
import com.rahmat.app.cataloguemovie.model.Movie;
import com.rahmat.app.cataloguemovie.model.MovieFavorite;
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
import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.INTENT_DETAIL;
import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.INTENT_TAG;

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
    Call<MovieResult> movieFavoriteCall;
    MovieResult movieResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movieList = new ArrayList<>();
        movieResult = new MovieResult();
        initView();

        if(getIntent() != null){
            if(getIntent().getStringExtra(INTENT_TAG).equals("search")){
                if(savedInstanceState!=null){
                    ArrayList<MovieResult> list;
                    list = savedInstanceState.getParcelableArrayList("now_movie");
                    movieAdapter.setMovieResult(list);
                    recyclerView.setAdapter(movieAdapter);
                }else{
                    String q = getIntent().getStringExtra(UtilsConstant.INTENT_SEARCH);
                    getMovies(q);
                }
            }else{
                if(savedInstanceState!=null){
                    ArrayList<MovieResult> list;
                    list = savedInstanceState.getParcelableArrayList("now_movie");
                    Log.v("Isi list", ""+list.size());
                    movieAdapter.setMovieResult(list);
                    recyclerView.setAdapter(movieAdapter);
                }else{
                    getSupportActionBar().setTitle("Favorite Movie");
                    ArrayList<MovieFavorite> movieFavoriteArrayList = getIntent()
                            .getParcelableArrayListExtra(INTENT_DETAIL);
                    for(MovieFavorite mF : movieFavoriteArrayList){
                        getFavoriteMovies(mF.getId());
                    }
                }
            }
        }
    }

    void initView(){
        columns = getResources().getInteger(R.integer.collumn_count);
        movieAdapter = new ItemMovieAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getFavoriteMovies(String id){
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieFavoriteCall = movieService.getMovieById(id, API_KEY);

        movieFavoriteCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                movieList.add(response.body());
                movieAdapter.setMovieResult(movieList);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                movieResult = null;
            }
        });
    }

    private void getMovies(final String q){
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieCall = movieService.getMovieBySearch(q, API_KEY);

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("now_movie", new ArrayList<>(movieAdapter.getList()));
    }
}
