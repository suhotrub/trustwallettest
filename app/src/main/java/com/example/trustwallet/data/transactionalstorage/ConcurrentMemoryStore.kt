package com.example.trustwallet.data.transactionalstorage

import com.example.trustwallet.di.transactionalstorage.DefaultDispatcher
import com.example.trustwallet.domain.transactionalstorage.storage.KeyValueStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author a.gorbachev
 */
@Singleton
internal class ConcurrentMemoryStore @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : KeyValueStore {

    private val transactions: ArrayDeque<ConcurrentHashMap<String, String>> = ArrayDeque()
    private val mutex: Mutex = Mutex()

    init {
        transactions.add(ConcurrentHashMap())
    }

    override suspend fun beginTransaction() {
        mutex.withLock {
            transactions.addLast(ConcurrentHashMap<String, String>())
        }
    }

    override suspend fun rollbackTransaction() {
        mutex.withLock {
            if (transactions.size == 1) throw IllegalStateException("No active transaction")
            transactions.removeLast()
        }
    }

    override suspend fun commitTransaction() {
        withContext(defaultDispatcher) {
            mutex.withLock {
                if (transactions.size == 1) throw IllegalStateException("No active transaction")
                val lastTransaction = transactions.removeLast()
                transactions.last().putAll(lastTransaction)
            }
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
