package cr.ac.una.paninisupport.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cr.ac.una.paninisupport.domain.repository.TicketRepository
import cr.ac.una.paninisupport.ui.tickets.detail.TicketDetailViewModel
import cr.ac.una.paninisupport.ui.tickets.list.TicketListViewModel

class TicketViewModelFactory(
    private val ticketRepository: TicketRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TicketListViewModel::class.java) ->
                TicketListViewModel(ticketRepository) as T
            modelClass.isAssignableFrom(TicketDetailViewModel::class.java) ->
                TicketDetailViewModel(ticketRepository) as T
            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}
