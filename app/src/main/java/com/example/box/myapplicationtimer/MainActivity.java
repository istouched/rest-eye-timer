package com.example.box.myapplicationtimer;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int breakTime = 1200000;
    private boolean start = false;
    public static short count = 2;
    public static String title, content;
    public static short countStr = 2;

    private TextView textView;
    private TextView textView2;
    private TextView textViewSmall;
    private TextView textViewBig;
    private Button button;

    private CountDownTimer timer;
    Context context;
    AlarmReceiver alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textViewSmall = (TextView)findViewById(R.id.textViewSmall);
        textViewBig = (TextView)findViewById(R.id.textViewBig);
        button = (Button)findViewById(R.id.button);

        alarm = new AlarmReceiver();
        context = this.getApplicationContext();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!start) {
                        breakTime = 1200000;
                        alarm.SetAlarm(context);
                        showTimer(breakTime);
                        start = true;
                        button.setText("СТОП");
                    }else {
                        alarm.CancelAlarm(context);
                        timer.cancel();
                        start = false;
                        button.setText("СТАРТ");
                    }
                } catch (NumberFormatException e) {}
            }
        });
    }

    private void showTimer(final int countdownMillis){
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(countdownMillis, 1000) {
            @Override
            public void onTick(long millisSec) {
                textView.setText("До короткого перерыва осталось:");
                textViewSmall.setText("" + millisSec/60000 + ":" + (millisSec%60000)/1000);
                textView2.setText("До большого перерыва осталось: ");
                textViewBig.setText("" + (count*20+millisSec/60000) + ":" + (millisSec%60000)/1000);
            }

            @Override
            public void onFinish() {
                count--;
                if (count < 0){
                    breakTime = 1800000;
                    showTimer(breakTime);
                    count = 2;
                } else {
                    breakTime = 1200000;
                    showTimer(breakTime);
                }
            }
        }.start();
    }
}
