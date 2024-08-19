package pe.edu.idat.bibliotecanacional.network

data class PrestamoRequest(
    val usuario: Usuario,  // Cambiar de idUsuario a Usuario
    val libro: LibroR       // Cambiar de idLibro a Libro
)
