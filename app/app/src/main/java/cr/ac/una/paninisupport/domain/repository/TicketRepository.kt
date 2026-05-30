package cr.ac.una.paninisupport.domain.repository

import cr.ac.una.paninisupport.domain.event.TicketEvent
import cr.ac.una.paninisupport.domain.model.Ticket
import cr.ac.una.paninisupport.domain.model.TicketCategory
import cr.ac.una.paninisupport.domain.model.TicketPriority
import cr.ac.una.paninisupport.domain.model.TicketStatus
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TicketRepository {
    val tickets: StateFlow<List<Ticket>>
    val events: SharedFlow<TicketEvent>
    fun getTicketById(ticketId: String): Ticket?
    fun createTicket(
        title: String,
        description: String,
        supplier: String,
        category: TicketCategory,
        priority: TicketPriority
    ): Ticket
    fun updateTicketStatus(ticketId: String, status: TicketStatus): Ticket?
    fun updateTicketPriority(ticketId: String, priority: TicketPriority): Ticket?
}
