package com.vngrs.githubchallange.search

import com.vngrs.githubchallange.model.SearchItem

class SearchContract {

    interface PresenterInterface {
        fun getSearchResults(query: String, page: String)
        fun stop()
    }
    interface ViewInterface {
        fun displaySearchResults(searchItemList: List<SearchItem>?)
        fun displayMessage(message: String)
        fun displayError(errorMessage: String)
    }
}