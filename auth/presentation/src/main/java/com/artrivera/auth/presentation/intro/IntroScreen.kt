package com.artrivera.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artrivera.auth.presentation.R
import com.artrivera.core.presentation.ui.R as CoreUiR
import com.artrivera.core.presentation.design_system.LogoIcon
import com.artrivera.core.presentation.design_system.RunStoryTheme
import com.artrivera.core.presentation.design_system.components.GradientBackground
import com.artrivera.core.presentation.design_system.components.RunStoryActionButton
import com.artrivera.core.presentation.design_system.components.RunStoryOutlinedActionButton

/*
 * This composable is used to inject view models, nav controllers etc,
 * Doing this, testing the screen is easier as it separates dependencies from
 * behavior.
 */
@Composable
fun IntroScreenRoot(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    IntroScreen(onAction = { action ->
        when (action) {
            IntroAction.OnSignInClick -> onSignInClick()
            IntroAction.OnSignUpClick -> onSignUpClick()
        }
    })
}


@Composable
private fun IntroScreen(
    onAction: (IntroAction) -> Unit
) {
    Scaffold { it ->
        GradientBackground(modifier = Modifier.padding(it)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                RunStoryLogo()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 48.dp)
            ) {
                Text(
                    stringResource(
                        R.string.welcome_to_runstory,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    stringResource(R.string.rs_desc),
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.height(32.dp))
                RunStoryOutlinedActionButton(
                    text = stringResource(R.string.sign_in),
                    isLoading = false,
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onAction(IntroAction.OnSignInClick)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                RunStoryActionButton(
                    text = stringResource(R.string.sign_up),
                    isLoading = false,
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onAction(IntroAction.OnSignUpClick)
                    }
                )

            }
        }
    }

}

@Composable
fun RunStoryLogo(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            stringResource(id = CoreUiR.string.runstory),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Preview
@Composable
private fun IntroScreenPreview() {
    RunStoryTheme {
        IntroScreen { }
    }
}