package com.rahmat.app.cataloguemovie.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.rahmat.app.cataloguemovie.MovieDetailActivity;
import com.rahmat.app.cataloguemovie.R;
import com.rahmat.app.cataloguemovie.model.MovieResult;

import java.util.Calendar;
import java.util.List;

import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.MOVIE_DETAIL;
import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.NOTIFICATION_CHANNEL_ID;
import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.NOTIFICATION_ID;

/**
 * Created by muhrahmatullah on 23/08/18.
 */
public class MovieUpcomingReceiver extends BroadcastReceiver{
    private static int notifId = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        String movieTitle = intent.getStringExtra("movietitle");
        int id = intent.getIntExtra("id", 0);
        long movieId = intent.getLongExtra("movieid", 0);
        String poster = intent.getStringExtra("movieposter");
        String back = intent.getStringExtra("movieback");
        String date = intent.getStringExtra("moviedate");
        Double rate = intent.getDoubleExtra("movierating", 0);
        String ovr = intent.getStringExtra("movieover");
        MovieResult movieResult = new MovieResult(movieId, movieTitle, poster, back, date, rate, ovr);
        String desc =String.valueOf(String.format(context.getString(R.string.release_today_msg), movieTitle));
        sendNotification(context, context.getString(R.string.app_name), desc, id, movieResult);
    }

    private void sendNotification(Context context, String title, String desc, int id, MovieResult movieResult) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_DETAIL, movieResult);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }

    public void setAlarm(Context context, List<MovieResult> movieResults) {
        int delay = 0;

        for (MovieResult movie : movieResults) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieUpcomingReceiver.class);
            intent.putExtra("movietitle", movie.getTitle());
            intent.putExtra("movieid", movie.getId());
            intent.putExtra("movieposter", movie.getPosterPath());
            intent.putExtra("movieback", movie.getBackdropPath());
            intent.putExtra("moviedate", movie.getReleaseDate());
            intent.putExtra("movierating", movie.getVoteAverage());
            intent.putExtra("movieover", movie.getOverview());
            intent.putExtra("id", notifId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay, pendingIntent);
            }
            notifId += 1;
            delay += 3000;
            Log.v("title", movie.getTitle());
        }
    }
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }
    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieUpcomingReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
