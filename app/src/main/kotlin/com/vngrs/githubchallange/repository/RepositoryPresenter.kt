package com.vngrs.githubchallange.repository

class RepositoryPresenter(
    private var viewInterface: RepositoryContract.ViewInterface
) : RepositoryContract.PresenterInterface {

    override fun showProfile() {
        viewInterface.showProfile()
    }
}