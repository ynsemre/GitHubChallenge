package com.vngrs.githubchallange.search

import com.vngrs.githubchallange.BaseTest
import com.vngrs.githubchallange.RxImmediateSchedulerRule
import com.vngrs.githubchallange.model.Owner
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.SearchDataSource
import com.vngrs.githubchallange.model.SearchResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: SearchContract.ViewInterface

    @Mock
    private val mockDataSource = SearchDataSource()

    lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        searchPresenter = SearchPresenter(viewInterface = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testSearchRepositories() {
        val dummySearchResponse = dummySearchResponse
        val searchDummyList = dummySearchList
        Mockito.doReturn(Observable.just(dummySearchResponse)).`when`(mockDataSource)
            .searchResultsObservable(anyString(), "3")

        searchPresenter.getSearchResults("s", "3")

        Mockito.verify(mockActivity).displaySearchResults(searchDummyList)
    }

    @Test
    fun testSearchRepositoryError() {
        Mockito.doReturn(
            Observable.error<Throwable>(Throwable("Something went wrong"))
        ).`when`(mockDataSource).searchResultsObservable(anyString(), "3")

        searchPresenter.getSearchResults("Art", "3")
        Mockito.verify(mockActivity).displayError("Error fetching search data")
    }

    private val dummySearchResponse: SearchResponse
        get() {
            val dummyRepositoryList = dummySearchList
            return SearchResponse(11, false, dummyRepositoryList)
        }

    private val dummySearchList: List<Repository>
        get() {
            val dummyRepositoryList = mutableListOf<Repository>()
            val dummyOwner = Owner("dummyOwner", "www.dummyurl.com")
            dummyRepositoryList.add(
                Repository(
                    name = "Name1",
                    fullName = "FullName1",
                    owner = dummyOwner,
                    description = "Description1",
                    defaultBranch = "master",
                    language = "kotlin",
                    forks = 13
                )
            )
            dummyRepositoryList.add(
                Repository(
                    name = "Name2",
                    fullName = "FullName2",
                    owner = dummyOwner,
                    description = "Description2",
                    defaultBranch = "master",
                    language = "kotlin",
                    forks = 12
                )
            )
            dummyRepositoryList.add(
                Repository(
                    name = "Name3",
                    fullName = "FullName3",
                    owner = dummyOwner,
                    description = "Description3",
                    defaultBranch = "master",
                    language = "kotlin",
                    forks = 50
                )
            )

            return dummyRepositoryList
        }

}