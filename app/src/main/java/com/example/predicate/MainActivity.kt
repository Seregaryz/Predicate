package com.example.predicate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.predicate.fragment.sign_up.SignUpFragment
import com.example.predicate.fragment.speech.SpeechFragment
import com.example.predicate.utils.newRootScreen
import com.example.predicate.utils.setSlideAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.newRootScreen(
            SignUpFragment::class.java,
            setupFragmentTransaction = { it.setSlideAnimation() }
        )
    }
}