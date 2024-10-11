package com.example.umc_week3

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.flo.Song
import com.example.umc_week3.databinding.ActivityMainBinding
import com.example.umc_week3.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        val albumTitle = intent.getStringExtra("title")
        val albumSinger = intent.getStringExtra("singer")

        if (albumTitle != null && albumSinger != null) {
           // Toast.makeText(this, "$albumTitle", Toast.LENGTH_SHORT).show()
            binding.songMusicTitleTv.text = albumTitle
            binding.songSingerNameTv.text = albumSinger
        }
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = intent.getStringExtra("title")
        binding.songSingerNameTv.text = intent.getStringExtra("singer")
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
        else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime : Int, var isPlaying : Boolean = true):Thread() {
        private var second : Int = 0
        private var mills : Float = 0f

        override fun run(){
            super.run()
            while(true) {
                if(second >= playTime){
                    break
                }

                if (isPlaying){
                    sleep(50)
                    mills += 50

                    runOnUiThread {
                        binding.songProgressSb.progress = ((mills/playTime) * 100).toInt()
                    }

                    if(mills % 1000 == 0f){
                        runOnUiThread {
                            binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                        }
                        second++
                    }
                }
            }
        }
    }
}