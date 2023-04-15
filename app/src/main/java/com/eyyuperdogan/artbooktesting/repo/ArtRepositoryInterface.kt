package com.eyyuperdogan.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.eyyuperdogan.artbooktesting.model.ImageResponse
import com.eyyuperdogan.artbooktesting.roomdb.Art
import com.eyyuperdogan.artbooktesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArt():LiveData<List<Art>>
    suspend fun searchImage(imageString: String):Resource<ImageResponse>
}