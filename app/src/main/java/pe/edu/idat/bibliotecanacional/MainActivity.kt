package pe.edu.idat.bibliotecanacional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import pe.edu.idat.bibliotecanacional.core.NavigationGraph
import pe.edu.idat.bibliotecanacional.ui.theme.BibliotecaNacionalTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaNacionalTheme {
                val navController = rememberNavController()
                NavigationGraph(navController = navController)
            }
        }
    }
}
