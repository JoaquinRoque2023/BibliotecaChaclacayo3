package pe.edu.idat.bibliotecanacional.screens


import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.Text

import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.platform.LocalContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import pe.edu.idat.bibliotecanacional.network.ReservaEspacio
import pe.edu.idat.bibliotecanacional.network.RetrofitClient

@Composable
fun ReservasEspaciosScreen(navController: NavHostController) {
    val reservas = remember { mutableStateOf<List<ReservaEspacio>>(emptyList()) }
    val context = LocalContext.current

    // Fetch reservations
    LaunchedEffect(Unit) {
        RetrofitClient.apiService.listarReservas().enqueue(object : Callback<List<ReservaEspacio>> {
            override fun onResponse(call: Call<List<ReservaEspacio>>, response: Response<List<ReservaEspacio>>) {
                if (response.isSuccessful) {
                    reservas.value = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Error al obtener reservas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ReservaEspacio>>, t: Throwable) {
                Toast.makeText(context, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Listado de Reservas de Espacios", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(reservas.value) { reserva ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Espacio: ${reserva.espacio}")
                    Text("Fecha: ${reserva.fechaReserva}")
                    Text("Hora Inicio: ${reserva.horaInicio}")
                    Text("Hora Fin: ${reserva.horaFin}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("registrarReserva") }) {
            Text("Registrar Reserva")
        }
    }
}


