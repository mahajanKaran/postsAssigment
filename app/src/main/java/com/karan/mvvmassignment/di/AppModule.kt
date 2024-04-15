package com.karan.mvvmassignment.di

import android.app.Application
import android.content.Context
import com.karan.mvvmassignment.repository.PostsRepository
import com.karan.mvvmassignment.utils.Network
import com.karan.mvvmassignment.utils.NetworkConnectivity
import com.karan.mvvmassignment.network.PostsApi
import com.karan.mvvmassignment.repository.impl.PostsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providePostsApi(): PostsApi {
        return Retrofit.Builder()
            .baseUrl(PostsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return Network(context)
    }

    @Provides
    @Singleton
    fun providePostsRepository(api: PostsApi, networkConnectivity: NetworkConnectivity): PostsRepository {
        return PostsRepositoryImpl(api,networkConnectivity)
    }


}
