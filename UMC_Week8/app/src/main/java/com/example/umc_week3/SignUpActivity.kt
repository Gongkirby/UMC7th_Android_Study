package com.example.umc_week3

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.umc_week3.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun getUser(): User {
        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()

        return User(email, pwd)
    }

    private fun signUp() {
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // Coroutine을 사용하여 비동기적으로 데이터베이스 작업 처리
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                val userDB = SongDatabase.getInstance(this@SignUpActivity)!!

                // 유저 삽입
                userDB.userDao().insert(getUser())

                // 유저 목록 조회
                userDB.userDao().getUsers()
            }

            // 결과 처리
            Log.d("sign-up", result.toString())
            Toast.makeText(this@SignUpActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

            // 회원가입 완료 후 액티비티 종료
            finish()
        }
    }
}
