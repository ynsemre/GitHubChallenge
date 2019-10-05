package com.vngrs.githubchallange.network

import com.vngrs.githubchallange.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("per_page") perPage: String = "10",
        @Query("page") page: String
    ): Observable<SearchResponse>

}