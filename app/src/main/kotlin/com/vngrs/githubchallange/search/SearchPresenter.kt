package com.vngrs.githubchallange.search

import android.util.Log
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

    val observer: DisposableObserver<SearchResponse>
        get() = object : DisposableObserver<SearchResponse>() {

            override fun onNext(@NonNull searchResponse: SearchResponse) {
                viewInterface.displaySearchResults(searchResponse.items)
            }

            override fun onError(@NonNull e: Throwable) {
                viewInterface.displayError("Error fetching search data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    val searchResultsObservable: (String, String) -> Observable<SearchResponse> =
        { query, page -> dataSource.searchResultsObservable(query, page) }


    override fun getSearchResults(query: String, page: String) {
        val searchResultsDisposable = searchResultsObservable(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}