import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_week3.SavedAlbumRVAdapter
import com.example.umc_week3.SongDatabase
import com.example.umc_week3.databinding.FragmentSavedAlbumBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedAlbumFragment : Fragment() {
    lateinit var binding: FragmentSavedAlbumBinding
    lateinit var albumDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedAlbumBinding.inflate(inflater, container, false)

        albumDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val albumRVAdapter = SavedAlbumRVAdapter()

        albumRVAdapter.setMyItemClickListener(object : SavedAlbumRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                // 비동기 작업으로 getLikedAlbums 호출
                GlobalScope.launch(Dispatchers.Main) {
                    val likedAlbums = withContext(Dispatchers.IO) {
                        // Room 데이터베이스 작업을 백그라운드에서 실행
                        albumDB.albumDao().getLikedAlbums(getJwt())
                    }
                    // UI 스레드에서 RecyclerView 업데이트
                    albumRVAdapter.addAlbums(likedAlbums as ArrayList)
                }
            }
        })

        binding.lockerSavedSongRecyclerView.adapter = albumRVAdapter

        // getLikedAlbums 메서드를 비동기적으로 호출하여 RecyclerView에 데이터 추가
        GlobalScope.launch(Dispatchers.Main) {
            val likedAlbums = withContext(Dispatchers.IO) {
                albumDB.albumDao().getLikedAlbums(getJwt())
            }
            albumRVAdapter.addAlbums(likedAlbums as ArrayList)
        }
    }

    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)
        Log.d("MAIN_ACT/GET_JWT", "jwt_token: $jwt")

        return jwt
    }
}