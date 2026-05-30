package cr.ac.una.paninisupport.core.network

import cr.ac.una.paninisupport.data.remote.PaniniSupportApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // URL marcador para la futura integración con el backend de Panini Support.
    // Para un backend local en la máquina del desarrollador, el emulador Android
    // accede al host vía 10.0.2.2 (por ejemplo, http://10.0.2.2:8080/). Mientras
    // no exista backend real, esta URL queda como placeholder y la capa de red
    // no se conecta aún al repositorio de tickets.
    private const val BASE_URL: String = "https://api.panini-support.example/"

    private fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    private fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: PaniniSupportApiService by lazy {
        retrofit.create(PaniniSupportApiService::class.java)
    }
}
