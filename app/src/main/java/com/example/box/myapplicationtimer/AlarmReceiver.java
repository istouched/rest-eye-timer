package com.example.box.myapplicationtimer;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.box.myapplicationtimer.MainActivity.content;
import static com.example.box.myapplicationtimer.MainActivity.countStr;
import static com.example.box.myapplicationtimer.MainActivity.breakTime;
import static com.example.box.myapplicationtimer.MainActivity.title;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (countStr == 0){
            title = "Большой перерыв!";
            content = "10 минут!";
            countStr = 2;
            breakTime = 1800000;
        } else {
            title = "Короткий перерыв!";
            content = "15 секунд!";
            countStr--;
            breakTime = 1200000;
        }

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(content);

        Notification notification = builder.build();

        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(101, notification);
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmReceiver.class);
        intent.putExtra("onetime", Boolean.FALSE);
        PendingIntent pi= PendingIntent.getBroadcast(context,0, intent,0);
        am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+breakTime, breakTime,pi);
    }

    public void CancelAlarm(Context context)
    {
        Intent intent=new Intent(context, AlarmReceiver.class);
        PendingIntent sender= PendingIntent.getBroadcast(context,0, intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
