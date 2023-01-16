package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.entities.Command
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.internal.assertFails
import org.amshove.kluent.should
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test


/**
 * @author a.gorbachev
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class ParseCommandUseCaseImplTest {

    @Test
    fun checkGet() = runTest {
        with(prepareEnvironment()) {
            invoke("GET 123")
                .shouldBeInstanceOf<Command.Get>()
                .should { key == "123" }
        }
    }

    @Test
    fun checkGetTooMuchParams() = runTest {
        with(prepareEnvironment()) {
            invoke("GET 123 456")
                .shouldBeInstanceOf<Command.Get>()
                .should { key == "123" }
        }
    }

    @Test
    fun checkGetNotEnoughParams() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("GET") }
        }
    }

    @Test
    fun checkWrongCommand() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("GOT") }
        }
    }

    @Test
    fun checkBegin() = runTest {
        with(prepareEnvironment()) {
            invoke("BEGIN") shouldBe Command.Begin
        }
    }

    @Test
    fun checkBeginWithParams() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("BEGIN 123") }
        }
    }

    @Test
    fun checkCommit() = runTest {
        with(prepareEnvironment()) {
            invoke("COMMIT") shouldBe Command.Commit
        }
    }

    @Test
    fun checkCommitWithParams() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("COMMIT 123") }
        }
    }

    @Test
    fun checkRollback() = runTest {
        with(prepareEnvironment()) {
            invoke("ROLLBACK") shouldBe Command.Rollback
        }
    }

    @Test
    fun checkRollbackWithParams() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("ROLLBACK 123") }
        }
    }

    @Test
    fun checkDeleteWithoutParams() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("DELETE") }
        }
    }

    @Test
    fun checkDelete() = runTest {
        with(prepareEnvironment()) {
            invoke("DELETE 123")
                .shouldBeInstanceOf<Command.Delete>()
                .should { key == "123" }
        }
    }

    @Test
    fun checkDeleteWithTooMuchParams() = runTest {
        with(prepareEnvironment()) {
            invoke("DELETE 123 456")
                .shouldBeInstanceOf<Command.Delete>()
                .should { key == "123" }
        }
    }

    @Test
    fun checkCountWithoutParams() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("COUNT") }
        }
    }

    @Test
    fun checkCount() = runTest {
        with(prepareEnvironment()) {
            invoke("COUNT 123")
                .shouldBeInstanceOf<Command.Count>()
                .should { value == "123" }
        }
    }

    @Test
    fun checkCountWithTooMuchParams() = runTest {
        with(prepareEnvironment()) {
            invoke("COUNT 123 456")
                .shouldBeInstanceOf<Command.Count>()
                .should { value == "123" }
        }
    }

    @Test
    fun checkSetWithoutParams() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("SET") }
        }
    }

    @Test
    fun checkSetWithOnlyOneParam() = runTest {
        with(prepareEnvironment()) {
            assertFails { invoke("SET 123") }
        }
    }

    @Test
    fun checkSet() = runTest {
        with(prepareEnvironment()) {
            invoke("SET 123 456")
                .shouldBeInstanceOf<Command.Set>()
                .should { key == "123" && value == "456" }
        }
    }

    @Test
    fun checkSetWithTooMuchParams() = runTest {
        with(prepareEnvironment()) {
            invoke("SET 123 456 789")
                .shouldBeInstanceOf<Command.Set>()
                .should { key == "123" && value == "456" }
        }
    }

    private fun prepareEnvironment(): ParseCommandUseCase {
        return ParseCommandUseCaseImpl()
    }
}
