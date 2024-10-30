package com.example.memo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var editTextMemo: EditText
    private var savedMemo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextMemo = binding.editTextNote
        val btnNext: Button = binding.buttonNext

        btnNext.setOnClickListener {
            showSaveDialog()
        }
    }

    private fun showSaveDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("메모 확인")
            setMessage("다시 작성하시겠습니까?")
            setPositiveButton("예") { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton("아니오") { dialog, _ ->
                val memoContent = editTextMemo.text.toString()
                val intent = Intent(this@MainActivity, ConfirmationActivity::class.java).apply {
                    putExtra("memo", memoContent)
                }
                startActivity(intent)
                dialog.dismiss()
            }
            show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!savedMemo.isNullOrEmpty()) {
            editTextMemo.setText(savedMemo)
        }
    }

    override fun onPause() {
        super.onPause()
        savedMemo = editTextMemo.text.toString()
    }
}
