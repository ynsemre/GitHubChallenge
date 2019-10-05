package com.vngrs.githubchallange.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vngrs.githubchallange.R

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}
