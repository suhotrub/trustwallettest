package com.example.trustwallet.presentation.recycler

import androidx.annotation.ColorRes
import com.example.trustwallet.R
import java.util.*

/**
 * @author a.gorbachev
 */
sealed class CommandLineItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    @ColorRes val colorRes: Int
) {

    class ErrorItem(text: String) : CommandLineItem(text = text, colorRes = R.color.error)
    class InputItem(text: String) : CommandLineItem(text = text, colorRes = R.color.input)
    class ResultItem(text: String) : CommandLineItem(text = text, colorRes = R.color.success)
}
