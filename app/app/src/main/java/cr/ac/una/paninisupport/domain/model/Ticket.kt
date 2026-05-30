package cr.ac.una.paninisupport.domain.model

data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val supplier: String,
    val createdAt: String,
    val category: TicketCategory,
    val priority: TicketPriority,
    val status: TicketStatus
)
