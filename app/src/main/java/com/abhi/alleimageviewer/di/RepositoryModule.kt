package com.abhi.alleimageviewer.di

import com.abhi.alleimageviewer.repository.ImageListRepository
import com.abhi.alleimageviewer.repository.ImageListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCountryDataRepository(): ImageListRepository {
        return ImageListRepositoryImpl()
    }
}