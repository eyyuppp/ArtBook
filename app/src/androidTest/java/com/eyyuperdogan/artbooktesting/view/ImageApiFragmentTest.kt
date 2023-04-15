package com.eyyuperdogan.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eyyuperdogan.artbooktesting.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.eyyuperdogan.artbooktesting.adapter.ImageRecyclerAdapter
import com.eyyuperdogan.artbooktesting.getOrAwaitValueTestAndroid
import com.eyyuperdogan.artbooktesting.launchFragmentInHiltContainer
import com.eyyuperdogan.artbooktesting.repo.FakeArtRepositoryTestAndroid
import com.eyyuperdogan.artbooktesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageApiFragmentTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){
        val navController=Mockito.mock(NavController::class.java)
        val testViewModel=ArtViewModel(FakeArtRepositoryTestAndroid())
        val selectedImageUrl="TestTest.com"
        launchFragmentInHiltContainer<ApiImageFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
            viewModel=testViewModel

            imageRecyclerAdapter.imageList= listOf(selectedImageUrl)

        }
        onView(ViewMatchers.withId(R.id.recyclerViewSearch)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0,click()
            )
        )

        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.selectedImageUrl.getOrAwaitValueTestAndroid()).isEqualTo(selectedImageUrl)
    }


}