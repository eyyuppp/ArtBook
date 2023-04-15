package com.eyyuperdogan.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eyyuperdogan.artbooktesting.MainCoroutineRule
import com.eyyuperdogan.artbooktesting.getOrAwaitValueTest
import com.eyyuperdogan.artbooktesting.repo.FakeArtRepository
import com.eyyuperdogan.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    private lateinit var viewModel: ArtViewModel

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()

    @Before
    fun setup(){
    viewModel= ArtViewModel(FakeArtRepository())
    }


    @Test
    fun `insert art without year returns error`(){
      viewModel.makeArt("eyup","vinci","")
        var value=viewModel.insertArtMessage.getOrAwaitValueTest()
         assertThat(value.status).isEqualTo(Status.ERROR)

    }
    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("eyup","","1800")
        var value=viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }
    @Test
    fun `insert art without artis returns error`(){
        viewModel.makeArt("","vinci","1800")
        var value=viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }
}