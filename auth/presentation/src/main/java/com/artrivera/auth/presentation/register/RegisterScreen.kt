package com.artrivera.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artrivera.auth.domain.UserDataValidator
import com.artrivera.auth.presentation.R
import com.artrivera.core.presentation.design_system.CheckIcon
import com.artrivera.core.presentation.design_system.CrossIcon
import com.artrivera.core.presentation.design_system.EmailIcon
import com.artrivera.core.presentation.design_system.Poppins
import com.artrivera.core.presentation.design_system.RunStoryTheme
import com.artrivera.core.presentation.design_system.RuniqueDarkRed
import com.artrivera.core.presentation.design_system.RuniqueGreen
import com.artrivera.core.presentation.design_system.components.GradientBackground
import com.artrivera.core.presentation.design_system.components.RunStoryActionButton
import com.artrivera.core.presentation.design_system.components.RunStoryPasswordTextField
import com.artrivera.core.presentation.design_system.components.RunStoryTextField
import com.artrivera.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_succcessful,
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
        }

    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnLoginClick -> onSignInClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {

    val email by remember { mutableStateOf(TextFieldState(state.email)) }
    val password by remember { mutableStateOf(TextFieldState(state.password)) }

    LaunchedEffect(email.text) {
        onAction(RegisterAction.OnChangeEmail(email.text.toString()))
    }

    LaunchedEffect(password.text) {
        onAction(RegisterAction.OnChangePassword(password.text.toString()))
    }

    Scaffold { paddingValues ->
        GradientBackground(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    style = MaterialTheme.typography.headlineMedium
                )
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        append(stringResource(R.string.already_have_an_account) + " ")
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
                                    onAction(RegisterAction.OnLoginClick)
                                }
                            )) {
                            append(stringResource(R.string.login))
                        }
                    }
                }
                Text(annotatedString)
                Spacer(modifier = Modifier.height(48.dp))
                RunStoryTextField(
                    state = email,
                    startIcon = EmailIcon,
                    endIcon = if (state.isEmailValid) {
                        CheckIcon
                    } else null,
                    hint = stringResource(R.string.example_email),
                    title = stringResource(R.string.email),
                    additionalInfo = stringResource(R.string.must_be_valid_email),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                RunStoryPasswordTextField(
                    state = password,
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                    },
                    hint = stringResource(R.string.password),
                    title = stringResource(R.string.password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    PasswordRequirement(
                        text = stringResource(
                            R.string.password_requirement_at_least_x_characters,
                            UserDataValidator.MIN_PASSWORD_LENGTH
                        ),
                        isValid = state.passwordValidationState.hasMinLength
                    )
                    PasswordRequirement(
                        text = stringResource(
                            R.string.password_requirement_at_least_one_number,
                        ),
                        isValid = state.passwordValidationState.hasDigit
                    )
                    PasswordRequirement(
                        text = stringResource(
                            R.string.password_requirement_contains_lowercase_char,
                        ),
                        isValid = state.passwordValidationState.hasLowerCaseChar
                    )
                    PasswordRequirement(
                        text = stringResource(
                            R.string.password_requirement_contains_uppercase_char,
                        ),
                        isValid = state.passwordValidationState.hasUpperCaseChar
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                RunStoryActionButton(
                    text = stringResource(R.string.register),
                    isLoading = state.isRegistering,
                    enabled = state.canRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onAction(RegisterAction.OnRegisterClick)
                }
            }
        }
    }
}

@Composable
private fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) RuniqueGreen else RuniqueDarkRed
        )
        Text(
            text = text, color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }

}

@Preview
@Composable
private fun RegisterScreenPrev() {
    RunStoryTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}