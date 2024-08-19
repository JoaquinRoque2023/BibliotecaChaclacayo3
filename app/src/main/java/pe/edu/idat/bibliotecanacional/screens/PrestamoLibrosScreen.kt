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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import pe.edu.idat.bibliotecanacional.network.LibroR
import pe.edu.idat.bibliotecanacional.network.LibroRequest
import pe.edu.idat.bibliotecanacional.network.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamoLibrosScreen(navController: NavHostController) {
    var libroId by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            val prestamoRequest = PrestamoRequest(
                usuario = Usuario(idUsuario = userId.toInt()),  // Crear un objeto Usuario
                libro = LibroR(idLibro = libroId.toInt())       // Crear un objeto Libro
            )

            // Registrar el préstamo del libro
            RetrofitClient.apiService.registrarPrestamo(prestamoRequest)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // Cambiar el estado del libro a INACTIVO
                            val libroInactivo = LibroRequest(
                                idLibro = libroId.toInt(),
                                estado = "INACTIVO" // Establecer el estado a INACTIVO
                            )
                            RetrofitClient.apiService.actualizarEstadoLibro(libroId.toInt(), libroInactivo)
                                .enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        mensaje = if (response.isSuccessful) {
                                            "Préstamo registrado y libro actualizado exitosamente"
                                        } else {
                                            "Error al actualizar el estado del libro"
                                        }
                                    }

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        mensaje = "Fallo de red al actualizar el libro: ${t.message}"
                                    }
                                })
                        } else {
                            mensaje = "Error al registrar el préstamo"
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

