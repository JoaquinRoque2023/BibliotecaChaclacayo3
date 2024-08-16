package pe.edu.idat.bibliotecanacional.screens


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.idat.bibliotecanacional.network.RetrofitClient
import pe.edu.idat.bibliotecanacional.network.LoginResponse
import pe.edu.idat.bibliotecanacional.network.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Clave") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val loginRequest = LoginRequest(email, clave)

            RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        loginResponse?.let {
                            Log.d("LoginScreen", "Inicio de sesión exitoso. Usuario: ${it.nombres} ${it.apellidoPaterno}")
                            // Redirige a la vista principal
                            navController.navigate("principal") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    } else {
                        errorMessage = "Error en el inicio de sesión"
                        Log.e("LoginScreen", "Error en el inicio de sesión: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    errorMessage = "Error de red: ${t.message}"
                    Log.e("LoginScreen", "Fallo en la red: ${t.message}")
                }
            })
        }) {
            Text("Iniciar Sesión")
        }

        errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
