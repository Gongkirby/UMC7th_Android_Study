package com.example.umc_week3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.umc_week3.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private val gson: Gson = Gson()

    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // JSON 데이터 가져오기
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        // 좋아요 상태 초기화
        isLiked = isLikedAlbum(album.id)

        // UI 초기화
        setInit(album)

        // 이벤트 리스너 설정
        setOnClickListener(album)

        // 뒤로가기 버튼
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()

        // 좋아요 아이콘 상태 설정
        if (isLiked) {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt(): Int {
        val spf = requireActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("jwt", 0) // SharedPreferences에서 JWT ID 가져오기
    }

    private fun likeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like) // 좋아요 추가
    }

    private fun isLikedAlbum(albumId: Int): Boolean {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val userId = getJwt()

        // 좋아요 여부 확인
        val likeId: Int? = songDB.albumDao().isLikedAlbum(userId, albumId)
        return likeId != null // 좋아요가 존재하면 true 반환
    }

    private fun disLikeAlbum(albumId: Int) {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId) // 좋아요 해제
    }

    private fun setOnClickListener(album: Album) {
        val userId = getJwt()

        // 좋아요 아이콘 클릭 리스너
        binding.albumLikeIv.setOnClickListener {
            if (isLiked) {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(album.id) // 좋아요 해제
            } else {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id) // 좋아요 추가
            }
            isLiked = !isLiked // 좋아요 상태 토글
        }
    }
}