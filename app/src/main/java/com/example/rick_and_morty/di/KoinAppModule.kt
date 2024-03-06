package com.example.rick_and_morty.di

import com.example.rick_and_morty.data.network.CharacterApi
import com.example.rick_and_morty.data.network.CharacterRepositoryImpl
import com.example.rick_and_morty.domain.repository.CharacterRepository
import com.example.rick_and_morty.presentation.viewmodels.CharacterDetailViewModel
import com.example.rick_and_morty.presentation.viewmodels.CharactersViewModel
import com.example.rick_and_morty.util.Constants
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel {
        CharactersViewModel(get())
    }
    viewModel {
        CharacterDetailViewModel(get())
    }
}

val repositoryModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl(get(), get())
    }
    single {
        dispatcherIO()
    }
}

val networkModule = module {
    single {
        provideLoggingInterceptor()
    }
    single {
        provideOkHttpClient(get())
    }
    single {
        provideRetrofit(get())
    }
    single {
        provideCharacterApi(get())
    }
}

val appModule = listOf(viewModelModule, repositoryModule, networkModule)

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(
    client: OkHttpClient,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

fun provideCharacterApi(retrofit: Retrofit): CharacterApi {
    return retrofit.create(CharacterApi::class.java)
}

fun dispatcherIO() = Dispatchers.IO