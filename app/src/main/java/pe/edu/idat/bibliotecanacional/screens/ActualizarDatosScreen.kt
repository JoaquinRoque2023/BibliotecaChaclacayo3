package pe.edu.idat.bibliotecanacional.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.ExperimentalMaterial3Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import pe.edu.idat.bibliotecanacional.network.RetrofitClient
import pe.edu.idat.bibliotecanacional.network.UsuarioUpdateRequest
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarDatosUsuarioScreen(navController: NavHostController, usuarioId: Int) {
    var clave by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf<String?>(null) }

    // Using Calendar to get the current date with proper formatting
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
    val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
    val fechaRegistro = "$year-$month-$day"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Actualizar Datos del Usuario", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // User input fields
        TextField(value = clave, onValueChange = { clave = it }, label = { Text("Clave") }, modifier = Modifier.fillMaxWidth())
        TextField(value = nombres, onValueChange = { nombres = it }, label = { Text("Nombres") }, modifier = Modifier.fillMaxWidth())
        TextField(value = apellidoPaterno, onValueChange = { apellidoPaterno = it }, label = { Text("Apellido Paterno") }, modifier = Modifier.fillMaxWidth())
        TextField(value = apellidoMaterno, onValueChange = { apellidoMaterno = it }, label = { Text("Apellido Materno") }, modifier = Modifier.fillMaxWidth())
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        TextField(value = celular, onValueChange = { celular = it }, label = { Text("Celular") }, modifier = Modifier.fillMaxWidth())
        TextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Update button
        Button(onClick = {
            val usuarioRequest = UsuarioUpdateRequest(
                clave = clave,
                nombres = nombres,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                email = email,
                celular = celular,
                direccion = direccion,
                fechaRegistro = fechaRegistro,  // Pass the current date
                estado = "ACTIVO"  // Ensure estado is always set to "ACTIVO"
            )

            RetrofitClient.apiService.actualizarUsuario(usuarioId, usuarioRequest)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    mensaje = if (response.isSuccessful) {
                        "Datos actualizados exitosamente"
                    } else {
                        "Error al actualizar los datos"
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mensaje = "Fallo de red: ${t.message}"
                }
            })
        }) {
            Text("Actualizar Datos")
        }

        mensaje?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Volver")
        }
    }
}
