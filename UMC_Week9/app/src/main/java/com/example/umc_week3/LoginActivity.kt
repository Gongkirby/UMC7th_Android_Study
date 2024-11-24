package com.example.umc_week3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_week3.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd: String = binding.loginPasswordEt.text.toString()

        // Coroutine을 사용하여 데이터베이스 작업을 비동기적으로 처리합니다.
//        lifecycleScope.launch {
//            val user = withContext(Dispatchers.IO) {
//                val songDB = SongDatabase.getInstance(this@LoginActivity)!!
//                songDB.userDao().getUser(email, pwd)
//            }
//
//            if (user != null) {
//                Log.d("LoginActivity", user.id.toString())
//                saveJwt(user.id)
//                startMainActivity()
//            } else {
//                Toast.makeText(this@LoginActivity, "회원 정보가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
//            }
//        }

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email, password = "", ""))

        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun saveJwt(jwt: String) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(code : Int, result: Result) {
        when(code){
            1000 -> {
                saveJwt(result.jwt)
                startMainActivity()
            }
        }
    }

    override fun onLoginFailure() {
        //실패처리
    }
}
