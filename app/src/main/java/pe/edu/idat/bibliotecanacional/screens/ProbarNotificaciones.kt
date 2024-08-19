package pe.edu.idat.bibliotecanacional.screens


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import pe.edu.idat.bibliotecanacional.network.AlertaRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import pe.edu.idat.bibliotecanacional.network.RetrofitClient


@Composable
fun ProbarNotificacionesScreen(navController: NavHostController) {
    var mensaje by remember { mutableStateOf("") }
    var estadoMensaje by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Probar Notificaciones", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            label = { Text("Mensaje de Notificación") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val alertaRequest = AlertaRequest(mensaje)
            RetrofitClient.apiService.enviarAlerta(alertaRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    estadoMensaje = if (response.isSuccessful) {
                        "Notificación enviada exitosamente"
                    } else {
                        "Error al enviar notificación"
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    estadoMensaje = "Fallo de red: ${t.message}"
                }
            })
        }) {
            Text("Enviar Notificación")
        }

        estadoMensaje?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("principal") }) {
            Text("Volver al Principal")
        }
    }
}
