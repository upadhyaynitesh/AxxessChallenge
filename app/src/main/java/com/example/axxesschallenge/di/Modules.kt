package com.example.axxesschallenge.di

import com.example.axxesschallenge.networking.ApiRepository
import com.example.axxesschallenge.networking.interfaces.FetchAPI
import com.example.axxesschallenge.utils.Constants
import com.example.axxesschallenge.viewmodel.AxxessViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val viewModelModule = module {
    viewModel {
        AxxessViewModel(get())
    }
}

val repositoryModule = module {
    single {
        ApiRepository(get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): FetchAPI {
        return retrofit.create(FetchAPI::class.java)
    }
    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { provideRetrofit() }
}