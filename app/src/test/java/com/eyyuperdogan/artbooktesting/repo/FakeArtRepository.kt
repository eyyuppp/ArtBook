package com.eyyuperdogan.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eyyuperdogan.artbooktesting.model.ImageResponse
import com.eyyuperdogan.artbooktesting.roomdb.Art
import com.eyyuperdogan.artbooktesting.util.Resource

class FakeArtRepository:ArtRepositoryInterface {
    private val arts= mutableListOf<Art>()
    private val artsLiveData=MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(0,0, listOf()))
    }

    private fun refreshData(){
        artsLiveData.value=arts
    }
}