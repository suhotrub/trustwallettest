package com.example.trustwallet.data.transactionalstorage

import com.example.trustwallet.di.transactionalstorage.DefaultDispatcher
import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
internal class ConcurrentMemoryStoreWithDelete @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : KeyValueStore {

    private val transactions: ArrayDeque<ConcurrentHashMap<String, String>> = ArrayDeque()
    private val mutex: Mutex = Mutex()

    init {
        transactions.add(ConcurrentHashMap())
    }

    override suspend fun beginTransaction() {
        withContext(defaultDispatcher) {
            mutex.withLock {
                val newTransaction = ConcurrentHashMap<String, String>()
                newTransaction.putAll(transactions.last())
                transactions.addLast(newTransaction)
            }
        }
    }

    override suspend fun rollbackTransaction() {
        mutex.withLock {
            if (transactions.size == 1) throw IllegalStateException("No active transaction")
            transactions.removeLast()
        }
    }

    override suspend fun commitTransaction() {
        mutex.withLock {
            if (transactions.size == 1) throw IllegalStateException("No active transaction")

            val lastTransaction = transactions.removeLast()
            transactions.removeLast()
            transactions.addLast(lastTransaction)
        }
    }

    override suspend fun delete(key: String) {
        mutex.withLock {
            transactions.last().remove(key)
        }
    }

    override suspend fun get(key: String): String {
        mutex.withLock {
            return transactions.last()[key] ?: throw IllegalArgumentException("key not set")
        }
    }

    override suspend fun set(key: String, value: String) {
        mutex.withLock {
            transactions.last()[key] = value
        }
    }

    override suspend fun countKeys(value: String): Int {
        return withContext(defaultDispatcher) {
            mutex.withLock {
                transactions.last().count { (_, v) -> v == value }
            }
        }
    }
}
