package com.example.umc_week10

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        var keyHash = Utility.getKeyHash(this)
        Log.d("HashKey", keyHash)
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}