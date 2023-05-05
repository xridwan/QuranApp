package com.xridwan.alquran.presenter.doa

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.data.remote.response.DoaResponse
import com.xridwan.alquran.databinding.DoaItemLayoutBinding
import com.xridwan.alquran.domain.model.Doa
import java.util.*

class DoaAdapter : RecyclerView.Adapter<DoaAdapter.DoaViewHolder>(), Filterable {
    private val doaList = arrayListOf<Doa>()

    var filterList = ArrayList<Doa>()

    init {
        filterList = doaList
    }

    fun setData(list: MutableList<Doa>) {
        doaList.clear()
        doaList.addAll(list)
        notifyDataSetChanged()
    }

    class DoaViewHolder(private val binding: DoaItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: Doa) {
            with(binding) {
                tvTitle.text = dataItem.doa
                tvAsma.text = dataItem.ayat
                tvArti.text = dataItem.artinya
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoaViewHolder {
        val binding =
            DoaItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoaViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        holder.itemView.animation = animation
        holder.bind(filterList[position])
    }

    override fun getItemCount(): Int = filterList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint.toString()
                filterList = if (search.isEmpty()) {
                    doaList
                } else {
                    val results = ArrayList<Doa>()
                    for (row in doaList) {
                        if (row.doa?.lowercase(Locale.getDefault())
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
                filterList = results?.values as ArrayList<Doa>
                notifyDataSetChanged()
            }
        }
    }
}