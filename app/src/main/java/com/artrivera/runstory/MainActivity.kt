package com.artrivera.runstory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.artrivera.auth.presentation.intro.IntroScreenRoot
import com.artrivera.core.presentation.design_system.RunStoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunStoryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IntroScreenRoot(
                        modifier = Modifier.padding(innerPadding),
                        onSignInClick = {},
                        onSignUpClick = {}
                    )
                }
            }
        }
    }
}