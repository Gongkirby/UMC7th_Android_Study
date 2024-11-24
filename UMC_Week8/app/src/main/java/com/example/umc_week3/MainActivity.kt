package com.example.umc_week3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_week3.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song = Song()
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()

    private val getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if(result.resultCode == Activity.RESULT_OK){
        val album_title = result.data?.getStringExtra("album_title")
        if(album_title != null) Log.d("album_title", album_title)
        Toast.makeText(this@MainActivity, album_title, Toast.LENGTH_SHORT).show()
    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deleteDatabase("song-database")

        CoroutineScope(Dispatchers.IO).launch {
            inputDummyAlbums()
            inputDummySongs()
        }
//        songs.addAll(songDB.songDao().getSongs())

        initBottomNavigation()

//        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 0, 60, false, "music_lilac")

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)

//            getResultText.launch(intent)
            startActivity(intent)
        }

    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frm, SearchF)
//                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
    }

    fun updateMiniPlayer(album: Album) {
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
    }

    override fun onStart() {
        super.onStart()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!

        CoroutineScope(Dispatchers.IO).launch {
            song = if (songId == 0){
                songDB.songDao().getSong(1)
            } else{
                songDB.songDao().getSong(songId)
            }

            Log.d("song ID", song.id.toString())

            withContext(Dispatchers.Main){
                Log.d("song ID", song.id.toString())
                setMiniPlayer(song)
            }
        }
    }

    private suspend fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!

        CoroutineScope(Dispatchers.IO).launch {
            songDB.songDao().insert(
                Song(
                    "Lilac",
                    "아이유 (IU)",
                    R.drawable.img_album_exp2,
                    0,
                    200,
                    false,
                    "music_lilac",
                    false,
                    1
                )
            )

            songDB.songDao().insert(
                Song(
                    "Flu",
                    "아이유 (IU)",
                    R.drawable.img_album_exp2,
                    0,
                    200,
                    false,
                    "music_flu",
                    false,
                    1
                )
            )

            songDB.songDao().insert(
                Song(
                    "Butter",
                    "방탄소년단 (BTS)",
                    R.drawable.img_album_exp,
                    0,
                    190,
                    false,
                    "music_butter",
                    false,
                    0
                )
            )

            songDB.songDao().insert(
                Song(
                    "Next Level",
                    "에스파 (AESPA)",
                    R.drawable.img_album_exp3,
                    0,
                    210,
                    false,
                    "music_next",
                    false,
                    2
                )
            )


            songDB.songDao().insert(
                Song(
                    "Boy with Luv",
                    "music_boy",
                    R.drawable.img_album_exp4,
                    0,
                    230,
                    false,
                    "music_lilac",
                    false,
                    3
                )
            )


            songDB.songDao().insert(
                Song(
                    "BBoom BBoom",
                    "모모랜드 (MOMOLAND)",
                    R.drawable.img_album_exp5,
                    0,
                    240,
                    false,
                    "music_bboom",
                    false,
                    4
                )
            )

            val _songs = songDB.songDao().getSongs()
            Log.d("DB데이터", _songs.toString())
        }.join() // 이 코루틴이 완료될 때까지 대기
    }
    private suspend fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!

        val job = CoroutineScope(Dispatchers.IO).launch {
            val albums = songDB.albumDao().getAlbums()

            songDB.albumDao().insert(
                Album(
                    0,
                    "Butter",
                    "방탄소년단 (BTS)",
                    R.drawable.img_album_exp,
                )
            )
            songDB.albumDao().insert(
                Album(
                    1, "Lilac", "아이유 (IU)", R.drawable.img_album_exp2,
                )
            )
            songDB.albumDao().insert(
                Album(
                    2, "Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3,
                )
            )
            songDB.albumDao().insert(
                Album(
                    3, "Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4,
                )
            )
            songDB.albumDao().insert(
                Album(
                    4, "BBoom BBoom", " 모모랜드 (MOMOLAND)", R.drawable.img_album_exp5,
                )
            )
            songDB.albumDao().insert(
                Album(
                    5, "Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6,
                )
            )
            val _albums = songDB.albumDao().getAlbums()
            Log.d("DB앨범데이터", _albums.toString())
        }

        job.join() // inputDummyAlbums가 완료될 때까지 대기
    }
}