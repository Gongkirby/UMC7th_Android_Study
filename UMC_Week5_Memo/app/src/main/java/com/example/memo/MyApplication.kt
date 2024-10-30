package com.example.memo

import android.app.Application

class MyApplication : Application() {
    var noteContent: String? = null  // 메모 내용을 저장할 전역 변수
}
