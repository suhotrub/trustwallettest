package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
interface CommitTransactionUseCase {

    suspend operator fun invoke()
}

internal class CommitTransactionUseCaseImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : CommitTransactionUseCase {

    override suspend fun invoke() = keyValueStore.commitTransaction()
}
