package pe.edu.idat.bibliotecanacional.network


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


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
}
