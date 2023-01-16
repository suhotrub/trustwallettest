package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
interface GetValueByKeyUseCase {

    suspend operator fun invoke(key: String): String
}

internal class GetValueByKeyUseCaseImpl @Inject constructor(
    private val keyValueStore: KeyValueStore
) : GetValueByKeyUseCase {

    override suspend fun invoke(key: String): String = keyValueStore.get(key)
}
