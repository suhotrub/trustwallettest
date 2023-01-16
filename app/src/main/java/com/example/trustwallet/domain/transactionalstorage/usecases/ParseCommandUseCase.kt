package com.example.trustwallet.domain.transactionalstorage.usecases

import com.example.trustwallet.domain.transactionalstorage.entities.Command
import javax.inject.Inject
import kotlin.IllegalArgumentException

/**
 * @author a.gorbachev
 */
interface ParseCommandUseCase {

    suspend operator fun invoke(input: String): Command
}

internal class ParseCommandUseCaseImpl @Inject constructor() : ParseCommandUseCase {

    override suspend fun invoke(input: String): Command {
        return when (input) {
            "BEGIN" -> Command.Begin
            "COMMIT" -> Command.Commit
            "ROLLBACK" -> Command.Rollback
            else -> {
                val splittedInput = input.split(" ")
                if (splittedInput.isEmpty()) {
                    throw IllegalArgumentException("Empty input")
                }
                when (splittedInput[0]) {
                    "GET" -> Command.Get(key = getFirstArgument(splittedInput))
                    "DELETE" -> Command.Delete(key = getFirstArgument(splittedInput))
                    "SET" -> Command.Set(
                        key = getFirstArgument(splittedInput),
                        value = getSecondArgument(splittedInput)
                    )
                    "COUNT" -> Command.Count(value = getFirstArgument(splittedInput))
                    else -> throw IllegalArgumentException("Unknown command")
                }
            }
        }
    }

    private fun getFirstArgument(splittedInput: List<String>): String {
        return if (splittedInput.getOrNull(1).isNullOrEmpty()) {
            throw IllegalArgumentException("Key not found")
        } else {
            splittedInput[1]
        }
    }

    private fun getSecondArgument(splittedInput: List<String>): String {
        return if (splittedInput.getOrNull(2).isNullOrEmpty()) {
            throw IllegalArgumentException("Value not found")
        } else {
            splittedInput[2]
        }
    }
}
