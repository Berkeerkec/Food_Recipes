package com.berkeerkec.foodrecipe.hilt

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.berkeerkec.foodrecipe.R
import com.berkeerkec.foodrecipe.api.FoodRecipesApi
import com.berkeerkec.foodrecipe.repository.RecipesRepository
import com.berkeerkec.foodrecipe.repository.RecipesRepositoryImpl
import com.berkeerkec.foodrecipe.util.Constant.Companion.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : FoodRecipesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FoodRecipesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context : Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(CircularProgressDrawable(context).apply {
                strokeWidth = 8f
                centerRadius = 40f
                start()
            })
                .error(R.drawable.error_image)
        )

    @Singleton
    @Provides
    fun provideRepository(api : FoodRecipesApi) = RecipesRepositoryImpl(api) as RecipesRepository
}