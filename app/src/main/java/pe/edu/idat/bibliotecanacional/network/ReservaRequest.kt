package pe.edu.idat.bibliotecanacional.network

data class ReservaRequest(
    val usuario: Int,
    val espacio: String,
    val fechaReserva: String,
    val horaInicio: String,
    val horaFin: String
)