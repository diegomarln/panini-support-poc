package cr.ac.una.paninisupport.ui.tickets.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.una.paninisupport.domain.repository.TicketRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TicketListViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    val uiState: StateFlow<TicketListUiState> = ticketRepository.tickets
        .map { tickets -> TicketListUiState(isLoading = false, tickets = tickets) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TicketListUiState(isLoading = true)
        )
}
