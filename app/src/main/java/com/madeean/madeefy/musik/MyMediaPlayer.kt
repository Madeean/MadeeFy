package com.madeean.madeefy.musik

import android.media.MediaPlayer

object MyMediaPlayer {
    var instance: MediaPlayer? = null

    @JvmName("getInstance1")
    fun getInstance(): MediaPlayer? {
        if (instance == null) {
            instance = MediaPlayer()
        }
        return instance
    }

    var currentIndex = -1
}