package cr.ac.una.paninisupport.data.remote.dto

data class CreateTicketRequestDto(
    val title: String,
    val description: String,
    val supplier: String,
    val category: String,
    val priority: String
)
