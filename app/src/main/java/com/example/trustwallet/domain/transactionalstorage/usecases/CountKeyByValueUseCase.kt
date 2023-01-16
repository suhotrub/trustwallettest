package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
interface CountKeyByValueUseCase {

    suspend operator fun invoke(value: String): Int
}

internal class CountKeyByValueUseCaseImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : CountKeyByValueUseCase {

    override suspend fun invoke(value: String): Int = keyValueStore.countKeys(value)
}
