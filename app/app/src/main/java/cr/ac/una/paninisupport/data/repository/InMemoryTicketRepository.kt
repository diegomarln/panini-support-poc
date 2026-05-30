package cr.ac.una.paninisupport.data.repository

import cr.ac.una.paninisupport.data.mock.MockTicketDataSource
import cr.ac.una.paninisupport.domain.model.Ticket
import cr.ac.una.paninisupport.domain.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryTicketRepository(
    initialTickets: List<Ticket> = MockTicketDataSource.getTickets()
) : TicketRepository {

    private val _tickets: MutableStateFlow<List<Ticket>> =
        MutableStateFlow(sortByPriority(initialTickets))

    override val tickets: StateFlow<List<Ticket>> = _tickets.asStateFlow()

    override fun getTicketById(ticketId: String): Ticket? =
        _tickets.value.firstOrNull { it.id == ticketId }

    private fun sortByPriority(items: List<Ticket>): List<Ticket> =
        items.sortedWith(
            compareByDescending<Ticket> { it.priority.sortOrder }
                .thenByDescending { it.createdAt }
        )
}
