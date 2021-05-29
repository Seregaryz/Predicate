package com.example.predicate.fragment.sign_up

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
import com.example.predicate.fragment.sign_in.SignInFragment
import com.example.predicate.system.subscribeToEvent
import com.example.predicate.utils.navigateTo
import com.example.predicate.utils.registerOnBackPressedCallback
import com.example.predicate.utils.setSlideAnimation
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerOnBackPressedCallback {
            parentFragmentManager.popBackStack()
        }
        etEmail.addTextChangedListener {
            viewModel.currentUserData.email = it.toString()
        }
        etFirstName.addTextChangedListener {
            viewModel.currentUserData.firstName = it.toString()
        }
        etLastName.addTextChangedListener {
            viewModel.currentUserData.lastName = it.toString()
        }
        etUsername.addTextChangedListener {
            viewModel.currentUserData.nickname = it.toString()
        }
        etPassword.addTextChangedListener {
            viewModel.currentUserData.password = it.toString()
        }
        etConfirmPassword.addTextChangedListener {
            viewModel.currentUserData.confirmPassword = it.toString()
        }
        btnSignUp.setOnClickListener {
            viewModel.createAccount()
        }
        btnSignIn.setOnClickListener {
            parentFragmentManager.navigateTo(
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