package com.example.umc_week3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_week3.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), SignUpView {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            Log.d("SignUpActivity", "Sign Up Button Clicked")
            signUp()
        }
    }

    private fun getUser() : User {
        val email : String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd : String = binding.signUpPasswordEt.text.toString()
        var name : String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name)
    }

    private fun signUp() : Boolean {
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        if(binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return false
        }

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signup(getUser())
        return true
    }

    override fun onSignupSuccess() {
        finish()
    }

    override fun onSignupFailure() {
    }
}