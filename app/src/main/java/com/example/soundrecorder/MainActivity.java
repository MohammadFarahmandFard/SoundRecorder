package com.example.soundrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder;

    Button btnPlay, btnStart, btnStop;

    String outputFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay=findViewById(R.id.btnPlay);
        btnStart=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);

        btnStop.setEnabled(false);
        btnPlay.setEnabled(false);


        outputFile= Environment.getExternalStorageDirectory().getAbsolutePath()+"/recording.3gp";


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mediaRecorder=new MediaRecorder();

                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setOutputFile(outputFile);
                    mediaRecorder.prepare();
                    mediaRecorder.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                btnStart.setEnabled(false);
                btnPlay.setEnabled(false);
                btnStop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording Start", Toast.LENGTH_SHORT).show();

            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder=null;

                btnStart.setEnabled(true);
                btnPlay.setEnabled(true);
                btnStop.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Recording stoped!", Toast.LENGTH_SHORT).show();

            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(getApplicationContext(), "Audio is playing ...", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        
                        mediaPlayer.stop();

                        Toast.makeText(MainActivity.this, "Audio finished!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


}