package com.example.umc_week6_recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_week6_recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //더미 데이터
        val items = listOf(
            Item(R.drawable.title_1, R.drawable.pro_1, "마음이 조급할 때 듣는 브금", "조회수 91만회", "1:03:40"),
            Item(R.drawable.title_2, R.drawable.pro_2, "「피크민의 콧노래」 피아노 연주", "조회수 6.9만회", "0:45"),
            Item(R.drawable.title_3, R.drawable.pro_3, "음뽀챠무 『인형뽑기』", "조회수 149만회", "1:27"),
            Item(R.drawable.title_4, R.drawable.pro_2, "「친구모아 아파트」 링크의 노래", "조회수 171만회", "0:57"),
            Item(R.drawable.title_5, R.drawable.pro_2, "처음 만나는 피크민", "조회수 149만회", "1:27"),
            Item(R.drawable.title_6, R.drawable.pro_2, "젤다의 전설 지혜의 투영 CM』", "조회수 35만회", "0:30"),
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(items)
    }
}
