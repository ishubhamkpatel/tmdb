package dev.shubhampatel.tmdb.ui.features.moviedetails.repository

import dev.shubhampatel.tmdb.data.IDataManager
import javax.inject.Inject

interface MovieDetailsRepository {
    val dataManager: IDataManager
}

class MovieDetailsRepositoryImpl @Inject constructor(
    override val dataManager: IDataManager
) : MovieDetailsRepository {
}