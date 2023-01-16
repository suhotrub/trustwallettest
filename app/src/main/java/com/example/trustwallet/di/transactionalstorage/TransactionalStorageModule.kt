package com.example.trustwallet.di.transactionalstorage

import com.example.trustwallet.data.transactionalstorage.ConcurrentMemoryStore
import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import com.example.trustwallet.domain.transactionalstorage.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author a.gorbachev
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface TransactionalStorageModule {

    @Binds
    fun bindTransactionalStorage(impl: ConcurrentMemoryStore): KeyValueStore

    @Binds
    fun bindBeginTransactionUseCase(impl: BeginTransactionUseCaseImpl): BeginTransactionUseCase

    @Binds
    fun bindCommitTransactionUseCase(impl: CommitTransactionUseCaseImpl): CommitTransactionUseCase

    @Binds
    fun bindCountKeyByValueUseCase(impl: CountKeyByValueUseCaseImpl): CountKeyByValueUseCase

    @Binds
    fun bindDeleteValueByKeyUseCase(impl: DeleteValueByKeyUseCaseImpl): DeleteValueByKeyUseCase

    @Binds
    fun bindGetValueByKeyUseCase(impl: GetValueByKeyUseCaseImpl): GetValueByKeyUseCase

    @Binds
    fun bindRollbackTransactionUseCase(impl: RollbackTransactionUseCaseImpl): RollbackTransactionUseCase

    @Binds
    fun bindSetKeyValueUseCase(impl: SetKeyValueUseCaseImpl): SetKeyValueUseCase

    @Binds
    fun bindParseCommandUseCase(impl: ParseCommandUseCaseImpl): ParseCommandUseCase
}
