package cr.ac.una.paninisupport.ui.tickets.list

import cr.ac.una.paninisupport.domain.model.Ticket

data class TicketListUiState(
    val isLoading: Boolean = false,
    val tickets: List<Ticket> = emptyList(),
    val errorMessage: String? = null
)
