package com.example.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        resetFab.setOnClickListener {
            reset()
        }

        lapButton.setOnClickListener {
            recordLapTime()
        }

    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)

        timerTask?.cancel()
    }

    private fun start() {
        fab.setImageResource(R.drawable.ic_baseline_pause_24)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                if (isRunning) {
                    secTextView.text = "$sec"
                    milliTextView.text = "$milli"
                }
            }
        }
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secTextView.text = "0"
        milliTextView.text = "00"

        lapLayout.removeAllViews()
        lap = 1
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}"

        lapLayout.addView(textView, 0)
        lap++
    }
}