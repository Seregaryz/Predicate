package com.example.predicate.ui.sign_up

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
import com.example.predicate.ui.sign_in.SignInFragment
import com.example.predicate.system.subscribeToEvent
import com.example.predicate.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_up.*

@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
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
        etEmail.addTextChangedListener {
            viewModel.currentUserData.email = it.toString()
            btnSignUp.isEnabled = viewModel.validateData()
        }
        etFirstName.addTextChangedListener {
            viewModel.currentUserData.firstName = it.toString()
            btnSignUp.isEnabled = viewModel.validateData()
        }
        etLastName.addTextChangedListener {
            viewModel.currentUserData.lastName = it.toString()
            btnSignUp.isEnabled = viewModel.validateData()
        }
        etUsername.addTextChangedListener {
            viewModel.currentUserData.nickname = it.toString()
            btnSignUp.isEnabled = viewModel.validateData()
        }
        etPassword.addTextChangedListener {
            viewModel.currentUserData.password = it.toString()
            btnSignUp.isEnabled = viewModel.validateData()
        }
        etConfirmPassword.addTextChangedListener {
            viewModel.currentUserData.confirmPassword = it.toString()
            btnSignUp.isEnabled = viewModel.validateData()
        }
        btnSignUp.apply {
            isEnabled = false
            setOnClickListener {
                viewModel.createAccount()
            }
        }
        btnSignIn.setOnClickListener {
            parentFragmentManager.replace(
                SignInFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
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
                    SignInFragment::class.java,
                    setupFragmentTransaction = { it.setSlideAnimation() }
                )
            }
        }
    }

}