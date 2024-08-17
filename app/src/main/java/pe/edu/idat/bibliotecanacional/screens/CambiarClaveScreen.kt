package pe.edu.idat.bibliotecanacional.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import pe.edu.idat.bibliotecanacional.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import pe.edu.idat.bibliotecanacional.network.LoginRequest
import pe.edu.idat.bibliotecanacional.network.LoginResponse
import pe.edu.idat.bibliotecanacional.network.RetrofitClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarClaveScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var claveAnterior by remember { mutableStateOf("") }
    var nuevaClave by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .background(Color.White)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cambiar Contraseña",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = claveAnterior,
                onValueChange = { claveAnterior = it },
                label = { Text("Clave Anterior") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nuevaClave,
                onValueChange = { nuevaClave = it },
                label = { Text("Nueva Clave") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val cambiarClaveRequest = mapOf(
                        "email" to email,
                        "claveAnterior" to claveAnterior,
                        "nuevaClave" to nuevaClave
                    )

                    RetrofitClient.apiService.cambiarClave(cambiarClaveRequest)
                        .enqueue(object : Callback<Void> {
                            override fun onResponse(
                                call: Call<Void>,
                                response: Response<Void>
                            ) {
                                if (response.isSuccessful) {
                                    navController.navigate("login") {
                                        popUpTo("cambiarClave") { inclusive = true }
                                    }
                                } else {
                                    errorMessage = "Error en el cambio de contraseña"
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                errorMessage = "Error de red: ${t.message}"
                            }
                        })
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                )
            ) {
                Text("Cambiar Contraseña")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("cambiarClave") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("Volver al Login")
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
