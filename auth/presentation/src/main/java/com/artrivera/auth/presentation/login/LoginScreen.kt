package com.artrivera.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artrivera.auth.presentation.R
import com.artrivera.core.presentation.design_system.EmailIcon
import com.artrivera.core.presentation.design_system.Poppins
import com.artrivera.core.presentation.design_system.RunStoryTheme
import com.artrivera.core.presentation.design_system.components.GradientBackground
import com.artrivera.core.presentation.design_system.components.RunStoryActionButton
import com.artrivera.core.presentation.design_system.components.RunStoryPasswordTextField
import com.artrivera.core.presentation.design_system.components.RunStoryTextField
import com.artrivera.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->

        when (event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }

            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(context, R.string.youre_logged_in, Toast.LENGTH_LONG).show()
                onLoginSuccess()
            }
        }


    }

    LoginScreen(state = state, onAction = { action ->
        when (action) {
            is LoginAction.OnRegisterClick -> onSignUpClick()
            else -> Unit
        }
        viewModel.onAction(action)
    })
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {

    val email by remember { mutableStateOf(TextFieldState(state.email)) }
    val password by remember { mutableStateOf(TextFieldState(state.password)) }

    LaunchedEffect(email.text) {
       onAction(LoginAction.OnChangeEmail(email.text.toString()))
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.hi_there),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.login_welcome),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            RunStoryTextField(
                state = email,
                startIcon = EmailIcon,
                endIcon = null,
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                additionalInfo = null,
            )

            Spacer(modifier = Modifier.height(16.dp))

            RunStoryPasswordTextField(
                state = password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = { onAction(LoginAction.OnTogglePasswordVisibility) },
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password)
            )

            Spacer(modifier = Modifier.height(32.dp))

            RunStoryActionButton(
                text = stringResource(R.string.login),
                isLoading = state.isLoading,
                enabled = state.canLogin,
                onClick = {
                    onAction(LoginAction.OnLoginClick)
                }
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.BottomCenter
            ) {
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        append(stringResource(R.string.dont_have_an_account) + " ")
                        withLink(
                            LinkAnnotation.Clickable(
                                tag = "clickable_text",
                                styles = TextLinkStyles(
                                    SpanStyle(
                                        fontFamily = Poppins,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                ),
                                linkInteractionListener = {
                                    onAction(LoginAction.OnRegisterClick)
                                }
                            )) {
                            append(stringResource(R.string.sign_up))
                        }
                    }
                }
                Text(annotatedString)
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(modifier: Modifier = Modifier) {
    RunStoryTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}