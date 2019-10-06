package com.vngrs.githubchallange.model

import com.vngrs.githubchallange.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ProfileDataSource {

    fun getProfileResultObservable(username: String): Observable<UserResponse> {
        return RetrofitClient.retrofit
            .getUserProfile(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getRepositoriesResultObsarvable(username: String, page: String): Observable<List<Repository>> {
        return RetrofitClient.retrofit
            .getUserRepositories(username = username, page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}