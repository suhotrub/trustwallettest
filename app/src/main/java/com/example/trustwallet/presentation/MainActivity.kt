package com.example.trustwallet.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trustwallet.databinding.ActivityMainBinding
import com.example.trustwallet.presentation.recycler.CommandLineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * @author a.gorbachev
 */
@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val viewBinding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter = CommandLineAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.consoleLog.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        viewBinding.consoleLog.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.consoleLog.collect { consoleLog ->
                    adapter.submitList(consoleLog) {
                        viewBinding.consoleLog.scrollToPosition(consoleLog.size - 1)
                    }
                }
            }
        }

        viewBinding.commandInput.setOnKeyListener { _: View, keyCode: Int, keyEvent: KeyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                onSubmitClicked()
                true
            } else {
                false
            }
        }
        viewBinding.sendBtn.setOnClickListener {
            onSubmitClicked()
        }
    }

    private fun onSubmitClicked() {
        viewModel.onNewCommand(viewBinding.commandInput.text.toString())
        viewBinding.commandInput.text = null
    }
}
