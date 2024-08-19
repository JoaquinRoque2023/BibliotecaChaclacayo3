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

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.compose.ui.platform.LocalContext

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import pe.edu.idat.bibliotecanacional.network.ReservaRequest
import pe.edu.idat.bibliotecanacional.network.RetrofitClient

@Composable
fun RegistrarReservaScreen(navController: NavHostController) {
    var espacio by remember { mutableStateOf("") }
    var fechaReserva by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("Registrar Reserva de Espacio", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = espacio,
                onValueChange = { espacio = it },
                label = { Text("Espacio") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = fechaReserva,
                onValueChange = { fechaReserva = it },
                label = { Text("Fecha Reserva") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = horaInicio,
                onValueChange = { horaInicio = it },
                label = { Text("Hora Inicio") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = horaFin,
                onValueChange = { horaFin = it },
                label = { Text("Hora Fin") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            mensaje?.let {
                Text(it, color = MaterialTheme.colorScheme.primary)
            }
        }

        // Fila de botones en la parte inferior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                val reservaRequest = ReservaRequest(
                    usuario = 2, // Esto debería ser reemplazado por el ID del usuario actual
                    espacio = espacio,
                    fechaReserva = fechaReserva,
                    horaInicio = horaInicio,
                    horaFin = horaFin
                )

                RetrofitClient.apiService.registrarReserva(reservaRequest).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        mensaje = if (response.isSuccessful) {
                            "Reserva registrada exitosamente"
                        } else {
                            "Error al registrar la reserva"
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        mensaje = "Fallo de red: ${t.message}"
                    }
                })
            }) {
                Text("Registrar Reserva")
            }

            Button(onClick = { navController.navigateUp() }) {
                Text("Volver")
            }
        }
    }
}

