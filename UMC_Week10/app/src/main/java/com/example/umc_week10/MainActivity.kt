package com.example.umc_week10

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_week10.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 버튼 설정
        val loginButton: ImageButton = findViewById(R.id.loginBut)
        loginButton.setOnClickListener {
            kakaoLogin()
        }

        NaverIdLoginSDK.initialize(
            this,
            getString(R.string.naver_client_id),
            getString(R.string.naver_client_secret),
            getString(R.string.naver_client_name)
        )

        binding.buttonOAuthLoginImg.setOAuthLogin(object : OAuthLoginCallback {
            override fun onSuccess() {
                Log.d("test", "AccessToken : " + NaverIdLoginSDK.getAccessToken())
                Log.d("test", "client id : " + getString(R.string.naver_client_id))
                Log.d("test", "ReFreshToken : " + NaverIdLoginSDK.getRefreshToken())
                Log.d("test", "Expires : " + NaverIdLoginSDK.getExpiresAt().toString())
                Log.d("test", "TokenType : " + NaverIdLoginSDK.getTokenType())
                Log.d("test", "State : " + NaverIdLoginSDK.getState().toString())
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("test", "$errorCode $errorDescription")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })
    }

    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("Fail", "로그인 실패", error)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    } else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            this,
                            callback = mCallback
                        )
                    }
                } else if (token != null) {
                    Log.e("Success", "로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("Fail", "사용자 정보 요청 실패 $error")
                        } else if (user != null) {
                            Log.e("Success", "사용자 정보 요청 성공 : $user")
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
        }
    }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("Fail", "로그인 실패 $error")
        } else if (token != null) {
            Log.e("Success", "로그인 성공 ${token.accessToken}")
        }
    }
}

