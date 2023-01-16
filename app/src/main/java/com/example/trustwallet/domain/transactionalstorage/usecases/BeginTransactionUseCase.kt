package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
interface BeginTransactionUseCase {

    suspend operator fun invoke()
}

internal class BeginTransactionUseCaseImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : BeginTransactionUseCase {

    override suspend fun invoke() = keyValueStore.beginTransaction()
}
