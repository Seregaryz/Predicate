package com.example.predicate.fragment.statistic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.predicate.R
import com.example.predicate.fragment.base.BaseFragment
import com.example.predicate.fragment.speech.SpeechFragment
import com.example.predicate.model.mistake.MistakeResponce
import com.example.predicate.system.subscribeToEvent
import com.example.predicate.utils.navigateTo
import com.example.predicate.utils.registerOnBackPressedCallback
import com.example.predicate.utils.setSlideAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fr_statistic.*

@AndroidEntryPoint
class StatisticFragment : BaseFragment(R.layout.fr_statistic) {

    private val viewModel: StatisticViewModel by viewModels()

    private val mistakeResponse: MistakeResponce? by lazy {
        requireArguments().getParcelable(ARG_MISTAKES)
    }

    private val mistakesAdapter: MistakesAdapter by lazy {
        MistakesAdapter { item, position ->
            viewModel.deleteMistake(item, position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mistakeResponse != null && mistakeResponse?.mistakes?.isNotEmpty() == true) {
            rvMistakes.apply {
                visibility = View.VISIBLE
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mistakesAdapter
            }
            tvNotFound.visibility = View.GONE
            mistakeResponse?.mistakes?.let {
                mistakesAdapter.setData(it)
            }
        } else {
            tvNotFound.visibility = View.VISIBLE
            rvMistakes.visibility = View.GONE
        }
        btnBack.setOnClickListener {
            parentFragmentManager.navigateTo(
                SpeechFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
        }
        registerOnBackPressedCallback {
            parentFragmentManager.navigateTo(
                SpeechFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            loading.subscribe {
                showProgressDialog(it)
            }.disposeOnPause()
            errorMessage.subscribeToEvent {

            }.disposeOnPause()
            mistakes.subscribe { mistakes ->
                if (mistakes.isNotEmpty()) {
                    mistakesAdapter.setData(mistakes)
                } else {
                    mistakesAdapter.setData(emptyList())
                    tvNotFound.visibility = View.VISIBLE
                    rvMistakes.visibility = View.GONE
                }
            }.disposeOnPause()
        }
    }

    companion object {
        const val ARG_MISTAKES = "mistakes"
    }


}