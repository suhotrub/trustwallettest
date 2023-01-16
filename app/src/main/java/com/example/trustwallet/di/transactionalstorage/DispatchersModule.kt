package com.example.trustwallet.di.transactionalstorage

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * @author a.gorbachev
 */
@Module
@InstallIn(SingletonComponent::class)
internal class DispatchersModule {

    @Provides
    @DefaultDispatcher
    fun bindDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}
