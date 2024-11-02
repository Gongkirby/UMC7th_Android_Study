package com.example.umc_week6_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 데이터 클래스 정의
data class Item(
    val thumbnail: Int,  // 썸네일 이미지 리소스 ID
    val profile: Int,    // 프로필 이미지 리소스 ID
    val title: String,   // 제목
    val views: String,   // 조회수
    val time: String     // 시간
)

class MyAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // ViewHolder 클래스 정의
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail_iv)
        val profile: ImageView = itemView.findViewById(R.id.profile_iv)
        val title: TextView = itemView.findViewById(R.id.title_tv)
        val views: TextView = itemView.findViewById(R.id.views_tv)
        val time: TextView = itemView.findViewById(R.id.time_tv)
    }

    // 아이템 레이아웃 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        holder.thumbnail.setImageResource(item.thumbnail) // 썸네일 이미지 설정
        holder.profile.setImageResource(item.profile)     // 프로필 이미지 설정
        holder.title.text = item.title                     // 제목 설정
        holder.views.text = item.views                     // 조회수 설정
        holder.time.text = item.time                       // 시간 설정
    }

    // 아이템 개수 반환
    override fun getItemCount(): Int {
        return itemList.size
    }
}