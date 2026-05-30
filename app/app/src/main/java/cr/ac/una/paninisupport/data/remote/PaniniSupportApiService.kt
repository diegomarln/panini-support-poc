package cr.ac.una.paninisupport.data.remote

import cr.ac.una.paninisupport.data.remote.dto.CreateTicketRequestDto
import cr.ac.una.paninisupport.data.remote.dto.TicketDto
import cr.ac.una.paninisupport.data.remote.dto.UpdateTicketPriorityRequestDto
import cr.ac.una.paninisupport.data.remote.dto.UpdateTicketStatusRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PaniniSupportApiService {

    @GET("tickets")
    suspend fun getTickets(): List<TicketDto>

    @POST("tickets")
    suspend fun createTicket(@Body request: CreateTicketRequestDto): TicketDto

    @GET("tickets/{ticketId}")
    suspend fun getTicketById(@Path("ticketId") ticketId: String): TicketDto

    @PATCH("tickets/{ticketId}/status")
    suspend fun updateTicketStatus(
        @Path("ticketId") ticketId: String,
        @Body request: UpdateTicketStatusRequestDto
    ): TicketDto

    @PATCH("tickets/{ticketId}/priority")
    suspend fun updateTicketPriority(
        @Path("ticketId") ticketId: String,
        @Body request: UpdateTicketPriorityRequestDto
    ): TicketDto
}
