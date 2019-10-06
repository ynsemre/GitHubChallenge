package com.vngrs.githubchallange.repository

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vngrs.githubchallange.model.Repository
import androidx.databinding.DataBindingUtil
import com.vngrs.githubchallange.R
import com.vngrs.githubchallange.databinding.ActivityRepositoryBinding

class RepositoryActivity : AppCompatActivity(), RepositoryContract.ViewInterface {

    private lateinit var repositoryPresenter: RepositoryContract.PresenterInterface

    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = intent.getParcelableExtra(KEY_REPOSITORY)
        repositoryPresenter = RepositoryPresenter(this)

        //Set repository name as a Toolbar title.
        title = repository.name

        val binding = DataBindingUtil
            .setContentView<ActivityRepositoryBinding>(
                this,
                R.layout.activity_repository
            )
        binding.repository = repository
        binding.presenter = repositoryPresenter as RepositoryPresenter
    }

    override fun showProfile() {
        //TODO: redirect to user profile screen when implemented Profile screen
    }

    companion object {
        private const val KEY_REPOSITORY = "KEY_REPOSITORY"

        fun newIntent(context: Context, repository: Repository): Intent {
            return Intent(context, RepositoryActivity::class.java).apply {
                putExtra(KEY_REPOSITORY, repository)
            }
        }
    }

}