package com.eyyuperdogan.artbooktesting.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eyyuperdogan.artbooktesting.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var framentFactory: ArtFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory=framentFactory
        setContentView(R.layout.activity_main)
    }
}