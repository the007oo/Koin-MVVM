package com.phattarapong.koin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.phattarapong.koin.network.Result
import com.phattarapong.koin.viewmodel.MainViewModel
import com.phattarapong.templatemvvm.database.CharacterLocal
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpObserveData()
    }

    private fun setUpObserveData() {
        viewModel.characterList.observe(this) {
            if (it is Result.Loading) {
                loadingLayout.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else if (it is Result.Success) {
                loadingLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                setUpAdapter(it.data)
            } else if (it is Result.Error) {
                loadingLayout.visibility = View.GONE
                recyclerView.visibility = View.GONE
                Snackbar.make(contentLayout, it.msg, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setUpAdapter(it: List<CharacterLocal>) {
        CharacterAdapter(it).apply {
            recyclerView.adapter = this
            recyclerView.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }
    }
}