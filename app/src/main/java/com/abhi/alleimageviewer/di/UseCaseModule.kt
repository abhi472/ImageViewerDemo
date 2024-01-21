package com.abhi.alleimageviewer.di

import com.abhi.alleimageviewer.repository.ImageListRepository
import com.abhi.alleimageviewer.usecase.ImageListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun provideCountriesUseCase(repository: ImageListRepository): ImageListUseCase {
        return ImageListUseCase(repository)
    }
}