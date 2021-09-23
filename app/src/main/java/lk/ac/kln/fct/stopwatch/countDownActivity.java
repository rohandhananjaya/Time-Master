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

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class countDownActivity extends AppCompatActivity {

    //Declaring the elements
    Button stpWatch;
    Button btnStart;
    TextView txtView;
    LinearLayout txtArea;
    EditText txtSec;
    EditText txtMin;
    EditText txtHour;

    //Background running task
    Handler handler;

    int stSec, stMin, stHr=0;
    long cDowntime=0L;
    boolean isRun=false;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        Objects.requireNonNull(getSupportActionBar()).hide(); //Hide the title bar
        handler=new Handler();
        //Initializing the elements
        stpWatch =(Button) findViewById(R.id.btnStopwatch);
        btnStart = (Button) findViewById(R.id.btnStart);
        txtView = (TextView) findViewById(R.id.timeView);
        txtArea = (LinearLayout) findViewById(R.id.setTextarea);
        txtHour = (EditText) findViewById(R.id.txtHour);
        txtMin = (EditText) findViewById(R.id.txtMinute);
        txtSec = (EditText) findViewById(R.id.txtSec);

        //Back to stopwatch
        stpWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(countDownActivity.this, stopWatchActivity.class);
                startActivity(intent);
            }
        });

        //Start Stop button
        btnStart.setOnClickListener(view -> {

            if (!isRun){
                //Make edit text visible off and start displaying count down text
                txtView.setVisibility(view.VISIBLE);
                txtArea.setVisibility(view.GONE);

                //Get edit text boxes values and assign them to variables
                stSec = Integer.parseInt(String.valueOf(txtSec.getText()));
                stMin = Integer.parseInt(String.valueOf(txtMin.getText()));
                stHr = Integer.parseInt(String.valueOf(txtHour.getText()));
                //Convert HH:MM:SS values to seconds
                cDowntime = stHr*3600+stMin*60+stSec;
                //Stating the timer
                handler.postDelayed(runnable,0);
                //Change start button text to stop
                btnStart.setText("Stop");
                isRun=true;
            }else{
                //Stop the counter and set back to normal
                isRun=false;
                handler.removeCallbacks(runnable);
                txtView.setVisibility(view.GONE);
                txtArea.setVisibility(view.VISIBLE);
                txtSec.setText("00");
                txtMin.setText("00");
                txtHour.setText("00");
                btnStart.setText("Start");
            }
        });

        EditText txtSec = (EditText) findViewById(R.id.txtSec);
        EditText txtMinuit = (EditText) findViewById(R.id.txtMinute);
        EditText txtHour = (EditText) findViewById(R.id.txtHour);
        //Set up the max values and min values for the edit text boxes
        txtSec.setFilters(new InputFilter[]{new MinMaxFilter("0","59")});
        txtMinuit.setFilters(new InputFilter[]{new MinMaxFilter("0","59")});
        txtHour.setFilters(new InputFilter[]{new MinMaxFilter("0","99")});


    }


    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                handler.postDelayed(this, 1000);
                if(cDowntime>0) {
                    //Subtract total seconds value from one by one
                    cDowntime--;
                    //Divide seconds to Hours, minute and seconds
                    stHr = (int) (cDowntime / 3600);
                    stMin = (int) (cDowntime % 3600) / 60;
                    stSec = (int) ((cDowntime % 60));
                    //Display the time
                    txtView.setText(String.format("%02d", stHr) + ":" + String.format("%02d", stMin) + ":" + String.format("%02d", stSec));
                }else{
                    txtView.setText("Times up!");
                    handler.removeCallbacks(runnable);
                    btnStart.setText("Reset");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };


}