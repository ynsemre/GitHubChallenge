package com.vngrs.githubchallange.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
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
    private var repositoriesList = mutableListOf<Repository>()
    private lateinit var username: String
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        username = intent.getStringExtra(KEY_USERNAME)

        setupPresenter()
        initUserInterface()
    }

    override fun onStart() {
        super.onStart()
        profilePresenter.getProfileResult(username)
    }

    private fun setupPresenter() {
        profilePresenter = ProfilePresenter(this, ProfileDataSource())
    }

    private fun initUserInterface() {
        profileRecyclerView = findViewById(R.id.activity_profile_recyclerview)
        val dividerItemDecoration = DividerItemDecoration(this, VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.profile_item_divider, null))
        profileRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun displayProfileResult(userResponse: UserResponse) {
        this.user = userResponse
        if (page == 1) {
            profilePresenter.getRepositoriesResult(username, page.toString())
        }
    }

    override fun displayRepositoriesResult(repositoryList: List<Repository>) {
        page++
        if (repositoriesList.isNullOrEmpty()) {
            repositoriesList.addAll(repositoryList)
            profileAdapter = ProfileAdapter(user, repositoriesList)
            profileRecyclerView.adapter = profileAdapter
            return
        }

        repositoriesList.addAll(repositoryList)
        profileAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val KEY_USERNAME = "KEY_USERNAME"

        fun newIntent(context: Context, username: String): Intent {
            return Intent(context, ProfileActivity::class.java).apply {
                putExtra(KEY_USERNAME, username)
            }
        }
    }
}