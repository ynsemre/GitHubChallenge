package com.vngrs.githubchallange.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.model.ProfileDataSource
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.UserResponse

class ProfileActivity : AppCompatActivity(),
    ProfileContract.ViewInterface {

    private lateinit var profilePresenter: ProfileContract.PresenterInterface

    private lateinit var profileRecyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter

    private lateinit var user: UserResponse
    private lateinit var username: String

    private var repositoriesList = mutableListOf<Repository>()
    private var isLoading: Boolean = false
    private var repositoryCount: Int = 0
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        username = intent.getStringExtra(KEY_USERNAME)

        setupPresenter()
        initUserInterface()
        initScrollListener()

        if (savedInstanceState != null) {
            initData(savedInstanceState)

        } else {
            profilePresenter.getProfileResult(username)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_REPOSITORY_LIST, ArrayList(repositoriesList))
        outState.putInt(KEY_REPOSITORY_COUNT, repositoryCount)
        outState.putString(KEY_USERNAME, username)
        outState.putInt(KEY_PAGE, page)
        outState.putParcelable(KEY_USER, user)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        profilePresenter.stop()
    }

    private fun setupPresenter() {
        profilePresenter = ProfilePresenter(this, ProfileDataSource())
    }

    private fun initData(savedInstanceState: Bundle) {
        if (savedInstanceState.getParcelable<UserResponse>(
                KEY_USER
            ) == null
        ) return
        page = savedInstanceState.getInt(KEY_PAGE)
        repositoryCount = savedInstanceState.getInt(KEY_REPOSITORY_COUNT)
        username = savedInstanceState.getString(KEY_USERNAME)!!
        user = savedInstanceState.getParcelable(KEY_USER)!!
        repositoriesList = savedInstanceState.getParcelableArrayList<Repository>(
            KEY_REPOSITORY_LIST
        )!!

        profileAdapter = ProfileAdapter(user, repositoriesList)
        profileRecyclerView.adapter = profileAdapter
    }

    private fun initUserInterface() {
        profileRecyclerView = findViewById(R.id.activity_profile_recyclerview)
        val dividerItemDecoration = DividerItemDecoration(this, VERTICAL)
        dividerItemDecoration.setDrawable(
            resources.getDrawable(
                R.drawable.profile_item_divider,
                null
            )
        )
        profileRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun initScrollListener() {
        profileRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager =
                    profileRecyclerView.layoutManager as LinearLayoutManager

                if (!isLoading && repositoriesList.size < repositoryCount
                    && linearLayoutManager
                        .findLastCompletelyVisibleItemPosition() == repositoriesList.size
                ) {
                    val pageCount = page + 1
                    profilePresenter.getRepositoriesResult(username, pageCount.toString())
                    isLoading = true
                }
            }
        })
    }

    override fun displayProfileResult(userResponse: UserResponse) {
        this.user = userResponse
        this.repositoryCount = userResponse.publicRepos
        if (page == 1) {
            profilePresenter.getRepositoriesResult(username, page.toString())
        }
    }

    override fun displayRepositoriesResult(repositoryList: List<Repository>) {

        if (repositoriesList.isNullOrEmpty()) {
            repositoriesList.addAll(repositoryList)
            profileAdapter = ProfileAdapter(user, repositoriesList)
            profileRecyclerView.adapter = profileAdapter
            return
        }

        repositoriesList.addAll(repositoryList)
        profileAdapter.notifyDataSetChanged()

        page++
        isLoading = false
    }

    override fun displayError(errorMessage: String) {
        isLoading = false
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val KEY_REPOSITORY_COUNT = "KEY_REPOSITORY_COUNT"
        private const val KEY_REPOSITORY_LIST = "KEY_REPOSITORY_LIST"
        private const val KEY_USERNAME = "KEY_USERNAME"
        private const val KEY_USER = "KEY_USER"
        private const val KEY_PAGE = "KEY_PAGE"

        fun newIntent(context: Context, username: String): Intent {
            return Intent(context, ProfileActivity::class.java).apply {
                putExtra(KEY_USERNAME, username)
            }
        }
    }
}