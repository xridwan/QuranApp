package com.xridwan.alquran.presenter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.databinding.SurahItemLayoutBinding
import com.xridwan.alquran.domain.model.Surat
import java.util.*

class SurahAdapter : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>(), Filterable {
    private val surahList = arrayListOf<Surat>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    var filterList = ArrayList<Surat>()

    init {
        filterList = surahList
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(list: MutableList<Surat>) {
        surahList.clear()
        surahList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SurahViewHolder(private val binding: SurahItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(surat: Surat) {
            with(binding) {
                tvNomor.text = surat.nomor
                tvTitle.text = surat.nama
                tvAsma.text = surat.asma
                tvType.text = "${surat.type} - ${surat.ayat} Ayat"

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(surat)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val binding =
            SurahItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SurahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        holder.bind(filterList[position])
        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int = filterList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Surat)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint.toString()
                filterList = if (search.isEmpty()) {
                    surahList
                } else {
                    val results = ArrayList<Surat>()
                    for (row in surahList) {
                        if (row.nama?.lowercase(Locale.getDefault())
                                ?.contains(
                                    constraint.toString().lowercase(Locale.getDefault())
                                ) == true
                        ) {
                            results.add(row)
                        }
                    }
                    results
                }
                val filterResult = FilterResults()
                filterResult.values = filterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<Surat>
                notifyDataSetChanged()
            }
        }
    }
}