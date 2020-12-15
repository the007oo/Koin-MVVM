package com.phattarapong.koin

import android.app.Application
import com.phattarapong.koin.di.provideApiService
import com.phattarapong.koin.di.provideOkHttpClient
import com.phattarapong.koin.di.provideRetrofit
import com.phattarapong.koin.repository.MainRepositoryImpl
import com.phattarapong.koin.viewmodel.MainViewModel
import com.phattarapong.templatemvvm.database.CharacterDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MainApplication : Application() {

    private val netWorkModule = module {
        single { provideOkHttpClient() }
        single {
            provideRetrofit(get(), "https://rickandmortyapi.com/api/")
        }
        single { provideApiService(get()) }
    }

    private val dataBaseModule = module {
        single { CharacterDatabase.getInstance(androidContext()).characterDao }
    }

    private val repositoryModule = module {
        factory { MainRepositoryImpl(get(), get()) }
    }

    private val viewModelModule = module {
        viewModel { MainViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(dataBaseModule, netWorkModule, repositoryModule, viewModelModule)
        }
    }
}