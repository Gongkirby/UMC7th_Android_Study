package com.example.umc_week3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_week3.com.example.umc_week3.SongDatabase
import com.example.umc_week3.databinding.FragmentHomeBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
//    private var albumDates = ArrayList<Album>()

    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB : SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        albumDates.apply {
//            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
//        }

        songDB = SongDatabase.getInstance(requireContext())!!

        // Room 데이터베이스 작업을 백그라운드에서 수행하여 UI 스레드를 차단하지 않도록 합니다.
        CoroutineScope(Dispatchers.IO).launch {
            // 데이터베이스에서 앨범 데이터를 가져옵니다.
            val albums = songDB.albumDao().getAlbums()

            // UI 업데이트는 메인 스레드에서 수행해야 하므로 withContext(Dispatchers.Main)를 사용합니다.
            withContext(Dispatchers.Main) {
                albumDatas.addAll(albums)

                val albumRVAdapter = albumRVAdapter(albumDatas)
                binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
                binding.homeTodayMusicAlbumRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


                albumRVAdapter.setItemClickListener(object : albumRVAdapter.OnItemClickListener {
                    override fun onItemClick(album: Album) {
                        changeToAlbumFragment(album)
                    }
                })
            }
        }

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

    private fun changeToAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumToJson = gson.toJson(album)
                    putString("album", albumToJson)
                }
            })
            .commitAllowingStateLoss()

    }
}