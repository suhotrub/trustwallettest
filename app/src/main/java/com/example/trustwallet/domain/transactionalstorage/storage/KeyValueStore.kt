package com.example.trustwallet.domain.transactionalstorage.storage

/**
 * @author a.gorbachev
 */
interface KeyValueStore {

    suspend fun beginTransaction()

    suspend fun rollbackTransaction()

    suspend fun commitTransaction()

    suspend fun delete(key: String)

    suspend fun get(key: String): String

    suspend fun set(key: String, value: String)

    suspend fun countKeys(value: String): Int
}
