package com.plango.app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plango.app.R
import com.plango.app.data.travel.TravelSummaryResponse
import com.plango.app.databinding.ItemTravelSummaryBinding

class TravelSummaryAdapter(
    private val onItemClick: (TravelSummaryResponse) -> Unit
) : RecyclerView.Adapter<TravelSummaryAdapter.TravelViewHolder>() {

    private val items = mutableListOf<TravelSummaryResponse>()

    fun submitList(list: List<TravelSummaryResponse>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class TravelViewHolder(
        private val binding: ItemTravelSummaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TravelSummaryResponse) {
            // 여행지 이름 (예: "서울 여행")
            binding.tvDestination.text = "${item.travelDest} 여행"
            
            // 날짜 범위 (예: "2024.01.01-2024.01.03")
            binding.tvDateRange.text = "${item.startDate}-${item.endDate}"
            
            // 썸네일 이미지 (기본 이미지 또는 나중에 서버에서 받은 이미지 사용)
            // TODO: 서버에서 썸네일 이미지 URL이 오면 Glide로 로드
            Glide.with(binding.root.context)
                .load(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(binding.imgThumbnail)

            // 삭제 아이콘은 일단 숨김 (나중에 필요하면 추가)
            binding.imgDelete.visibility = View.GONE

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        val binding = ItemTravelSummaryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TravelViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

