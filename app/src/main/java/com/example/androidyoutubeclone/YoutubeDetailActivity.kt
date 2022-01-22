package com.example.androidyoutubeclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.example.androidyoutubeclone.databinding.ActivityYoutubeDetailBinding

class YoutubeDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityYoutubeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityYoutubeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val url = intent.getStringExtra("video_url")
        val mediaController = MediaController(this)
        binding.videoView.setVideoPath(url)
        binding.videoView.requestFocus()
        binding.videoView.start()
        mediaController.show()

        // Exoplayer - 전문적으로 영상을 다룰 때
        // drm: digital right management

    }
}