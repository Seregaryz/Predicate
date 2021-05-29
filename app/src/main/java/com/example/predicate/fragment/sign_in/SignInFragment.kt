package com.example.predicate.fragment.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.predicate.R
import com.example.predicate.fragment.base.BaseFragment
import com.example.predicate.fragment.sign_up.SignUpFragment
import com.example.predicate.fragment.speech.SpeechFragment
import com.example.predicate.system.subscribeToEvent
import com.example.predicate.utils.navigateTo
import com.example.predicate.utils.registerOnBackPressedCallback
import com.example.predicate.utils.setSlideAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fr_sign_in.*

@AndroidEntryPoint
class SignInFragment : BaseFragment(R.layout.fr_sign_in) {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerOnBackPressedCallback {
            parentFragmentManager.popBackStack()
        }
        btnSignUp.setOnClickListener {
            parentFragmentManager.navigateTo(
                SignUpFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
        }
        etUsername.addTextChangedListener {
            viewModel.currentLogin = it.toString()
        }
        etPassword.addTextChangedListener {
            viewModel.currentPassword = it.toString()
        }
        btnSignIn.setOnClickListener {
            viewModel.signIn()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            loading.subscribe {
                showProgressDialog(it)
            }
            errorMessage.subscribeToEvent {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            successSignUp.subscribe {
                parentFragmentManager.navigateTo(
                    SpeechFragment::class.java,
                    setupFragmentTransaction = { it.setSlideAnimation() }
                )
            }
        }
    }
}