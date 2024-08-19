package pe.edu.idat.bibliotecanacional.network

data class ReservaEspacio(
    val idReservaEspacio: Int,
    val espacio: String,
    val fechaReserva: String,
    val horaInicio: String,
    val horaFin: String
)