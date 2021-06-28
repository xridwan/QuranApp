package com.xridwan.alquran.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.R
import com.xridwan.alquran.data.source.local.entity.Surah
import com.xridwan.alquran.databinding.AyatItemLayoutBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    private val ayatList = arrayListOf<Surah>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(list: MutableList<Surah>) {
        ayatList.clear()
        ayatList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = AyatItemLayoutBinding.bind(itemView)
        fun bind(surah: Surah) {
            with(binding) {
                tvAyat.text = surah.ar
                tvTranslate.text = surah.id
                tvNomor.text = surah.nomor
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ayat_item_layout, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        holder.bind(ayatList[position])
        holder.itemView.animation = animation

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = ayatList.size
}