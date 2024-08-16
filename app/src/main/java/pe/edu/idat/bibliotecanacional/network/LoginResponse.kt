package pe.edu.idat.bibliotecanacional.network


data class LoginResponse(
    val idUsuario: Int,
    val clave: String,
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val email: String,
    val celular: String,
    val direccion: String,
    val fechaRegistro: String,
    val estado: String
)
