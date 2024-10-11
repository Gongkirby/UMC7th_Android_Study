package com.example.umc_week4_timer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_week4_timer.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mTimer: Timer

    private var isTimerRunning = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0

    private fun funTimer() {
        mTimer = timer(period = 10) {
            runOnUiThread {
                if (isTimerRunning) {
                    val currentTime = System.currentTimeMillis()
                    val totalTime = elapsedTime + (currentTime - startTime)
                    val minutes = (totalTime / 1000) / 60
                    val seconds = (totalTime / 1000) % 60
                    val milliseconds = (totalTime % 1000) / 10

                    binding.time.text = String.format("%02d:%02d.%02d", minutes, seconds, milliseconds)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pause.visibility = View.GONE

        binding.start.setOnClickListener {
            binding.start.visibility = View.GONE
            binding.pause.visibility = View.VISIBLE

            if (!isTimerRunning) {
                isTimerRunning = true
                startTime = System.currentTimeMillis()
                funTimer()
            }
        }

        binding.pause.setOnClickListener {
            binding.start.visibility = View.VISIBLE
            binding.pause.visibility = View.GONE

            if (isTimerRunning) {
                isTimerRunning = false
                elapsedTime += System.currentTimeMillis() - startTime
                mTimer.cancel()
            }
        }

        binding.clear.setOnClickListener {
            isTimerRunning = false
            elapsedTime = 0
            startTime = 0
            if (::mTimer.isInitialized) {
                mTimer.cancel()
            }
            binding.time.text = String.format("%02d:%02d.%02d", 0, 0, 0)
            binding.start.visibility = View.VISIBLE
            binding.pause.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mTimer.isInitialized) {
            mTimer.cancel()
        }
    }
}
