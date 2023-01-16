package com.example.trustwallet.domain.transactionalstorage.entities

/**
 * @author a.gorbachev
 */
sealed class Command {

    class Get(val key: String) : Command()
    class Delete(val key: String) : Command()
    class Set(val key: String, val value: String) : Command()
    class Count(val value: String) : Command()

    object Commit : Command()
    object Begin : Command()
    object Rollback : Command()
}
