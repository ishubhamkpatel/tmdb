package dev.shubhampatel.tmdb.di.modules

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.shubhampatel.tmdb.BuildConfig
import dev.shubhampatel.tmdb.data.DataManager
import dev.shubhampatel.tmdb.data.IDataManager
import dev.shubhampatel.tmdb.data.local.ILocalDataManager
import dev.shubhampatel.tmdb.data.local.LocalDataManager
import dev.shubhampatel.tmdb.data.local.db.AppDatabase
import dev.shubhampatel.tmdb.data.local.db.DatabaseMigrations
import dev.shubhampatel.tmdb.data.local.db.DbManager
import dev.shubhampatel.tmdb.data.local.db.IDbManager
import dev.shubhampatel.tmdb.data.local.ds.IPrefsManager
import dev.shubhampatel.tmdb.data.local.ds.PrefsManager
import dev.shubhampatel.tmdb.data.remote.Api
import dev.shubhampatel.tmdb.data.remote.ApiInterceptor
import dev.shubhampatel.tmdb.data.remote.IRemoteDataManager
import dev.shubhampatel.tmdb.data.remote.RemoteDataManager
import dev.shubhampatel.tmdb.di.qualifiers.ApiInterceptorOkHttpClient
import dev.shubhampatel.tmdb.di.qualifiers.HttpLoggingInterceptorOkHttpClient
import dev.shubhampatel.tmdb.utility.dataStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataManager(
        localDataManager: ILocalDataManager,
        remoteDataManager: IRemoteDataManager
    ): IDataManager {
        return DataManager(localDataManager, remoteDataManager)
    }

    @Singleton
    @Provides
    fun provideLocalDataManager(
        prefsManager: IPrefsManager,
        dbManager: IDbManager
    ): ILocalDataManager {
        return LocalDataManager(prefsManager, dbManager)
    }

    @Singleton
    @Provides
    fun providePrefsManager(@ApplicationContext context: Context): IPrefsManager {
        return PrefsManager(context.dataStore)
    }

    @Singleton
    @Provides
    fun provideDbManager(appDatabase: AppDatabase): IDbManager {
        return DbManager(appDatabase)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
         return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
             .addMigrations(*DatabaseMigrations.MIGRATIONS)
             .build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataManager(api: Api): IRemoteDataManager {
        return RemoteDataManager(api)
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @HttpLoggingInterceptorOkHttpClient httpLoggingInterceptor: Interceptor,
        @ApiInterceptorOkHttpClient apiInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(httpLoggingInterceptor)
                }
            }
            .addInterceptor(apiInterceptor)
            .build()
    }

    @Singleton
    @HttpLoggingInterceptorOkHttpClient
    @Provides
    fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @ApiInterceptorOkHttpClient
    @Provides
    fun provideApiInterceptor(): Interceptor {
        return ApiInterceptor()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}