package com.eyyuperdogan.artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.eyyuperdogan.artbooktesting.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    @Named("testDatabase")
    fun injectInMemory(@ApplicationContext context: Context)= Room.inMemoryDatabaseBuilder(
        context,ArtDatabase::class.java
    ).allowMainThreadQueries().build()
}