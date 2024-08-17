package pe.edu.idat.bibliotecanacional.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pe.edu.idat.bibliotecanacional.network.LoginResponse
import pe.edu.idat.bibliotecanacional.screens.*

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("principal") {
            PrincipalScreen(navController = navController)
        }
        composable("listadoLibros") {
            ListadoLibrosScreen(navController = navController)
        }
        composable("prestamoLibros") {
            PrestamoLibrosScreen(navController = navController)
        }
        composable("devolucionLibros") {
            DevolucionLibrosScreen(navController = navController)
        }
        composable("registro") {
            RegistroScreen(navController)
        }
        composable("cambiarClave") {
            CambiarClaveScreen(navController)
        }

    }
}
