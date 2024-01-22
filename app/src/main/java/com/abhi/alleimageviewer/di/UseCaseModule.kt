package com.abhi.alleimageviewer.di

import com.abhi.alleimageviewer.repository.ImageListRepository
import com.abhi.alleimageviewer.usecase.ImageListUseCase
import com.abhi.alleimageviewer.usecase.ImageMlUseCase
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
    fun provideImageListUseCase(repository: ImageListRepository): ImageListUseCase {
        return ImageListUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideImageLabel(repository: ImageListRepository): ImageMlUseCase {
        return ImageMlUseCase(repository)
    }
}