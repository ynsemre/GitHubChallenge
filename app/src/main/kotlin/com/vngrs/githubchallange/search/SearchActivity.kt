package com.vngrs.githubchallange.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.model.SearchDataSource

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {

    private lateinit var searchPresenter: SearchContract.PresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupPresenter()
    }

    override fun onStart() {
        super.onStart()
        //TODO: add real method parameters after search screen view implementation
        searchPresenter.getSearchResults("retrofit", "1")
    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }

    private fun setupPresenter() {
        val dataSource: SearchDataSource = SearchDataSource()
        searchPresenter = SearchPresenter(this, dataSource)
    }
}
