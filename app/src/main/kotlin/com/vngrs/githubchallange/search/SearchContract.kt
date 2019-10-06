package com.vngrs.githubchallange.search

import com.vngrs.githubchallange.model.Repository

class SearchContract {

    interface PresenterInterface {
        fun getSearchResults(query: String, page: String)
        fun stop()
    }
    interface ViewInterface {
        fun displaySearchResults(repositoryList: List<Repository>?)
        fun displayMessage(message: String)
        fun displayError(errorMessage: String)
    }
}