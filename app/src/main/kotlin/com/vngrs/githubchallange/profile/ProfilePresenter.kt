package com.vngrs.githubchallange.profile

import android.util.Log
import com.vngrs.githubchallange.model.ProfileDataSource
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.UserResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(
    private var viewInterface: ProfileContract.ViewInterface,
    private var dataSource: ProfileDataSource
) : ProfileContract.PresenterInterface {

    private val TAG = ProfilePresenter::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val profileResultObserver: DisposableObserver<UserResponse>
        get() = object : DisposableObserver<UserResponse>() {

            override fun onNext(userResponse: UserResponse) {
                viewInterface.displayProfileResult(userResponse)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    val repositoriesResultObserver: DisposableObserver<List<Repository>>
        get() = object : DisposableObserver<List<Repository>>() {

            override fun onNext(response: List<Repository>) {
                viewInterface.displayRepositoriesResult(response)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    val profileResultObservable: (String) -> Observable<UserResponse> =
        { username -> dataSource.getProfileResultObservable(username) }

    val repositoriesResultObservable: (String, String) -> Observable<List<Repository>> =
        { username, page -> dataSource.getRepositoriesResultObsarvable(username, page) }

    override fun getProfileResult(username: String) {
        val profileResultDisposable = profileResultObservable(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(profileResultObserver)

        compositeDisposable.add(profileResultDisposable)
    }

    override fun getRepositoriesResult(username: String, page: String) {
        val repositoriesResultDisposable = repositoriesResultObservable(username, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(repositoriesResultObserver)

        compositeDisposable.add(repositoriesResultDisposable)
    }

}