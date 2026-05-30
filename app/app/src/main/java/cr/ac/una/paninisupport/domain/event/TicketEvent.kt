package cr.ac.una.paninisupport.domain.event

sealed interface TicketEvent {
    data class TicketCreated(val ticketId: String) : TicketEvent
}
