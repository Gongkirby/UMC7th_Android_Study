package com.example.umc_week1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_week1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, SubActivity::class.java)

        // 버튼 리스트를 뷰 바인딩을 통해 가져옴
        val buttons = listOf(
            binding.butNomal,
            binding.butAngry,
            binding.butAnxiety,
            binding.butExcited,
            binding.butHappy
        )

        // 각 버튼 클릭 시 SubActivity로 이동
        buttons.forEach { button ->
            button.setOnClickListener {
                startActivity(intent)
            }
        }
    }
}
