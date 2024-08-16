package pe.edu.idat.bibliotecanacional.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.idat.bibliotecanacional.network.PrestamoRequest
import pe.edu.idat.bibliotecanacional.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment

@Composable
fun PrestamoLibrosScreen(navController: NavHostController) {
    var libroId by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar Préstamo de Libro", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = libroId,
            onValueChange = { libroId = it },
            label = { Text("ID del Libro") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("ID del Usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val prestamoRequest = PrestamoRequest(libroId.toInt(), userId.toInt())
            RetrofitClient.apiService.registrarPrestamo(prestamoRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    mensaje = if (response.isSuccessful) {
                        "Préstamo registrado exitosamente"
                    } else {
                        "Error al registrar el préstamo"
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mensaje = "Fallo de red: ${t.message}"
                }
            })
        }) {
            Text("Registrar Préstamo")
        }

        mensaje?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}
