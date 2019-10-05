package com.vngrs.githubchallange.model

import com.vngrs.githubchallange.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class SearchDataSource {

    fun searchResultsObservable(query: String, page: String): Observable<SearchResponse> {
        return RetrofitClient.retrofit
            .searchRepositories(query = query, page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}