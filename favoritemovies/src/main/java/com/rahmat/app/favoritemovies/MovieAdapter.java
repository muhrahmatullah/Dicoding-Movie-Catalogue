package com.rahmat.app.favoritemovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahmat.app.favoritemovies.model.MovieResult;
import com.rahmat.app.favoritemovies.rest.UtilsConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by muhrahmatullah on 09/08/18.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemMovieViewHolder>{

    private List<MovieResult> movieResultList = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ItemMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemMovieViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_favorite,
                        parent, false)
        );
    }

    public void setMovieResult(List<MovieResult> movieResult){
        this.movieResultList = movieResult;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ItemMovieViewHolder holder, int position) {
        holder.bindView(movieResultList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieResultList.size();
    }

    //View Holder
    class ItemMovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_poster)
        ImageView item_poster;
        @BindView(R.id.icon_share) ImageView item_share;
        @BindView(R.id.movie_title)
        TextView item_title;
        @BindView(R.id.movie_rating) TextView item_rating;

        ItemMovieViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
        public void bindView(final MovieResult movieResult){
            item_title.setText(movieResult.getTitle());
            item_rating.setText(movieResult.getVoteAverage().toString());
            Picasso.with(itemView.getContext())
                    .load(UtilsConstant.BASE_POSTER_URL+movieResult.getPosterPath())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(item_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra(UtilsConstant.MOVIE_DETAIL, movieResult);
                    itemView.getContext().startActivity(intent);
                }
            });
            item_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            context.getString(R.string.share, movieResult.getTitle()));
                    sendIntent.setType("text/plain");
                    itemView.getContext().startActivity(sendIntent);
                }
            });

        }

    }
}
