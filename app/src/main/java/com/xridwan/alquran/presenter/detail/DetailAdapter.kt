package com.xridwan.alquran.presenter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.databinding.AyatItemLayoutBinding
import com.xridwan.alquran.domain.model.Ayat

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    private val ayatList = arrayListOf<Ayat>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(list: MutableList<Ayat>) {
        ayatList.clear()
        ayatList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class DetailViewHolder(private val binding: AyatItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(surah: Ayat) {
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
        val binding =
            AyatItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
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