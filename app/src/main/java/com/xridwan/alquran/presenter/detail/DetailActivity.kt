package com.xridwan.alquran.presenter.detail

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.data.source.local.entity.Surah
import com.xridwan.alquran.data.source.local.preference.HistoryPreference
import com.xridwan.alquran.databinding.ActivityDetailBinding
import com.xridwan.alquran.utils.Resource

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var data: Surah

    private var intPosition: Int = 0

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val DATA_1 = 1
        const val DATA_2 = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.getIntExtra(EXTRA_DATA, 0)) {
            DATA_1 -> {
                data = intent.getParcelableExtra<Surah>("DATA_1") as Surah
                Log.e(TAG, "main: ${data.index}")
            }
            DATA_2 -> {
                data = intent.getParcelableExtra<Surah>("DATA_2") as Surah
                Log.e(TAG, "adapter: ${data.index}")
            }
        }

        Log.e(TAG, "onCreate: ${data.nama}")

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = data.nama
            elevation = 0.0f
        }

        binding.apply {
            tvNama.text = data.nama
            tvArti.text = data.arti
            tvType.text = data.type
            tvAyat.text = "${data.ayat} Ayat"
        }

        setupViewModel()
        detailViewModel.setDetail(data.nomor.toString())
        getViewModel()
        recyclerView()
    }

    private fun recyclerView() {
        detailAdapter = DetailAdapter()
        detailAdapter.notifyDataSetChanged()

        binding.apply {
            val linearLayoutManager = LinearLayoutManager(this@DetailActivity)
            rvAyat.layoutManager = linearLayoutManager
            rvAyat.setHasFixedSize(true)
            rvAyat.adapter = detailAdapter

            Handler(Looper.getMainLooper()).postDelayed({
                intPosition = data.index!!
                rvAyat.scrollToPosition(intPosition)

                Log.e(TAG, "onCreate: $intPosition")

                Toast.makeText(this@DetailActivity, "Berhasil Memuat", Toast.LENGTH_SHORT).show()
            }, 2000L)

            rvAyat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    intPosition = linearLayoutManager.findFirstVisibleItemPosition()
                }
            })

            detailAdapter.setOnItemClickCallback(object : DetailAdapter.OnItemClickCallback {
                override fun onItemClicked(position: Int) {
                    Log.e(TAG, "onItemClicked: $position")
                    selectedItem(position)
                }
            })
        }
    }

    private fun selectedItem(position: Int) {
        val historyPreference = HistoryPreference(this)
        historyPreference.setHistory(
            Surah(
                index = position,
                nomor = data.nomor,
                nama = data.nama,
                arti = data.arti,
                ayat = data.ayat,
                type = data.type,
                last = "${position + 1}"
            )
        )

        Log.e(TAG, "onDestroy: ${Surah(intPosition)}")
        Toast.makeText(this, "Menyimpan", Toast.LENGTH_SHORT).show()
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
    }

    private fun getViewModel() {
        detailViewModel.getDetail().observe(this) { state ->
            when (state.status) {
                Resource.Status.SUCCESS -> {
                    onLoading(false)
                    state.data?.let { it -> detailAdapter.setData(it) }
                }
                Resource.Status.LOADING -> {
                    onLoading(true)
                }
                Resource.Status.ERROR -> {
                    onLoading(false)
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onLoading(state: Boolean) {
        if (state) binding.progressAyat.visibility = View.VISIBLE
        else binding.progressAyat.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}