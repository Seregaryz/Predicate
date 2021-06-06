package com.example.predicate.ui.speech

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.predicate.R
import com.example.predicate.ui.base.BaseFragment
import com.example.predicate.ui.statistic.StatisticFragment
import com.example.predicate.system.subscribeToEvent
import com.example.predicate.utils.navigateTo
import com.example.predicate.utils.registerOnBackPressedCallback
import com.example.predicate.utils.setSlideAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_speech.*

@AndroidEntryPoint
class SpeechFragment : BaseFragment(R.layout.fragment_speech) {

    private val viewModel: SpeechViewModel by viewModels()

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val resultString = matches?.get(0) ?: ""
                viewModel.currentText += resultString
                viewModel.programmaticallyMode = true
                etResult.setText(viewModel.currentText)
                viewModel.programmaticallyMode = false
            }
        }

    private fun openSomeActivityForResult() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_PROMPT, "record your discussion")
        }
        resultLauncher.launch(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerOnBackPressedCallback {
            parentFragmentManager.popBackStack()
        }
        btnRecord.setOnClickListener {
            openSomeActivityForResult()
        }
        etResult.addTextChangedListener {
            if (!viewModel.programmaticallyMode) {
                viewModel.currentText = it.toString()
            }
        }
        btnSend.setOnClickListener {
            viewModel.sendArgument()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            loading.subscribe {
                showProgressDialog(it)
            }.disposeOnPause()
            errorMessage.subscribeToEvent {
                Toast.makeText(requireContext(), "Что-то пошло не так. Повторите попытку позже", Toast.LENGTH_SHORT).show()
            }.disposeOnPause()
            mistakes.subscribe { mistake ->
                parentFragmentManager.navigateTo(
                    StatisticFragment::class.java,
                    bundleOf(StatisticFragment.ARG_MISTAKES to mistake),
                    setupFragmentTransaction = { it.setSlideAnimation() }
                )
            }.disposeOnPause()
        }
    }


    companion object {
        const val RECOGNIZER_RESULT = 1
    }
}