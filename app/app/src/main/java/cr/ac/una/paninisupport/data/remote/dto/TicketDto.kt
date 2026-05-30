package cr.ac.una.paninisupport.data.remote.dto

data class TicketDto(
    val id: String,
    val title: String,
    val description: String,
    val supplier: String,
    val createdAt: String,
    val category: String,
    val priority: String,
    val status: String
)
