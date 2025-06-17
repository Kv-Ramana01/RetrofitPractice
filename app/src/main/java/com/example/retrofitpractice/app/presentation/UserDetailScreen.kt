package com.example.retrofitpractice.app.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun UserDetailScreen(
    userId: Int,

    userViewModel: UserViewModel = viewModel()
) {

    LaunchedEffect(userId) {
        userViewModel.fetchUser(userId)
    }

    val user by userViewModel.user.observeAsState()
    val error by userViewModel.error.observeAsState()
    val isLoading by userViewModel.isLoading.observeAsState(initial = false)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            user != null -> {
                // Since 'user' is UserDto?, you access its properties directly
                val currentUser = user!!
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("User Profile:", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("ID: ${currentUser.id}", style = MaterialTheme.typography.bodyLarge)
                    Text("Username: ${currentUser.username}", style = MaterialTheme.typography.bodyMedium)
                    Text("Email: ${currentUser.email}", style = MaterialTheme.typography.bodyMedium)
                    // Text("Password: ${currentUser.password}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            error != null -> {
                Text("Error fetching user: $error", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                Text("User details will load here.")
            }
        }
    }
}

