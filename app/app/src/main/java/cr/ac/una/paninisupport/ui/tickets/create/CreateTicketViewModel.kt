package cr.ac.una.paninisupport.ui.tickets.create

import androidx.lifecycle.ViewModel
import cr.ac.una.paninisupport.domain.model.TicketCategory
import cr.ac.una.paninisupport.domain.model.TicketPriority
import cr.ac.una.paninisupport.domain.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateTicketViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTicketUiState())
    val uiState: StateFlow<CreateTicketUiState> = _uiState.asStateFlow()

    fun onTitleChanged(value: String) {
        _uiState.update { it.copy(title = value, errorMessage = null) }
    }

    fun onDescriptionChanged(value: String) {
        _uiState.update { it.copy(description = value, errorMessage = null) }
    }

    fun onSupplierChanged(value: String) {
        _uiState.update { it.copy(supplier = value, errorMessage = null) }
    }

    fun onCategorySelected(category: TicketCategory) {
        _uiState.update { it.copy(category = category) }
    }

    fun onPrioritySelected(priority: TicketPriority) {
        _uiState.update { it.copy(priority = priority) }
    }

    fun save() {
        val current = _uiState.value
        if (current.isSaving || current.isCreated) return

        val title = current.title.trim()
        val description = current.description.trim()
        val supplier = current.supplier.trim()

        when {
            title.isBlank() -> {
                _uiState.update {
                    it.copy(errorMessage = "Ingrese el título del ticket.")
                }
                return
            }
            description.isBlank() -> {
                _uiState.update {
                    it.copy(errorMessage = "Ingrese una descripción del incidente.")
                }
                return
            }
            supplier.isBlank() -> {
                _uiState.update {
                    it.copy(errorMessage = "Indique el proveedor o el equipo interno asociado.")
                }
                return
            }
        }

        _uiState.update { it.copy(isSaving = true, errorMessage = null) }
        ticketRepository.createTicket(
            title = title,
            description = description,
            supplier = supplier,
            category = current.category,
            priority = current.priority
        )
        _uiState.update { it.copy(isSaving = false, isCreated = true) }
    }
}
