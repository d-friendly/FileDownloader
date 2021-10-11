package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;
    private volatile boolean displayThread = false;
    private TextView downloadProgress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        downloadProgress = findViewById(R.id.downloadProgress);


    }


    public void mockFileDownloader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run(){
                startButton.setText("DOWNLOADING ....");
            }
        });

        for (int i = 0; i <= 100; i+=10) {
            if (stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                    }
                });
                return;
            }



            String progress = "Download Progress:  "+  i +"%";
            Log.d(TAG,progress);


            runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    downloadProgress.setText(progress);
                    if(displayThread){
                        downloadProgress.setVisibility(View.VISIBLE);
                    }else{
                        downloadProgress.setVisibility(View.INVISIBLE);
                    }
                }
            });


            try{
                Thread.sleep(1000);

            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run(){
                startButton.setText("Start");
                downloadProgress.setVisibility(View.INVISIBLE);;
            }
        });

    }



    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }

    public void startDownload(View view){
        stopThread = false;
        displayThread = true;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view){
        stopThread = true;
        displayThread = false;
    }
}
