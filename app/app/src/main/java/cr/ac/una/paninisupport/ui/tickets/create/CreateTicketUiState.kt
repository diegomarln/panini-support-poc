package cr.ac.una.paninisupport.ui.tickets.create

import cr.ac.una.paninisupport.domain.model.TicketCategory
import cr.ac.una.paninisupport.domain.model.TicketPriority

data class CreateTicketUiState(
    val title: String = "",
    val description: String = "",
    val supplier: String = "",
    val category: TicketCategory = TicketCategory.Distribution,
    val priority: TicketPriority = TicketPriority.Medium,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val isCreated: Boolean = false
)
