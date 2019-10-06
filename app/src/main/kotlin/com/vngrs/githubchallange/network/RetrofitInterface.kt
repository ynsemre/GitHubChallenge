package com.vngrs.githubchallange.network

import com.vngrs.githubchallange.model.UserResponse
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("per_page") perPage: String = "10",
        @Query("page") page: String
    ): Observable<SearchResponse>

    @GET("/users/{username}")
    fun getUserProfile(
        @Path("username") username: String
    ): Observable<UserResponse>

    @GET("/users/{username}/repos")
    fun getUserRepositories(
        @Path("username") username: String,
        @Query("per_page") perPage: String = "5",
        @Query("page") page: String
    ): Observable<List<Repository>>

}