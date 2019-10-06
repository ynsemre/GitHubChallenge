package com.vngrs.githubchallange.search

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.model.SearchDataSource

class SearchActivity : AppCompatActivity(),
    SearchContract.ViewInterface,
    SearchView.OnQueryTextListener {

    private lateinit var searchPresenter: SearchContract.PresenterInterface

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupPresenter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchMenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchMenuItem.actionView as SearchView

        with(searchView) {
            queryHint = resources.getString(R.string.search_repositories_hint)
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@SearchActivity)

            setIconifiedByDefault(false)
            isFocusable = true
            requestFocus()
        }

        return true
    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }

    private fun setupPresenter() {
        val dataSource: SearchDataSource = SearchDataSource()
        searchPresenter = SearchPresenter(this, dataSource)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchPresenter.getSearchResults(query, page.toString())
        return true
    }
}
