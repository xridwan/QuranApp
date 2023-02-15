package com.xridwan.alquran.presenter.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.R
import com.xridwan.alquran.data.local.entity.Surah
import com.xridwan.alquran.databinding.SurahItemLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class SurahAdapter : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>(), Filterable {
    private val surahList = arrayListOf<Surah>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    var filterList = ArrayList<Surah>()

    init {
        filterList = surahList
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(list: MutableList<Surah>) {
        surahList.clear()
        surahList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = SurahItemLayoutBinding.bind(itemView)
        fun bind(surah: Surah) {
            with(binding) {
                tvNomor.text = surah.nomor
                tvTitle.text = surah.nama
                tvAsma.text = surah.asma
                tvType.text = surah.type

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(surah)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.surah_item_layout, parent, false)
        return SurahViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        holder.bind(filterList[position])
        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int = filterList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Surah)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint.toString()
                filterList = if (search.isEmpty()) {
                    surahList
                } else {
                    val results = ArrayList<Surah>()
                    for (row in surahList) {
                        if (row.nama?.toLowerCase(Locale.getDefault())
                                ?.contains(
                                    constraint.toString().toLowerCase(Locale.getDefault())
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
                filterList = results?.values as ArrayList<Surah>
                notifyDataSetChanged()
            }
        }
    }
}