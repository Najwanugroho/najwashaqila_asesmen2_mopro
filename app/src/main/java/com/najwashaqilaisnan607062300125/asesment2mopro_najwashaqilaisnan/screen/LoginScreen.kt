package com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.database.CatatanDb
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.model.Pengguna
import com.najwashaqilaisnan607062300125.asesment2mopro_najwashaqilaisnan.navigation.Screen

@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: (Pengguna) -> Unit) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = CatatanDb.getInstance(context)
    val penggunaDao = db.penggunaDao
    val viewModel = remember { LoginViewModel(penggunaDao) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF1F8)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Login Mood Tracker",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF6A1B9A)
                    )
                )

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        scope.launch {
                            if (email.isBlank() || password.isBlank()) {
                                Toast.makeText(context, "Isi email dan password", Toast.LENGTH_SHORT).show()
                                return@launch
                            }
                            val user = viewModel.login(email, password)
                            if (user != null) {
                                onLoginSuccess(user)
                            } else {
                                Toast.makeText(context, "Akun tidak ditemukan. Silakan register.", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Register.route)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBA68C8)
                    )
                ) {
                    Text("Login", color = Color.White)
                }

                TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                    Text("Belum punya akun? Register di sini", color = Color(0xFF6A1B9A))
                }
            }
        }
    }
}
