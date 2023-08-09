package com.mycompany.mpv_player

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.sl)

    private lateinit var durationTextView: TextView
    private lateinit var fab_play: FloatingActionButton
    private lateinit var btn: Button

    private lateinit var seekbar: SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        durationTextView = findViewById(R.id.durationTextView)
        fab_play = findViewById(R.id.fab_play)
        seekbar = findViewById(R.id.seekbar)

        btn = findViewById(R.id.btn)

        controlSound(currentSong[0])

        mp?.setOnCompletionListener {
            mp?.seekTo(0)
            mp?.start()
        }
    }



    private fun controlSound(id: Int) {
        var isPlaying = false

        fab_play.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                mp?.setOnCompletionListener {
                    mp?.seekTo(0)
                    mp?.start()
                }
                initialiseSeekBar()
            }

            if (!isPlaying) {
                mp?.start()
                isPlaying = true
                fab_play.setImageResource(R.drawable.baseline_pause_24) // Change the play button icon to pause
            } else {
                mp?.pause()
                isPlaying = false
                fab_play.setImageResource(R.drawable.baseline_play) // Change the pause button icon to play
            }
        }

btn.setOnClickListener{
    val intent = Intent(this@MainActivity, AllTheStuffActivity::class.java)
    startActivity(intent)
}

    // Rest of the code...
   /* }


    private fun controlSound(id: Int) {
        fab_play.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                initialiseSeekBar()
            }
            mp?.start()
        }*/

       /* fab_pause.setOnClickListener {
            mp?.pause()
        }*/

      /*  fab_stop.setOnClickListener {
            if (mp != null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }*/

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
                        val currentPosition = mp!!.currentPosition
                        seekbar.progress = currentPosition

                        // Update the durationTextView with the current position formatted as minutes:seconds
                        val minutes = currentPosition / 1000 / 60
                        val seconds = (currentPosition / 1000) % 60
                        durationTextView.text = String.format("%02d:%02d", minutes, seconds)

                        handler.postDelayed(this, 1000)
                    } catch (e: java.lang.Exception) {
                        seekbar.progress = 0
                    }
                }
            }, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.release()
        mp = null
    }


}
