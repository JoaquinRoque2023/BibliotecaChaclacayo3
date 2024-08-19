package pe.edu.idat.bibliotecanacional.network

data class UsuarioUpdateRequest(
    val clave: String,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val email: String,
    val celular: String,
    val fechaRegistro: String,
    val estado: String = "ACTIVO",
    val direccion: String
)