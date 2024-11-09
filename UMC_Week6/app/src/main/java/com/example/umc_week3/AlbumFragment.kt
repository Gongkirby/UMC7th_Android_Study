package com.example.umc_week3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.umc_week3.databinding.FragmentAlbumBinding
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumToJson = arguments?.getString("album")
        val album = gson.fromJson(albumToJson, Album::class.java)
        setInit(album)

        binding.albumBackIv.setOnClickListener {
            //Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun setInit(album : Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
    }
}