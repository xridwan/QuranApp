package com.xridwan.alquran.presenter.doa

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.alquran.R
import com.xridwan.alquran.databinding.ActivityDoaBinding
import com.xridwan.alquran.domain.Resource
import com.xridwan.alquran.domain.model.Doa
import com.xridwan.alquran.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoaBinding
    private val doaViewModel: DoaViewModel by viewModel()
    private lateinit var doaAdapter: DoaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.title_doa)
            elevation = 0.0f
        }

        recyclerView()
        getViewModel()
    }

    private fun recyclerView() {
        doaAdapter = DoaAdapter()
        doaAdapter.notifyDataSetChanged()

        binding.apply {
            rvDoa.layoutManager = LinearLayoutManager(this@DoaActivity)
            rvDoa.setHasFixedSize(true)
            rvDoa.adapter = doaAdapter
        }
    }

    private fun getViewModel() {
        doaViewModel.doa().observe(this) { state ->
            when (state) {
                is Resource.Success -> {
                    onLoading(false)
                    state.data?.let { it -> doaAdapter.setData(it as MutableList<Doa>) }
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
        menuInflater.inflate(R.menu.option_search, menu)
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
                    doaAdapter.filter.filter("")
                } else {
                    try {
                        doaAdapter.filter.filter(newText)
                    } catch (e: Exception) {
                        e.message
                    }
                }
                return false
            }
        })
        return true
    }

    private fun onLoading(state: Boolean) {
        if (state) binding.progressDoa.visibility = View.VISIBLE
        else binding.progressDoa.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}