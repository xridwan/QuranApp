package com.xridwan.alquran.presenter.main

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.alquran.R
import com.xridwan.alquran.data.preference.Surah
import com.xridwan.alquran.databinding.ActivityMainBinding
import com.xridwan.alquran.domain.Resource
import com.xridwan.alquran.domain.model.Surat
import com.xridwan.alquran.presenter.detail.DetailActivity
import com.xridwan.alquran.presenter.doa.DoaActivity
import com.xridwan.alquran.utils.Constants.DATA_1
import com.xridwan.alquran.utils.Constants.DATA_2
import com.xridwan.alquran.utils.Constants.EXTRA_DATA
import com.xridwan.alquran.utils.Constants.EXTRA_DATA_1
import com.xridwan.alquran.utils.Constants.EXTRA_DATA_2
import com.xridwan.alquran.utils.hide
import com.xridwan.alquran.utils.show
import com.xridwan.alquran.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SurahViewModel by viewModel()
    private lateinit var surahAdapter: SurahAdapter
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0.0f

        getHistory()
        recyclerView()
        getData()
    }

    override fun onResume() {
        super.onResume()
        getHistory()
    }

    private fun getHistory() {
        val history = viewModel.mPrefs.getHistory()
        if (history.nama.toString().isNotEmpty()) setContent(history)
        else {
            binding.apply {
                tvTitle.hide()
                tvHistorySurah.hide()
                tvNext.hide()
            }
        }
    }

    private fun setContent(history: Surah) {
        binding.apply {
            tvTitle.show()
            tvHistorySurah.show()
            tvNext.show()
            tvHistorySurah.text = "${history.nama} : ${history.last}"
            index = history.index ?: 0

            tvNext.setOnClickListener {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DATA, DATA_1)
                intent.putExtra(EXTRA_DATA_1, history)
                startActivity(intent)
            }
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
                override fun onItemClicked(data: Surat) {
                    selectedItem(data)
                }
            })
        }
    }

    private fun selectedItem(data: Surat) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_DATA, DATA_2)
        intent.putExtra(
            EXTRA_DATA_2,
            Surah(
                index = 0,
                arti = data.arti,
                nama = data.nama,
                ayat = data.ayat.toString(),
                type = data.type,
                nomor = data.nomor
            )
        )
        startActivity(intent)
    }

    private fun getData() {
        viewModel.surat().observe(this) { state ->
            when (state) {
                is Resource.Success -> {
                    onLoading(false)
                    state.data?.let { it -> surahAdapter.setData(it as MutableList<Surat>) }
                }
                is Resource.Loading -> {
                    onLoading(true)
                }
                is Resource.Error -> {
                    onLoading(false)
                    showToast(state.message)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.option_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.label_search)
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
        when (item.itemId) {
            R.id.option_doa -> startActivity(Intent(this, DoaActivity::class.java))
            R.id.option_reset -> resetDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun resetDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                viewModel.mPrefs.setHistory(
                    Surah(
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        0
                    )
                )
                getHistory()
                showToast("Berhasil dibersihkan")
            }
            .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun onLoading(state: Boolean) {
        if (state) binding.progressSurah.show()
        else binding.progressSurah.hide()
    }
}