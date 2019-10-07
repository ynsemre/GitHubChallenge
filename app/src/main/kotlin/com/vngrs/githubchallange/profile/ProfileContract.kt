package com.vngrs.githubchallange.profile

import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.UserResponse

class ProfileContract {

    interface PresenterInterface {
        fun getProfileResult(username: String)
        fun getRepositoriesResult(username: String, page: String)
        fun stop()
    }

    interface ViewInterface {
        fun displayProfileResult(userResponse: UserResponse)
        fun displayRepositoriesResult(repositoryList: List<Repository>)
        fun displayError(errorMessage: String)
    }

}