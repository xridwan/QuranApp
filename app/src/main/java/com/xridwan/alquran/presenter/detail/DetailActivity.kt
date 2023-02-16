package com.xridwan.alquran.presenter.detail

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.alquran.R
import com.xridwan.alquran.data.preference.Surah
import com.xridwan.alquran.databinding.ActivityDetailBinding
import com.xridwan.alquran.domain.Resource
import com.xridwan.alquran.domain.model.Ayat
import com.xridwan.alquran.utils.Constants.DATA_1
import com.xridwan.alquran.utils.Constants.DATA_2
import com.xridwan.alquran.utils.Constants.EXTRA_DATA
import com.xridwan.alquran.utils.Constants.EXTRA_DATA_1
import com.xridwan.alquran.utils.Constants.EXTRA_DATA_2
import com.xridwan.alquran.utils.parcelable
import com.xridwan.alquran.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var data: Surah
    private var intPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.getIntExtra(EXTRA_DATA, 0)) {
            DATA_1 -> data = intent.parcelable<Surah>(EXTRA_DATA_1) as Surah
            DATA_2 -> data = intent.parcelable<Surah>(EXTRA_DATA_2) as Surah
        }

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

        getViewModel(data.nomor.toString())
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

            rvAyat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    intPosition = linearLayoutManager.findFirstVisibleItemPosition()
                }
            })

            detailAdapter.setOnItemClickCallback(object : DetailAdapter.OnItemClickCallback {
                override fun onItemClicked(position: Int) {
                    Log.e(TAG, "onItemClicked: $position")
                    saveDialog(position)
                }
            })
        }
    }

    private fun selectedItem(position: Int) {
        viewModel.mPrefs.setHistory(
            Surah(
                index = position,
                nomor = data.nomor,
                nama = data.nama,
                arti = data.arti,
                ayat = data.ayat,
                type = data.type,
                last = position + 1
            )
        )
        showToast("Berhasil menambahkan")
    }

    private fun getViewModel(nomor: String) {
        viewModel.ayat(nomor.toInt()).observe(this) { state ->
            when (state) {
                is Resource.Success -> {
                    onLoading(false)
                    state.data?.let { it -> detailAdapter.setData(it as MutableList<Ayat>) }
                    intPosition = data.index ?: 0
                    if (intPosition != 0) {
                        binding.rvAyat.scrollToPosition(intPosition)
                        showToast("Berhasil Memuat")
                    }
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

    private fun saveDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_save))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                selectedItem(position)
            }
            .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
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