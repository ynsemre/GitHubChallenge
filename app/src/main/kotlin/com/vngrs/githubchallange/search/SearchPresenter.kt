package com.vngrs.githubchallange.search

import android.util.Log
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.SearchDataSource
import com.vngrs.githubchallange.model.SearchResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: SearchDataSource
) : SearchContract.PresenterInterface {

    private val TAG = SearchPresenter::class.java.simpleName
    private val compositeDisposable = CompositeDisposable()

    private var searchItemList = mutableListOf<Repository>()
    private var page = 1
    private var searchQuery: String? = null
    private var loading: Boolean = false

    val observer: DisposableObserver<SearchResponse>
        get() = object : DisposableObserver<SearchResponse>() {

            override fun onNext(@NonNull searchResponse: SearchResponse) {
                if (page == 1) {
                    searchItemList.clear()
                }
                searchResponse.items?.let {
                    searchItemList.addAll(it)
                    loading = false
                    page++
                }
                viewInterface.displaySearchResults(searchItemList)
            }

            override fun onError(@NonNull e: Throwable) {
                loading = false
                viewInterface.displayError("Error fetching search data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    val searchResultsObservable: (String, String) -> Observable<SearchResponse> =
        { query, page -> dataSource.searchResultsObservable(query, page) }

    override fun getNewQuerySearchResults(query: String) {
        searchQuery = query
        page = 1
        getSearchResults(query)
    }

    override fun getQueryNewPageSearchResults() {
        page ++
        getSearchResults(searchQuery!!)
        loading = true
    }

    private fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query, page.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }

    override fun clearSearchResults() {
        searchItemList.clear()
        page = 1
        searchQuery = null
        loading = false
        viewInterface.displaySearchResults(searchItemList)
    }

    override fun isLoading() = loading

    override fun getRepositoryList() = searchItemList

    override fun setRepositoryList(repositoryList: List<Repository>) {
        this.searchItemList = repositoryList as MutableList<Repository>
    }

    override fun getQuery() = searchQuery

    override fun setQuery(query: String?) {
        this.searchQuery = query
    }

    override fun getPageCount() = page

    override fun setPageCount(pageCount: Int) {
        this.page = pageCount
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}