package com.example.axxesschallenge

import android.app.Application
import com.example.axxesschallenge.di.apiModule
import com.example.axxesschallenge.di.repositoryModule
import com.example.axxesschallenge.di.retrofitModule
import com.example.axxesschallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/*This class is referenced from manifest and is responsible for providing dependencies for
* modules: repositoryModule,viewModelModule, retrofitModule and apiModule*/
class AxxessApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AxxessApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule
                )
            )
        }
    }
}