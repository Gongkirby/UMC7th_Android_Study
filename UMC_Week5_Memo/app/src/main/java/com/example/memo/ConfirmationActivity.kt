package com.example.memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memo.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MainActivity에서 전달된 메모 내용을 텍스트뷰에 반영
        val noteContent = intent.getStringExtra("memo")  // Intent에서 메모 내용 가져오기
        binding.textView.text = noteContent ?: "내용이 없습니다."  // 내용이 없으면 기본 메시지
    }
}
