package com.eyyuperdogan.artbooktesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.eyyuperdogan.artbooktesting.getOrAwaitValueTestAndroid
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    //hilttin başlamasını sağlıyor
    var hiltRule=HiltAndroidRule(this)

    //field injectionlar private yapılamaz
    @Inject
    //bu projede 2 tane Room.DataBaseBuilder döndürüyoruz.
    // karışıklık olmasın diye(inmemorydatabase olanı alsın diye)isim veriyoruz
    @Named("testDatabase")
    lateinit var database:ArtDatabase

    private lateinit var dao:ArtDao

    @Before
    fun setup() {
/* hilti kullanmadan bu database'i yaparaktan testi yapabiliriz
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArtDataBase::class.java)
            .allowMainThreadQueries() //this is a test case, we don't want other thread pools
            .build()
*/
        hiltRule.inject()
        dao=database.artDao()
    }


    @After
    fun tearDown(){
        database.close()
    }


    @Test
    fun insertArtTesting()= runTest{
        val exampleArt=Art("eyyup","erdogan",1900,"test.com",1)
        dao.insertArt(exampleArt)
        val list=dao.observeArts().getOrAwaitValueTestAndroid()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun delete()= runTest {
        val exampleArt=Art("eyyup","erdogan",1900,"test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        val list=dao.observeArts().getOrAwaitValueTestAndroid()
        assertThat(list).doesNotContain(exampleArt)
    }

}