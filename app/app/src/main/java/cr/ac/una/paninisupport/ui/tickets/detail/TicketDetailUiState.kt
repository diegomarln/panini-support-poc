package cr.ac.una.paninisupport.ui.tickets.detail

import cr.ac.una.paninisupport.domain.model.Ticket

data class TicketDetailUiState(
    val isLoading: Boolean = false,
    val ticket: Ticket? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
