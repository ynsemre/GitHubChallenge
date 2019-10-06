package com.vngrs.githubchallange.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.model.ProfileDataSource
import com.vngrs.githubchallange.model.Repository
import com.vngrs.githubchallange.model.UserResponse

class ProfileActivity : AppCompatActivity(),
    ProfileContract.ViewInterface {

    private lateinit var profilePresenter: ProfileContract.PresenterInterface

    private lateinit var username: String
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        username = intent.getStringExtra(KEY_USERNAME)

        setupPresenter()
    }

    override fun onStart() {
        super.onStart()
        profilePresenter.getProfileResult(username)
    }

    private fun setupPresenter() {
        profilePresenter = ProfilePresenter(this, ProfileDataSource())
    }

    override fun displayProfileResult(userResponse: UserResponse) {
        //TODO: will be handled later
        if (page == 1) {
            profilePresenter.getRepositoriesResult(username, page.toString())
        }
    }

    override fun displayRepositoriesResult(repositoryList: List<Repository>) {
        //TODO: will be handled later
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