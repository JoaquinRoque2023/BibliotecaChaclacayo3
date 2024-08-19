package pe.edu.idat.bibliotecanacional.network

data class LibroRequest(
    val idLibro: Int,
    val estado: String // Agregamos el campo estado
)

data class LibroR(
    val idLibro: Int
)