package cr.ac.una.paninisupport.ui.tickets.detail

import androidx.lifecycle.ViewModel
import cr.ac.una.paninisupport.domain.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicketDetailViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketDetailUiState())
    val uiState: StateFlow<TicketDetailUiState> = _uiState.asStateFlow()

    fun loadTicket(ticketId: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        val ticket = ticketRepository.getTicketById(ticketId)
        if (ticket == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    ticket = null,
                    errorMessage = "No se encontró el ticket solicitado."
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    ticket = ticket,
                    errorMessage = null
                )
            }
        }
    }
}
