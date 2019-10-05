package com.vngrs.githubchallange.search

class SearchContract {

    interface PresenterInterface {
        fun getSearchResults(query: String, page: String)
        fun stop()
    }
    interface ViewInterface {
        //TODO: add interface methods for View
    }
}