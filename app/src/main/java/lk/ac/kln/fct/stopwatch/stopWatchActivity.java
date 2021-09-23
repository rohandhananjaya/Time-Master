/**
 * <h1>Time Master</h1>
 * This is a simple app with stopwatch and a countdown timer'.
 * <p>
 *
 *
 * @author  		Amarasooriya K.R.D (CT/2017/006)
 * @version 		1.0
 * @since   		2021-09-24
 * @link            https://github.com/rohandhananjaya/Time-Master.git
 *
 */

package lk.ac.kln.fct.stopwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class stopWatchActivity extends AppCompatActivity {

    //Detecting variable for weather time counting start or not
    private boolean isResume;
    // For make runnable object
    Handler handler;
    //Timing variables
    long tMillSec, tStart, tBuff, tUpdate = 0L;
    int sec,min,milliSec;

    //Declaring the elements
    TextView viewTime;
    Button btnStart;
    Button btnReset;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide(); //Hide the title bar

        handler = new Handler();

        //Initializing the elements
        viewTime = findViewById(R.id.timeView);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnStop);
        Button btnCountDown = findViewById(R.id.btnCountDown);

        //On click function of start button
        btnStart.setOnClickListener(view -> {
            if(!isResume){  //Start if not resume

                tStart =SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                isResume = true;
                btnStart.setText(R.string.stpTxt); //Set start button text to stop
                btnReset.setEnabled(false); //Disable reset button

            }else{
                tBuff += tMillSec;
                handler.removeCallbacks(runnable); //Stop handler
                isResume = false;
                btnStart.setText("Resume");
                btnReset.setEnabled(true);
            }
        });

        //On click function of reset button. This will reset the stopwatch.
        btnReset.setOnClickListener(view -> {
            if (!isResume){
                tMillSec= 0L;
                tStart = 0L;
                tBuff = 0L;
                tUpdate = 0L;
                sec = 0;
                min =0;
                milliSec = 0;
                viewTime.setText("00:00:00");
                btnReset.setEnabled(false);
                btnStart.setText("Start");
            }
        });

        //Open the countdown activity
        btnCountDown.setOnClickListener(view -> {
            Intent intent = new Intent(stopWatchActivity.this, countDownActivity.class);
            startActivity(intent);
        });
    }

    //Background thread to run timer
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final TextView viewTime = (TextView) findViewById(R.id.timeView);
            try {
                //Calculating time
                tMillSec = SystemClock.uptimeMillis() - tStart;
                tUpdate = tBuff + tMillSec;
                sec = (int) (tUpdate/1000);
                min = sec/60;
                sec = sec%60;
                milliSec = (int) (tUpdate%100);
                //Set time text
                viewTime.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",milliSec));
                handler.postDelayed(this,60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}