package com.rahmat.app.cataloguemovie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rahmat.app.cataloguemovie.adapter.ViewPagerAdapter;
import com.rahmat.app.cataloguemovie.database.MovieContract;
import com.rahmat.app.cataloguemovie.model.MovieFavorite;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.INTENT_DETAIL;
import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.INTENT_SEARCH;
import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.INTENT_TAG;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        setUpViewpager(viewPager);
        tab.setupWithViewPager(viewPager);

    }

    void setUpViewpager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.populateFragment(new NowFragment(), getString(R.string.txtnow));
        adapter.populateFragment(new UpcomingFragment(), getString(R.string.txtupcoming));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.setting){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            return true;
        }else if(id == R.id.favorite){
            ArrayList<MovieFavorite> movieFavoriteArrayList = new ArrayList<>();
            Cursor cursor = getContentResolver().query(MovieContract.CONTENT_URI, null,
                    null, null, null, null);
            cursor.moveToFirst();
            MovieFavorite favorite;
            if(cursor.getCount() > 0){
                do{
                    favorite = new MovieFavorite(cursor.getString(cursor.getColumnIndexOrThrow(
                            MovieContract.MovieColumns.MOVIE_ID)));
                    movieFavoriteArrayList.add(favorite);
                    cursor.moveToNext();
                }while(!cursor.isAfterLast());
            }
            Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
            intent.putExtra(INTENT_DETAIL, movieFavoriteArrayList);
            intent.putExtra(INTENT_TAG, "detail");
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                intent.putExtra(INTENT_SEARCH, query);
                intent.putExtra(INTENT_TAG, "search");
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }
}
