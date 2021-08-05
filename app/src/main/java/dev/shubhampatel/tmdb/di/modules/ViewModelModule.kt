package dev.shubhampatel.tmdb.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.shubhampatel.tmdb.data.IDataManager
import dev.shubhampatel.tmdb.ui.features.moviedetails.domain.MovieDetailsUseCase
import dev.shubhampatel.tmdb.ui.features.moviedetails.domain.MovieDetailsUseCaseImpl
import dev.shubhampatel.tmdb.ui.features.moviedetails.repository.MovieDetailsRepository
import dev.shubhampatel.tmdb.ui.features.moviedetails.repository.MovieDetailsRepositoryImpl
import dev.shubhampatel.tmdb.ui.features.movieslist.domain.MoviesListUseCase
import dev.shubhampatel.tmdb.ui.features.movieslist.domain.MoviesListUseCaseImpl
import dev.shubhampatel.tmdb.ui.features.movieslist.repository.MoviesListRepository
import dev.shubhampatel.tmdb.ui.features.movieslist.repository.MoviesListRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideMoviesListUseCase(repository: MoviesListRepository): MoviesListUseCase {
        return MoviesListUseCaseImpl(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideMoviesListRepository(dataManager: IDataManager): MoviesListRepository {
        return MoviesListRepositoryImpl(dataManager)
    }

    @ViewModelScoped
    @Provides
    fun provideMovieDetailsUseCase(repository: MovieDetailsRepository): MovieDetailsUseCase {
        return MovieDetailsUseCaseImpl(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideMovieDetailsRepository(dataManager: IDataManager): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(dataManager)
    }
}