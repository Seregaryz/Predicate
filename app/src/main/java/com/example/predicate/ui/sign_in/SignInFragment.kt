package com.example.predicate.ui.sign_in

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.predicate.R
import com.example.predicate.ui.base.BaseFragment
import com.example.predicate.ui.sign_up.SignUpFragment
import com.example.predicate.ui.speech.SpeechFragment
import com.example.predicate.system.subscribeToEvent
import com.example.predicate.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fr_sign_in.*
import kotlinx.android.synthetic.main.fr_sign_in.btnSignIn
import kotlinx.android.synthetic.main.fr_sign_in.btnSignUp
import kotlinx.android.synthetic.main.fr_sign_in.container
import kotlinx.android.synthetic.main.fr_sign_in.etPassword
import kotlinx.android.synthetic.main.fr_sign_in.etUsername
import kotlinx.android.synthetic.main.fragment_sign_up.*

@AndroidEntryPoint
class SignInFragment : BaseFragment(R.layout.fr_sign_in) {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_sign_in, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container.setOnTouchListener { v, event ->
            v.onTouchEvent(event)
            hideKeyboard()
            true
        }
        registerOnBackPressedCallback {
            parentFragmentManager.popBackStack()
        }
        btnSignUp.setOnClickListener {
            parentFragmentManager.replace(
                SignUpFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
        }
        etUsername.addTextChangedListener {
            viewModel.currentLogin = it.toString()
            btnSignIn.isEnabled = viewModel.validateData()
        }
        etPassword.addTextChangedListener {
            viewModel.currentPassword = it.toString()
            btnSignIn.isEnabled = viewModel.validateData()

        }
        btnSignIn.apply {
            isEnabled = false
            setOnClickListener {
                viewModel.signIn()
            }
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