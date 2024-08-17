package pe.edu.idat.bibliotecanacional.network

data class UsuarioRequest(
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val email: String,
    val celular: String,
    val direccion: String,
    val clave: String
)