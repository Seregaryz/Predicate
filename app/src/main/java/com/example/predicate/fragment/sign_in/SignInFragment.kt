package com.example.predicate.fragment.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.predicate.R
import com.example.predicate.fragment.sign_up.SignUpFragment
import com.example.predicate.utils.navigateTo
import com.example.predicate.utils.setSlideAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fr_sign_in.*

@AndroidEntryPoint
class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignUp.setOnClickListener {
            parentFragmentManager.navigateTo(
                SignUpFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
        }
        etUsername.addTextChangedListener {

        }
    }
}