package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
interface DeleteValueByKeyUseCase {

    suspend operator fun invoke(key: String)
}

internal class DeleteValueByKeyUseCaseImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : DeleteValueByKeyUseCase {

    override suspend fun invoke(key: String) = keyValueStore.delete(key)
}
