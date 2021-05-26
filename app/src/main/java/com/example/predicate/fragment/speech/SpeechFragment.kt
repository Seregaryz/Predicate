package com.example.predicate.fragment.speech

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.predicate.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_speech.*

@AndroidEntryPoint
class SpeechFragment : Fragment() {

    private val viewModel: SpeechViewModel by viewModels()

    var resultString = ""

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            resultString = matches?.get(0) ?: ""
            viewModel.catchSpeechResult(resultString)
        }
    }

    private fun openSomeActivityForResult() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "record your discussion")
        }
        resultLauncher.launch(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_speech, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRecord.setOnClickListener {
//            val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
//                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//                putExtra(RecognizerIntent.EXTRA_PROMPT, "record your discussion")
//            }
//            startActivityForResult(speechIntent, RECOGNIZER_RESULT)
            openSomeActivityForResult()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            speech.subscribe {
                result.text = it
            }
        }
    }


    companion object {
        const val RECOGNIZER_RESULT = 1
    }
}