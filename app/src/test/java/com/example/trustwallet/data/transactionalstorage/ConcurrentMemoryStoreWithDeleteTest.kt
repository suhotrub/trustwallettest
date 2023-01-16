package com.example.trustwallet.data.transactionalstorage

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.internal.assertFails
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

/**
 * @author a.gorbachev
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class ConcurrentMemoryStoreWithDeleteTest {

    @Test
    fun setCorrect() = runTest {
        with(prepareEnvironment()) {
            set("123", "123")
            get("123") shouldBe "123"
        }
    }

    @Test
    fun setOverwrite() = runTest {
        with(prepareEnvironment()) {
            set("123", "123")
            set("123", "124")

            get("123") shouldBe "124"
        }
    }

    @Test
    fun delete() = runTest {
        with(prepareEnvironment()) {
            set("123", "123")
            delete("123")

            assertFails { get("123") }
        }
    }

    @Test
    fun deleteUnset() = runTest {
        with(prepareEnvironment()) {
            delete("123")

            assertFails { get("123") }
        }
    }

    @Test
    fun getUnset() = runTest {
        with(prepareEnvironment()) {

            assertFails { get("123") }
        }
    }

    @Test
    fun countEmpty() = runTest {
        with(prepareEnvironment()) {
            countKeys("123") shouldBeEqualTo 0
        }
    }

    @Test
    fun countDifferentKey() = runTest {
        with(prepareEnvironment()) {
            set("124", "124")

            countKeys("123") shouldBeEqualTo 0
        }
    }

    @Test
    fun countSameKey() = runTest {
        with(prepareEnvironment()) {
            set("123", "124")

            countKeys("124") shouldBeEqualTo 1
        }
    }

    @Test
    fun countSameKeySetTwice() = runTest {
        with(prepareEnvironment()) {
            set("123", "124")
            set("123", "124")

            countKeys("124") shouldBeEqualTo 1
        }
    }

    @Test
    fun failsCommitEmptyTransaction() = runTest {
        with(prepareEnvironment()) {
            assertFails { commitTransaction() }
        }
    }

    @Test
    fun failsRollbackEmptyTransaction() = runTest {
        with(prepareEnvironment()) {
            assertFails { rollbackTransaction() }
        }
    }

    @Test
    fun commitTransaction() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            set("123", "124")
            commitTransaction()

            get("123") shouldBe "124"
        }
    }

    @Test
    fun failsCommitAfterSuccesfullCommit() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            set("123", "124")
            commitTransaction()

            assertFails { commitTransaction() }
        }
    }

    @Test
    fun failsRollbackAfterSuccesfullCommit() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            set("123", "124")
            commitTransaction()

            assertFails { rollbackTransaction() }
        }
    }

    @Test
    fun rollbackTransaction() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            set("123", "124")
            rollbackTransaction()

            assertFails { get("123") }
        }
    }

    @Test
    fun nestedRollback() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            beginTransaction()
            set("123", "124")
            rollbackTransaction()
            commitTransaction()

            assertFails { get("123") }
        }
    }

    @Test
    fun nestedCommit() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            beginTransaction()
            set("123", "124")
            commitTransaction()
            rollbackTransaction()

            assertFails { get("123") }
        }
    }

    @Test
    fun nestedCommitTwice() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            beginTransaction()
            set("123", "124")
            commitTransaction()
            commitTransaction()

            get("123") shouldBe "124"
        }
    }

    @Test
    fun getInNestedTransaction() = runTest {
        with(prepareEnvironment()) {
            beginTransaction()
            set("123","125")
            beginTransaction()
            set("123", "124")
            get("123") shouldBe "124"
            rollbackTransaction()
            get("123") shouldBe "125"
            rollbackTransaction()

            assertFails { get("123") }
        }
    }

    @Test
    fun deleteInNestedTransaction() = runTest {
        with(prepareEnvironment()) {
            set("123","125")
            beginTransaction()
            delete("123")
            commitTransaction()

            assertFails { get("123") }
        }
    }

    private fun prepareEnvironment(): ConcurrentMemoryStoreWithDelete {
        return ConcurrentMemoryStoreWithDelete(UnconfinedTestDispatcher())
    }
}
