package com.example.onlinecoachingapp.activities;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.onlinecoachingapp.R;

public class VideoPlayerActivity extends AppCompatActivity {

    private PlayerView playerView;

    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.playerView);

        String videoUrl =
                getIntent().getStringExtra("videoUrl");

        player = new ExoPlayer.Builder(this).build();

        playerView.setPlayer(player);

        MediaItem mediaItem =
                MediaItem.fromUri(Uri.parse(videoUrl));

        player.setMediaItem(mediaItem);

        player.prepare();

        player.play();

    }

    @Override
    protected void onStop() {

        super.onStop();

        if(player!=null){

            player.release();

        }

    }

}