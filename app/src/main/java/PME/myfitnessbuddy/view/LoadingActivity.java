package PME.myfitnessbuddy.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.myfitnessbuddy.R;

import java.util.Objects;
import java.util.Random;

import PME.myfitnessbuddy.storage.MyFitnessBuddyDatabase;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadingHandler handler = new LoadingHandler( this.findViewById( R.id.activity_loading_progressBar ) );
        handler.start();
    }

    class LoadingHandler extends Handler {
        int progress = 0;
        ProgressBar loadingBar;
        Random progressCalculator;

        public LoadingHandler( ProgressBar loadingBar )
        {
            super(Objects.requireNonNull(Looper.myLooper()));
            this.loadingBar = loadingBar;
            this.progressCalculator = new Random();
        }

        public void start() {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress += progressCalculator.nextInt(12);
                    loadingBar.setProgress( progress );

                    if( progress < 100 )
                        // We schedule another execution of this Runnable
                        LoadingHandler.this.postDelayed(this, 30 + progressCalculator.nextInt(120));
                    else {
                        // Loading finished - we launch the Start Activity
                        Intent i = new Intent(LoadingActivity.this, MainActivity.class);
                        startActivity( i );
                        finish(); // Clear this activity from back stack
                    }
                }
            }, 300); // Initial Delay of 300 ms
        }
    }

    }
