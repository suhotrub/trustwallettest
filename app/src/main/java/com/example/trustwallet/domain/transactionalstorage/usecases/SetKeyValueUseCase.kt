package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
interface SetKeyValueUseCase {

    suspend operator fun invoke(key: String, value: String)
}

internal class SetKeyValueUseCaseImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : SetKeyValueUseCase {

    override suspend fun invoke(key: String, value: String) = keyValueStore.set(key, value)
}
