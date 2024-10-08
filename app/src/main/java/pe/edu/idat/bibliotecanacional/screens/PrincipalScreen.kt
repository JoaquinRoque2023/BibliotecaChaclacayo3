package pe.edu.idat.bibliotecanacional.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun PrincipalScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { navController.navigate("listadoLibros") }) {
            Text("Listado de Libros")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("prestamoLibros") }) {
            Text("Préstamo de Libros")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("devolucionLibros") }) {
            Text("Devolución de Libros")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para actualizar datos del usuario
        Button(onClick = {
            val usuarioId = 2 // Reemplazar con el ID real del usuario
            navController.navigate("actualizarDatos/$usuarioId")
        }) {
            Text("Actualizar Datos del Usuario")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Nuevo botón para gestionar reservas de espacios
        Button(onClick = {
            navController.navigate("reservasEspacios")
        }) {
            Text("Reservas de Espacios")
        }
    }
}

