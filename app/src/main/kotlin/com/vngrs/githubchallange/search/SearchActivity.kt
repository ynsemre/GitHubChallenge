package com.vngrs.githubchallange.search

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.SearchDataSource
import com.vngrs.githubchallange.profile.ProfileActivity
import com.vngrs.githubchallange.repository.RepositoryActivity

private const val KEY_PAGE = "KEY_PAGE"
private const val KEY_QUERY = "KEY_QUERY"
private const val KEY_SEARCH_ITEM_LIST = "KEY_SEARCH_ITEM_LIST"

class SearchActivity : AppCompatActivity(),
    SearchContract.ViewInterface,
    SearchView.OnQueryTextListener {

    private lateinit var searchPresenter: SearchContract.PresenterInterface

    private lateinit var searchRepositoriesRecyclerView: RecyclerView
    private lateinit var searchRepositoriesAdapter: SearchAdapter
    private lateinit var searchView: SearchView

    private var searchItemList = mutableListOf<Repository>()
    private var page = 1
    private var searchQuery: String? = null
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupPresenter()
        initData(savedInstanceState)
        initUserInterface()
        initScrollListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_PAGE, page)
        outState.putParcelableArrayList(KEY_SEARCH_ITEM_LIST, ArrayList(searchItemList))
        outState.putString(KEY_QUERY, searchQuery)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchMenuItem = menu.findItem(R.id.menu_item_search)
        searchView = searchMenuItem.actionView as SearchView

        with(searchView) {
            queryHint = resources.getString(R.string.search_repositories_hint)
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@SearchActivity)

            setIconifiedByDefault(false)
            isFocusable = true

            if (!searchQuery.isNullOrEmpty()) {
                setQuery(searchQuery, false)
            }
        }

        return true
    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }

    private fun setupPresenter() {
        searchPresenter = SearchPresenter(this, SearchDataSource())
    }

    private fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList<Repository>(
                    KEY_SEARCH_ITEM_LIST) == null
            ) return
            page = savedInstanceState.getInt(KEY_PAGE)
            searchQuery = savedInstanceState.getString(KEY_QUERY)!!
            searchItemList = savedInstanceState.getParcelableArrayList<Repository>(
                KEY_SEARCH_ITEM_LIST)!!
        }
    }

    private fun initUserInterface() {
        searchRepositoriesRecyclerView =
            findViewById(R.id.activity_search_repositories_recyclerview)
        searchRepositoriesAdapter = SearchAdapter(searchItemList,
            avatarClickAction = { showProfile(it) },
            repositoryInfoClickAction = { showRepository(it) })
        searchRepositoriesRecyclerView.adapter = searchRepositoriesAdapter
    }

    private fun showProfile(userName: String) {
        startActivity(ProfileActivity.newIntent(this, userName))
    }

    private fun showRepository(repository: Repository) {
        startActivity(RepositoryActivity.newIntent(this, repository))
    }

    private fun initScrollListener() {
        searchRepositoriesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager =
                    searchRepositoriesRecyclerView.layoutManager as LinearLayoutManager
                if (!isLoading) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == searchItemList.size - 1) {
                        val pageCount = page + 1
                        searchPresenter.getSearchResults(searchQuery!!, pageCount.toString())
                        isLoading = true
                    }
                }
            }
        })
    }

    override fun displaySearchResults(repositoryList: List<Repository>?) {
        if (page == 1) {
            this.searchItemList.clear()
        }
        repositoryList?.let {
            this.searchItemList.addAll(it)
            searchRepositoriesAdapter.notifyDataSetChanged()
            isLoading = false
            page++
        }
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun displayError(errorMessage: String) {
        isLoading = false
        displayMessage(errorMessage)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        this.searchQuery = query
        this.page = 1
        searchPresenter.getSearchResults(query, page.toString())
        return false
    }
}
