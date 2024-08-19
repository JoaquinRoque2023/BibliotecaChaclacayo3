package pe.edu.idat.bibliotecanacional.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import pe.edu.idat.bibliotecanacional.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import pe.edu.idat.bibliotecanacional.network.LoginRequest
import pe.edu.idat.bibliotecanacional.network.LoginResponse
import pe.edu.idat.bibliotecanacional.network.RetrofitClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
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
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineSmall.copy(
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
                colors = TextFieldDefaults.textFieldColors()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text("Clave") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val loginRequest = LoginRequest(email, clave)

                    RetrofitClient.apiService.login(loginRequest)
                        .enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val loginResponse = response.body()
                                    loginResponse?.let {
                                        Log.d(
                                            "LoginScreen",
                                            "Inicio de sesión exitoso. Usuario: ${it.nombres} ${it.apellidoPaterno}"
                                        )
                                        navController.navigate("principal") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                } else {
                                    errorMessage = "Usuario y/o contraseña no existe"
                                    Log.e("LoginScreen", "Error en el inicio de sesión: ${response.message()}")
                                }
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                errorMessage = "Error de red: ${t.message}"
                                Log.e("LoginScreen", "Fallo en la red: ${t.message}")
                            }
                        })
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                )
            ) {
                Text("Iniciar Sesión")
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            val annotatedText = buildAnnotatedString {
                append("¿No tienes una cuenta? ")
                pushStringAnnotation(tag = "REGISTRO", annotation = "registro")
                withStyle(style = SpanStyle(color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)) {
                    append("Regístrate aquí")
                }
                pop()
            }

            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "REGISTRO", start = offset, end = offset)
                        .firstOrNull()?.let {
                            navController.navigate("registro")
                        }
                },
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("cambiarClave")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("Cambiar Contraseña")
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

        }
    }
}
