package pe.edu.idat.bibliotecanacional.network

data class CambiarClaveRequest(
    val email: String,
    val claveAnterior: String,
    val nuevaClave: String
)