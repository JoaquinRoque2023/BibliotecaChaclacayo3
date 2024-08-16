package pe.edu.idat.bibliotecanacional.network



data class Libro(
    val idLibro: Int,
    val titulo: String,
    val autor: String,
    val editorial: String,
    val añoPublicacion: Int,
    val isbn: String,
    val categoria: String,
    val ubicacion: String,
    val estado: String
)

