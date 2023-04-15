package com.eyyuperdogan.artbooktesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.eyyuperdogan.artbooktesting.adapter.ArtRecyclerAdapter
import com.eyyuperdogan.artbooktesting.adapter.ImageRecyclerAdapter
import com.eyyuperdogan.artbooktesting.roomdb.Art
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val artImageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide: RequestManager
) :FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtFragment::class.java.name->ArtFragment(artRecyclerAdapter)
            ApiImageFragment::class.java.name->ApiImageFragment(artImageRecyclerAdapter)
            ArtDetailsFragment::class.java.name ->ArtDetailsFragment(glide)
            else->super.instantiate(classLoader, className)
        }
    }
}