package com.xridwan.alquran.ui.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.alquran.R
import com.xridwan.alquran.data.source.local.entity.Surah
import com.xridwan.alquran.data.source.local.preference.HistoryPreference
import com.xridwan.alquran.data.viewmodel.SurahViewModel
import com.xridwan.alquran.databinding.ActivityMainBinding
import com.xridwan.alquran.ui.adapter.SurahAdapter
import com.xridwan.alquran.utils.Resource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var surahViewModel: SurahViewModel
    private lateinit var surahAdapter: SurahAdapter

    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0.0f

        getHistory()
        recyclerView()
        setupViewModel()
        getViewModel()
    }

    override fun onRestart() {
        super.onRestart()
        getHistory()
    }

    private fun getHistory() {
        val preference = HistoryPreference(this)
        val history = preference.getHistory()
        if (history.nama.toString().isNotEmpty()) {
            binding.apply {
                setForm()
                tvHistorySurah.text = "${history.nama} : ${history.last}"
                index = history.index!!

                tvNext.setOnClickListener {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, DetailActivity.DATA_1)
                    intent.putExtra("DATA_1", history)
                    startActivity(intent)
                }
            }
        } else {
            binding.apply {
                tvTitle.visibility = View.GONE
                tvHistorySurah.visibility = View.GONE
                tvNext.visibility = View.GONE
            }
        }
    }

    private fun setForm() {
        binding.apply {
            tvTitle.visibility = View.VISIBLE
            tvHistorySurah.visibility = View.VISIBLE
            tvNext.visibility = View.VISIBLE
        }
    }

    private fun recyclerView() {
        surahAdapter = SurahAdapter()
        surahAdapter.notifyDataSetChanged()

        binding.apply {
            rvSurah.layoutManager = LinearLayoutManager(this@MainActivity)
            rvSurah.setHasFixedSize(true)
            rvSurah.adapter = surahAdapter

            surahAdapter.setOnItemClickCallback(object : SurahAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Surah) {
                    selectedItem(data)
                }
            })
        }
    }

    private fun selectedItem(data: Surah) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, DetailActivity.DATA_2)
        intent.putExtra(
            "DATA_2",
            Surah(
                index = 0,
                arti = data.arti,
                nama = data.nama,
                ayat = data.ayat,
                type = data.type,
                nomor = data.nomor
            )
        )
        startActivity(intent)
    }

    private fun setupViewModel() {
        surahViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SurahViewModel::class.java)
    }

    private fun getViewModel() {
        surahViewModel.getSurah().observe(this, { state ->
            when (state.status) {
                Resource.Status.SUCCESS -> {
                    onLoading(false)
                    state.data?.let { it -> surahAdapter.setData(it) }
                }
                Resource.Status.LOADING -> {
                    onLoading(true)
                }
                Resource.Status.ERROR -> {
                    onLoading(false)
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        surahViewModel.setSurah()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.option_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Pencarian"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    surahAdapter.filter.filter("")
                } else {
                    try {
                        surahAdapter.filter.filter(newText)
                    } catch (e: Exception) {
                        e.message
                    }
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_doa) {
            startActivity(Intent(this, DoaActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onLoading(state: Boolean) {
        if (state) binding.progressSurah.visibility = View.VISIBLE
        else binding.progressSurah.visibility = View.GONE
    }
}