package com.xridwan.alquran.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.R
import com.xridwan.alquran.data.source.remote.response.DataItem
import com.xridwan.alquran.databinding.DoaItemLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class DoaAdapter : RecyclerView.Adapter<DoaAdapter.DoaViewHolder>(), Filterable {
    private val doas = arrayListOf<DataItem>()

    var filterList = ArrayList<DataItem>()

    init {
        filterList = doas
    }

    fun setData(list: MutableList<DataItem>) {
        doas.clear()
        doas.addAll(list)
        notifyDataSetChanged()
    }

    class DoaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = DoaItemLayoutBinding.bind(itemView)
        fun bind(dataItem: DataItem) {
            with(binding) {
                tvTitle.text = dataItem.title
                tvAsma.text = dataItem.arabic
                tvArti.text = dataItem.translation
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.doa_item_layout, parent, false)
        return DoaViewHolder(view)
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
                    doas
                } else {
                    val results = ArrayList<DataItem>()
                    for (row in doas) {
                        if (row.title.toLowerCase(Locale.getDefault())
                                .contains(
                                    constraint.toString().toLowerCase(Locale.getDefault())
                                )
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
                filterList = results?.values as ArrayList<DataItem>
                notifyDataSetChanged()
            }
        }
    }
}