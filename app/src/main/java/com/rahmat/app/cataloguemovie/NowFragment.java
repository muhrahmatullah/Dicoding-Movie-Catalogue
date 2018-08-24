package com.rahmat.app.cataloguemovie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rahmat.app.cataloguemovie.adapter.ItemMovieAdapter;
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

import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.API_KEY;


public class NowFragment extends Fragment {


    public NowFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_movie_now)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    List<MovieResult> movieList;
    ItemMovieAdapter movieAdapter;
    int columns = 0;

    MovieInterface movieService;
    Call<Movie> movieCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_now, container, false);
        ButterKnife.bind(this, rootView);
        movieList = new ArrayList<>();
        initView();
        if(savedInstanceState!=null){
            ArrayList<MovieResult> list;
            list = savedInstanceState.getParcelableArrayList("now_movie");
            movieAdapter.setMovieResult(list);
            recyclerView.setAdapter(movieAdapter);
        }else{
            getMovies();
        }

        return rootView;
    }

    void initView(){
        columns = getResources().getInteger(R.integer.collumn_count);
        movieAdapter = new ItemMovieAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getMovies(){
        showPbar();
        movieService = MovieClient.getClient().create(MovieInterface.class);
        movieCall = movieService.getNowPlayingMovie(API_KEY);

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieList = response.body().getResults();
                Log.v("Matt", "Number of movie with  = "+response.body().getTotalResults());
                movieAdapter.setMovieResult(movieList);
                recyclerView.setAdapter(movieAdapter);
                hidePbar();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
                hidePbar();
            }
        });
    }

    void showPbar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    void hidePbar(){
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("now_movie", new ArrayList<>(movieAdapter.getList()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            ArrayList<MovieResult> list;
            list = savedInstanceState.getParcelableArrayList("now_movie");
            movieAdapter.setMovieResult(list);
            recyclerView.setAdapter(movieAdapter);
        }
    }
}
