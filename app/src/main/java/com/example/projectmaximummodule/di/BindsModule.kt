package com.example.projectmaximummodule.di

import com.example.projectmaximummodule.core.navigation.Router
import com.example.projectmaximummodule.core.navigation.RouterImpl
import com.example.projectmaximummodule.data.auth.AuthRepository
import com.example.projectmaximummodule.data.auth.AuthRepositoryImpl
import com.example.projectmaximummodule.data.auth.remote.AuthRemoteDataSource
import com.example.projectmaximummodule.data.auth.remote.AuthRemoteDataSourceImpl
import com.example.projectmaximummodule.data.debts.DebtsRepository
import com.example.projectmaximummodule.data.debts.DebtsRepositoryImpl
import com.example.projectmaximummodule.data.debts.remote.DebtsRemoteDataSource
import com.example.projectmaximummodule.data.debts.remote.DebtsRemoteDataSourceImpl
import com.example.projectmaximummodule.data.messenger.MessengerRepository
import com.example.projectmaximummodule.data.messenger.MessengerRepositoryImpl
import com.example.projectmaximummodule.data.messenger.remote.MessengerRemoteDataSource
import com.example.projectmaximummodule.data.messenger.remote.MessengerRemoteDataSourceImpl
import com.example.projectmaximummodule.data.profile.ProfileRepository
import com.example.projectmaximummodule.data.profile.ProfileRepositoryImpl
import com.example.projectmaximummodule.data.profile.remote.ProfileRemoteDataSource
import com.example.projectmaximummodule.data.profile.remote.ProfileRemoteDataSourceImpl
import com.example.projectmaximummodule.data.schedule.ScheduleRepository
import com.example.projectmaximummodule.data.schedule.ScheduleRepositoryImpl
import com.example.projectmaximummodule.data.schedule.local.ScheduleLocalDataSource
import com.example.projectmaximummodule.data.schedule.local.ScheduleLocalDataSourceImpl
import com.example.projectmaximummodule.data.schedule.remote.ScheduleRemoteDataSource
import com.example.projectmaximummodule.data.schedule.remote.ScheduleRemoteDataSourceImpl
import com.example.projectmaximummodule.data.theory.TheoryRepository
import com.example.projectmaximummodule.data.theory.TheoryRepositoryImpl
import com.example.projectmaximummodule.data.theory.remote.TheoryRemoteDataSource
import com.example.projectmaximummodule.data.theory.remote.TheoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface BindsModule {

    //Auth
    @Binds
    fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindAuthRemoteDataSource(
        impl: AuthRemoteDataSourceImpl,
    ): AuthRemoteDataSource

    //Schedule
    @Binds
    fun bindScheduleRepository(
        impl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    fun bindScheduleRemoteDataSource(
        impl: ScheduleRemoteDataSourceImpl
    ): ScheduleRemoteDataSource

    @Binds
    fun bindScheduleLocalDataSource(
        impl: ScheduleLocalDataSourceImpl
    ): ScheduleLocalDataSource

    //Theory
    @Binds
    fun bindTheoryRepository(
        impl: TheoryRepositoryImpl
    ): TheoryRepository

    @Binds
    fun bindTheoryRemoteDataSource(
        impl: TheoryRemoteDataSourceImpl
    ): TheoryRemoteDataSource

    //Debts
    @Binds
    fun bindDebtsRepository(
        impl: DebtsRepositoryImpl
    ): DebtsRepository

    @Binds
    fun bindDebtsRemoteDataSource(
        impl: DebtsRemoteDataSourceImpl
    ): DebtsRemoteDataSource

    //Messenger
    @Binds
    fun bindMessengerRepository(
        impl: MessengerRepositoryImpl
    ): MessengerRepository

    @Binds
    fun bindMessengerRemoteDataSource(
        impl: MessengerRemoteDataSourceImpl
    ): MessengerRemoteDataSource

    //Profile
    @Binds
    fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    fun bindProfileRemoteDataSource(
        impl: ProfileRemoteDataSourceImpl
    ): ProfileRemoteDataSource


    //Navigation
    @Binds
    fun bindRouter(
        impl: RouterImpl
    ): Router
}