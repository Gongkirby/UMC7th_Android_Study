package com.example.umc_week3

interface LoginView {
    fun onLoginSuccess(code : Int, result: Result)
    fun onLoginFailure()
}