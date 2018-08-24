package com.rahmat.app.cataloguemovie.utils;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.rahmat.app.cataloguemovie.R;
import com.rahmat.app.cataloguemovie.model.Movie;
import com.rahmat.app.cataloguemovie.model.MovieResult;
import com.rahmat.app.cataloguemovie.notification.MovieDailyReceiver;
import com.rahmat.app.cataloguemovie.notification.MovieUpcomingReceiver;
import com.rahmat.app.cataloguemovie.rest.MovieClient;
import com.rahmat.app.cataloguemovie.rest.MovieInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.API_KEY;

/**
 * Created by muhrahmatullah on 23/08/18.
 */
public class SettingsPrefActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        SwitchPreference switchReminder;
        SwitchPreference switchToday;

        MovieDailyReceiver movieDailyReceiver = new MovieDailyReceiver();
        MovieUpcomingReceiver movieUpcomingReceiver = new MovieUpcomingReceiver();

        List<MovieResult> movieList;
        List<MovieResult> sameMovieList;
        MovieInterface movieService;
        Call<Movie> movieCall;



        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            movieList = new ArrayList<>();
            sameMovieList = new ArrayList<>();

            switchReminder = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));
            switchReminder.setOnPreferenceChangeListener(this);
            switchToday = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
            switchToday.setOnPreferenceChangeListener(this);


            Preference myPref = findPreference(getString(R.string.key_lang));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));

                    return true;
                }
            });

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean b = (boolean) newValue;

            if(key.equals(getString(R.string.key_today_reminder))){
                if(b){
                    movieDailyReceiver.setAlarm(getActivity());
                }else{
                    movieDailyReceiver.cancelAlarm(getActivity());
                }
            }else{
                if(b){
                    setReleaseAlarm();
                }else{
                    movieUpcomingReceiver.cancelAlarm(getActivity());
                }
            }

            return true;
        }

        private void setReleaseAlarm(){
            movieService = MovieClient.getClient().create(MovieInterface.class);
            movieCall = movieService.getUpcomingMovie(API_KEY);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String now = dateFormat.format(date);

            movieCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    movieList = response.body().getResults();
                    for(MovieResult movieResult : movieList){
                        if(movieResult.getReleaseDate().equals(now)){
                            sameMovieList.add(movieResult);
                            Log.v("adakah", ""+sameMovieList.size());
                        }
                    }
                    movieUpcomingReceiver.setAlarm(getActivity(), sameMovieList);

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong"
                            , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
