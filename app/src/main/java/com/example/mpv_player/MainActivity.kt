package com.example.mpv_player

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.sl)

    private lateinit var fab_play: FloatingActionButton
    private lateinit var fab_pause: FloatingActionButton
    private lateinit var fab_stop: FloatingActionButton
    private lateinit var seekbar: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_play = findViewById(R.id.fab_play)
        fab_pause = findViewById(R.id.fab_pause)
        fab_stop = findViewById(R.id.fab_stop)
        seekbar = findViewById(R.id.seekbar)

        controlSound(currentSong[0])
    }

    private fun controlSound(id: Int) {
        fab_play.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                initialiseSeekBar()
            }
            mp?.start()
        }

        fab_pause.setOnClickListener {
            mp?.pause()
        }

        fab_stop.setOnClickListener {
            if (mp != null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No action needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No action needed
            }
        })
    }

    private fun initialiseSeekBar() {
        if (mp != null) {
            seekbar.max = mp!!.duration

            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        seekbar.progress = mp!!.currentPosition
                        handler.postDelayed(this, 1000)
                    } catch (e: java.lang.Exception) {
                        seekbar.progress = 0
                    }
                }
            }, 0)
        }
    }
}
