package com.nesgorilla.feature.feed.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nesgorilla.R

class FeedListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_list)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}