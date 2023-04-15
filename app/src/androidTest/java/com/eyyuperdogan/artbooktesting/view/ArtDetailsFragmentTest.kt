package com.eyyuperdogan.artbooktesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eyyuperdogan.artbooktesting.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.eyyuperdogan.artbooktesting.getOrAwaitValueTestAndroid
import com.eyyuperdogan.artbooktesting.launchFragmentInHiltContainer
import com.eyyuperdogan.artbooktesting.repo.FakeArtRepositoryTestAndroid
import com.eyyuperdogan.artbooktesting.roomdb.Art
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
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory:ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsToImageApi(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
         Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artImageViewDetail)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
       ArtDetailsFragmentDirections.actionArtDetailsFragmentToApiImageFragment()
        )

    }




    @Test
    fun testOnBackPressed(){
        val navController=Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun TestSave(){

        val testViewModel=ArtViewModel(FakeArtRepositoryTestAndroid())
        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            viewModel=testViewModel
        }
        //Espresso yazmasanda olur

        Espresso.onView(withId(R.id.nameTextDetail)).perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.artistTextDetail)).perform(replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.yearTextDetail)).perform(replaceText("1900"))
        onView(withId(R.id.saveButton)).perform(click())

        assertThat(testViewModel.artList.getOrAwaitValueTestAndroid()).contains(
            Art("Mona Lisa","Da Vinci",1900,"")
        )

    }
    }

