package app.appplanet.com.mytoolbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.Calendar;

/**
 * Created by digivox on 15/02/16.
 */
public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button detail = (Button) getView().findViewById(R.id.button_detail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new DetailFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button transparent = (Button) getView().findViewById(R.id.button_transparent);
        transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new TransparentFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button compass = (Button) getView().findViewById(R.id.button_compass);
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new CompassV2Fragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button statusbar = (Button) getView().findViewById(R.id.button_statusbar);
        statusbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fm.beginTransaction().add(R.id.fl_content, new StatusBarFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button notify = (Button) getView().findViewById(R.id.button_notification);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupAlarmNotification();
            }
        });
    }

    private void setupAlarmNotification() {
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        alarmIntent.putExtra("name", "nordin");
        alarmIntent.putExtra("comment", "Hallo, hoe gaat het?");

        //Create an offset from the current time in which the alarm will go off.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);

        JodaTimeAndroid.init(getActivity());

        DateTime now = LocalTime.now().toDateTimeToday();
        DateTime alarm = now.plusSeconds(10);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, /*cal.getTimeInMillis()*/alarm.getMillis(), pendingIntent); //now+10 sec.
    }

    private void buildNotification() {
        /*
        Calendar now = GregorianCalendar.getInstance();
        int dayOfWeek = now.get(Calendar.DATE);
        if(dayOfWeek != 1 && dayOfWeek != 7) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Tijd om te bidden!")
                            .setContentText("Het is nu tijd om fadjr gebed te bidden.");

            //Intent resultIntent = new Intent(context, MainActivity.class);
            //PendingIntent resultPendingIntent = context.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }
        */
    }
}
