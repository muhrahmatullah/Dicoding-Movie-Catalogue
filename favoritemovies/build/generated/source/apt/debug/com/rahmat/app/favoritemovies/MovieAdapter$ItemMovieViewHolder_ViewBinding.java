// Generated code from Butter Knife. Do not modify!
package com.rahmat.app.favoritemovies;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieAdapter$ItemMovieViewHolder_ViewBinding implements Unbinder {
  private MovieAdapter.ItemMovieViewHolder target;

  @UiThread
  public MovieAdapter$ItemMovieViewHolder_ViewBinding(MovieAdapter.ItemMovieViewHolder target,
      View source) {
    this.target = target;

    target.item_poster = Utils.findRequiredViewAsType(source, R.id.item_poster, "field 'item_poster'", ImageView.class);
    target.item_share = Utils.findRequiredViewAsType(source, R.id.icon_share, "field 'item_share'", ImageView.class);
    target.item_title = Utils.findRequiredViewAsType(source, R.id.movie_title, "field 'item_title'", TextView.class);
    target.item_rating = Utils.findRequiredViewAsType(source, R.id.movie_rating, "field 'item_rating'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieAdapter.ItemMovieViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.item_poster = null;
    target.item_share = null;
    target.item_title = null;
    target.item_rating = null;
  }
}
