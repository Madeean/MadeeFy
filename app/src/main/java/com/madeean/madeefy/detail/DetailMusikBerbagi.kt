package com.madeean.madeefy.detail

import android.app.ProgressDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.madeean.madeefy.R
import com.madeean.madeefy.musik.MyMediaPlayer
import java.io.IOException

class DetailMusikBerbagi : AppCompatActivity() {

    lateinit var judulLagu:TextView
    lateinit var lottie:LottieAnimationView
    lateinit var seekbar:SeekBar
    lateinit var pause_play:ImageView
    lateinit var mediaPlayer:MediaPlayer

    lateinit var linkLagu:String
    lateinit var judul:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_musik_berbagi)

        judulLagu = findViewById(R.id.judul_lagu)
        lottie = findViewById(R.id.lottie_lagu)
        seekbar = findViewById(R.id.seek_bar)
        pause_play = findViewById(R.id.pause_play)

        mediaPlayer = MyMediaPlayer.getInstance()!!;
        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC)

        judulLagu.isSelected = true

        linkLagu = intent.getStringExtra("file").toString()
        judul = intent.getStringExtra("judul").toString()

        setResourcesWithMusic()

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    seekbar.setProgress(mediaPlayer.currentPosition)

                    if (mediaPlayer.isPlaying) {
                        pause_play.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)

                    } else {
                        pause_play.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                    }
                }
                Handler().postDelayed(this, 100)
            }
        })


    }

    private fun setResourcesWithMusic() {
        judulLagu.text = judul
        pause_play.setOnClickListener { pausePlay() }
        lottie.playAnimation()
        playMusic()
    }

    private fun playMusic() {
        val dialog = ProgressDialog(this@DetailMusikBerbagi)
        dialog.setMessage("Waiting")
        dialog.setCancelable(false)
        dialog.setInverseBackgroundForced(false)
        dialog.show()
        val judul = linkLagu
        val audioUri = Uri.parse(judul)
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(this, audioUri)
            mediaPlayer.prepare()
            dialog.hide()
            mediaPlayer.start()
            seekbar.setProgress(0)
            seekbar.setMax(mediaPlayer.duration)
        } catch (e: IOException) {
            dialog.hide()
            e.printStackTrace()
        }
    }

    private fun pausePlay() {
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
            lottie.pauseAnimation()
        } else{
            mediaPlayer.start()
            lottie.playAnimation()
        }
    }
}