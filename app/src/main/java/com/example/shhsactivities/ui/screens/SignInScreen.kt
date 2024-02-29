package com.example.shhsactivities.ui.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onNavigate: () -> Unit,
    googleAuthApi: GoogleAuthApi,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        if (googleAuthApi.getSignedInUser() != null) onNavigate()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == ComponentActivity.RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthApi.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            onNavigate()
            viewModel.resetState()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Button(onClick = {
            scope.launch {
                val signInIntentSender = googleAuthApi.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }) {
            Text(text = "Sign In")
        }
    }
}