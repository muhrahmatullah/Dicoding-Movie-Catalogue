package com.rahmat.app.cataloguemovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahmat.app.cataloguemovie.R;
import com.rahmat.app.cataloguemovie.model.MovieResult;
import com.rahmat.app.cataloguemovie.utils.DateFormator;
import com.rahmat.app.cataloguemovie.utils.UtilsConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemMovieAdapter extends RecyclerView.Adapter<ItemMovieAdapter.ItemMovieViewHolder>{

    private List<MovieResult> movieResultList = new ArrayList<>();
    private Context context;

    public ItemMovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovieResult(List<MovieResult> movieResultList){
        this.movieResultList = movieResultList;
    }

    @Override
    public ItemMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemMovieViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ItemMovieViewHolder holder, int position) {
        holder.bindView(movieResultList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieResultList.size();
    }

    class ItemMovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_poster)
        ImageView item_poster;
        @BindView(R.id.item_date)
        TextView item_date;
        @BindView(R.id.item_rating)
        TextView item_rating;
        @BindView(R.id.item_title)
        TextView item_title;

        ItemMovieViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bindView(MovieResult movieResult){
            item_title.setText(movieResult.getTitle());
            item_rating.setText(itemView.getContext().getResources().getString(R.string.rating,
                    movieResult.getVoteAverage().toString()));
            item_date.setText(itemView.getContext().getResources().getString(R.string.release_date,
                    DateFormator.getDateDay(movieResult.getReleaseDate())));

            Picasso.with(itemView.getContext())
                    .load(UtilsConstant.BASE_POSTER_URL+movieResult.getPosterPath())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(item_poster);
        }

    }
}
