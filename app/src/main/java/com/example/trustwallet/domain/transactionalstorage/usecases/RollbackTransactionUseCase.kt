package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
interface RollbackTransactionUseCase {

    suspend operator fun invoke()
}

internal class RollbackTransactionUseCaseImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : RollbackTransactionUseCase {

    override suspend fun invoke() = keyValueStore.rollbackTransaction()
}
