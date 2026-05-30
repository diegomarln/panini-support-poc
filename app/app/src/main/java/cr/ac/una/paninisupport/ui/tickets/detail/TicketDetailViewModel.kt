package cr.ac.una.paninisupport.ui.tickets.detail

import androidx.lifecycle.ViewModel
import cr.ac.una.paninisupport.domain.model.TicketPriority
import cr.ac.una.paninisupport.domain.model.TicketStatus
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
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null, successMessage = null)
        }
        val ticket = ticketRepository.getTicketById(ticketId)
        if (ticket == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    ticket = null,
                    errorMessage = "No se encontró el ticket solicitado.",
                    successMessage = null
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    ticket = ticket,
                    errorMessage = null,
                    successMessage = null
                )
            }
        }
    }

    fun onStatusSelected(status: TicketStatus) {
        val ticketId = _uiState.value.ticket?.id
        if (ticketId == null) {
            _uiState.update {
                it.copy(
                    errorMessage = "No hay un ticket cargado para actualizar.",
                    successMessage = null
                )
            }
            return
        }
        val updated = ticketRepository.updateTicketStatus(ticketId, status)
        if (updated == null) {
            _uiState.update {
                it.copy(
                    errorMessage = "No se pudo actualizar el estado del ticket.",
                    successMessage = null
                )
            }
            return
        }
        _uiState.update {
            it.copy(
                ticket = updated,
                errorMessage = null,
                successMessage = "Estado actualizado correctamente."
            )
        }
    }

    fun onPrioritySelected(priority: TicketPriority) {
        val ticketId = _uiState.value.ticket?.id
        if (ticketId == null) {
            _uiState.update {
                it.copy(
                    errorMessage = "No hay un ticket cargado para actualizar.",
                    successMessage = null
                )
            }
            return
        }
        val updated = ticketRepository.updateTicketPriority(ticketId, priority)
        if (updated == null) {
            _uiState.update {
                it.copy(
                    errorMessage = "No se pudo actualizar la prioridad del ticket.",
                    successMessage = null
                )
            }
            return
        }
        _uiState.update {
            it.copy(
                ticket = updated,
                errorMessage = null,
                successMessage = "Prioridad actualizada correctamente."
            )
        }
    }
}
