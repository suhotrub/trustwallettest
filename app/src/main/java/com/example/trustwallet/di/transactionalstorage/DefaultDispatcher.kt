package com.example.trustwallet.di.transactionalstorage

import javax.inject.Qualifier

/**
 * @author a.gorbachev
 */
@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
internal annotation class DefaultDispatcher
