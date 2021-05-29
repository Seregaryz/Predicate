package com.example.predicate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.predicate.fragment.sign_in.SignInFragment
import com.example.predicate.fragment.sign_up.SignUpFragment
import com.example.predicate.fragment.speech.SpeechFragment
import com.example.predicate.system.SessionKeeper
import com.example.predicate.utils.newRootScreen
import com.example.predicate.utils.setSlideAnimation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionKeeper: SessionKeeper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (sessionKeeper.token != null) {
            supportFragmentManager.newRootScreen(
                SpeechFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
        } else {
            supportFragmentManager.newRootScreen(
                SignInFragment::class.java,
                setupFragmentTransaction = { it.setSlideAnimation() }
            )
        }
    }

    private fun triggerRestart() {
        recreate()
    }
}