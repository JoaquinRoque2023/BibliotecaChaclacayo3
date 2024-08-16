package pe.edu.idat.bibliotecanacional.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import pe.edu.idat.bibliotecanacional.network.Libro
import pe.edu.idat.bibliotecanacional.network.RetrofitClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pe.edu.idat.bibliotecanacional.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.navigation.NavHostController



@Composable
fun ListadoLibrosScreen(navController: NavHostController) {
    var libros by remember { mutableStateOf<List<Libro>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Llamar a la API para obtener el listado de libros
        RetrofitClient.apiService.getLibros().enqueue(object : Callback<List<Libro>> {
            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                if (response.isSuccessful) {
                    libros = response.body() ?: emptyList()
                    Log.i("response","respuesta de api")
                } else {
                    Log.e("ListadoLibros", "Error al obtener los libros: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                Log.e("ListadoLibros", "Fallo de red: ${t.message}")
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Listado de Libros", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(libros) { libro ->
                Column(modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        // Placeholder para la imagen del libro
                        Image(
                            painter = painterResource(id = R.drawable.loan_icon),
                            contentDescription = "Portada del libro",
                            modifier = Modifier
                                .size(64.dp)
                                .padding(end = 16.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column {
                            Text("Título: ${libro.titulo}", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                            Text("Autor: ${libro.autor}", color = Color.Black)
                            Text("Editorial: ${libro.editorial}", color = Color.Black)
                            Text("Año de Publicación: ${libro.añoPublicacion}", color = Color.Black)
                            Text("ISBN: ${libro.isbn}", color = Color.Black)
                            Text("Categoría: ${libro.categoria}", color = Color.Black)
                            Text("Ubicación: ${libro.ubicacion}", color = Color.Black)
                            Text("Estado: ${libro.estado}", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}