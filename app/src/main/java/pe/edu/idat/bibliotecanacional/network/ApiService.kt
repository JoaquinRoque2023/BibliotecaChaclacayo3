package pe.edu.idat.bibliotecanacional.network


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


data class LoginRequest(val email: String, val clave: String)

interface ApiService {
    @POST("biblioteca/usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>


    @POST("biblioteca/usuarios")
    fun register(@Body registerRequest: LoginRequest): Call<Void>

    @GET("biblioteca/libros")
    fun getLibros(): Call<List<Libro>>

    @POST("biblioteca/prestamos-libros")
    fun registrarPrestamo(@Body prestamoRequest: PrestamoRequest): Call<Void>

    @POST("biblioteca/devoluciones-libros")
    fun registrarDevolucion(@Body devolucionRequest: DevolucionRequest): Call<Void>

    @POST("biblioteca/usuarios")
    fun registerUsuario(@Body request: UsuarioRequest): Call<RegistroResponse>

    @PUT("biblioteca/usuarios/cambiar-clave")
    fun cambiarClave(@Body requestBody: Map<String, String>): Call<Void>

    @PUT("biblioteca/libros/{id}")
    fun actualizarEstadoLibro(@Path("id") idLibro: Int, @Body libro: LibroRequest): Call<Void>

    @PUT("biblioteca/usuarios/{id}")
    fun actualizarUsuario(
        @Path("id") id: Int,
        @Body usuarioRequest: UsuarioUpdateRequest
    ): Call<Void>


        @POST("biblioteca/alertas")
        fun enviarAlerta(@Body alertaRequest: AlertaRequest): Call<Void>

    @GET("biblioteca/reservas-espacios-comunes")
    fun listarReservas(): Call<List<ReservaEspacio>>

    @POST("biblioteca/reservas-espacios-comunes")
    fun registrarReserva(@Body reservaRequest: ReservaRequest): Call<Void>

}
