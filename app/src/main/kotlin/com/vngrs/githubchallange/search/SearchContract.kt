package com.vngrs.githubchallange.search

import com.vngrs.githubchallange.model.Repository

class SearchContract {

    interface PresenterInterface {
        fun getNewQuerySearchResults(query: String)
        fun getQueryNewPageSearchResults()
        fun clearSearchResults()
        fun isLoading(): Boolean
        fun getRepositoryList(): List<Repository>
        fun setRepositoryList(repositoryList: List<Repository>)
        fun getQuery(): String?
        fun setQuery(query: String?)
        fun getPageCount(): Int
        fun setPageCount(pageCount: Int)
        fun stop()
    }
    interface ViewInterface {
        fun displaySearchResults(repositoryList: List<Repository>?)
        fun displayMessage(message: String)
        fun displayError(errorMessage: String)
    }
}