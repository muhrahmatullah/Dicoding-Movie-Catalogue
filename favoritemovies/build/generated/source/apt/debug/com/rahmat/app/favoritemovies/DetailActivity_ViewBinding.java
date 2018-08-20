// Generated code from Butter Knife. Do not modify!
package com.rahmat.app.favoritemovies;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailActivity_ViewBinding implements Unbinder {
  private DetailActivity target;

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailActivity_ViewBinding(DetailActivity target, View source) {
    this.target = target;

    target.tvOverview = Utils.findRequiredViewAsType(source, R.id.detail_overview_tv, "field 'tvOverview'", TextView.class);
    target.backDrop = Utils.findRequiredViewAsType(source, R.id.image_detail, "field 'backDrop'", ImageView.class);
    target.tvDate = Utils.findRequiredViewAsType(source, R.id.item_date_detail, "field 'tvDate'", TextView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.item_title_detail, "field 'tvTitle'", TextView.class);
    target.poster = Utils.findRequiredViewAsType(source, R.id.movie_poster_detail, "field 'poster'", ImageView.class);
    target.tvRating = Utils.findRequiredViewAsType(source, R.id.item_rating_detial, "field 'tvRating'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.floatingActionButton = Utils.findRequiredViewAsType(source, R.id.fab, "field 'floatingActionButton'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvOverview = null;
    target.backDrop = null;
    target.tvDate = null;
    target.tvTitle = null;
    target.poster = null;
    target.tvRating = null;
    target.toolbar = null;
    target.floatingActionButton = null;
  }
}
