package cr.ac.una.paninisupport.domain.repository

import cr.ac.una.paninisupport.domain.model.Ticket
import kotlinx.coroutines.flow.StateFlow

interface TicketRepository {
    val tickets: StateFlow<List<Ticket>>
    fun getTicketById(ticketId: String): Ticket?
}
