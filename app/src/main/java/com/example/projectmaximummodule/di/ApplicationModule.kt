package com.example.projectmaximummodule.di

import android.content.Context
import android.content.SharedPreferences
import com.example.projectmaximummodule.application.AppSharedPreferences.Companion.SHARED_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {


    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }
}