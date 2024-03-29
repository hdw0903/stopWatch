package com.example.a82104.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunnuing = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            isRunnuing = !isRunnuing

            if (isRunnuing) {
                start()
            } else {
                pause()
            }
        }

        lapButton.setOnClickListener {
            recordLpapTime()
        }

        resetButton.setOnClickListener {
            reset()
        }

    }

    private fun start() {
        fab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                if (isRunnuing) {
                    secTextView.text = "$sec"
                    milliTextView.text = "$milli"
                }
            }
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun recordLpapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap Lap : ${lapTime / 100}.${lapTime % 100}"

        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunnuing = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        lapLayout.removeAllViews()
        lap = 1
    }

}