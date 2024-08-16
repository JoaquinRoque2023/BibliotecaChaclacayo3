package pe.edu.idat.bibliotecanacional.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pe.edu.idat.bibliotecanacional.network.DevolucionRequest
import pe.edu.idat.bibliotecanacional.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment

@Composable
fun DevolucionLibrosScreen(navController: NavHostController) {
    var libroId by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar Devolución de Libro", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = libroId,
            onValueChange = { libroId = it },
            label = { Text("ID del Libro") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val devolucionRequest = DevolucionRequest(libroId.toInt())
            RetrofitClient.apiService.registrarDevolucion(devolucionRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    mensaje = if (response.isSuccessful) {
                        "Devolución registrada exitosamente"
                    } else {
                        "Error al registrar la devolución"
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mensaje = "Fallo de red: ${t.message}"
                }
            })
        }) {
            Text("Registrar Devolución")
        }

        mensaje?.let {
            Text(it, color = MaterialTheme.colors.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Volver")
        }
    }
}
