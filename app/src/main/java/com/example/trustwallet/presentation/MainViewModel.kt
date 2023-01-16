package com.example.trustwallet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustwallet.domain.transactionalstorage.entities.Command
import com.example.trustwallet.domain.transactionalstorage.usecases.*
import com.example.trustwallet.presentation.recycler.CommandLineItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author a.gorbachev
 */
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val beginTransactionUseCase: BeginTransactionUseCase,
    private val commitTransactionUseCase: CommitTransactionUseCase,
    private val countKeyByValueUseCase: CountKeyByValueUseCase,
    private val deleteValueByKeyUseCase: DeleteValueByKeyUseCase,
    private val getValueByKeyUseCase: GetValueByKeyUseCase,
    private val rollbackTransactionUseCase: RollbackTransactionUseCase,
    private val setKeyValueUseCase: SetKeyValueUseCase,
    private val parseCommandUseCase: ParseCommandUseCase,
) : ViewModel() {

    private val _consoleLog: MutableStateFlow<List<CommandLineItem>> = MutableStateFlow(emptyList())
    val consoleLog: StateFlow<List<CommandLineItem>> = _consoleLog.asStateFlow()

    fun onNewCommand(command: String) {
        viewModelScope.launch {
            appendLineToLog(CommandLineItem.InputItem("> $command"))
            try {
                invokeCommand(parseCommandUseCase(command))
            } catch (e: Exception) {
                appendLineToLog(CommandLineItem.ErrorItem(e.message.toString()))
            }
        }
    }

    private suspend fun invokeCommand(command: Command) {
        when (command) {
            Command.Begin -> beginTransactionUseCase()
            Command.Commit -> commitTransactionUseCase()
            Command.Rollback -> rollbackTransactionUseCase()
            is Command.Count -> {
                val count = countKeyByValueUseCase(command.value)
                appendLineToLog(CommandLineItem.ResultItem("Found $count keys with value ${command.value}"))
            }
            is Command.Delete -> deleteValueByKeyUseCase(command.key)
            is Command.Get -> {
                val value = getValueByKeyUseCase(command.key)
                appendLineToLog(CommandLineItem.ResultItem(value))
            }
            is Command.Set -> setKeyValueUseCase(command.key, command.value)
        }
    }

    private suspend fun appendLineToLog(line: CommandLineItem) {
        _consoleLog.emit(_consoleLog.value + line)
    }

}
