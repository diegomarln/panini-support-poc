package cr.ac.una.paninisupport.data.repository

import cr.ac.una.paninisupport.data.mock.MockTicketDataSource
import cr.ac.una.paninisupport.domain.event.TicketEvent
import cr.ac.una.paninisupport.domain.model.Ticket
import cr.ac.una.paninisupport.domain.model.TicketCategory
import cr.ac.una.paninisupport.domain.model.TicketPriority
import cr.ac.una.paninisupport.domain.model.TicketStatus
import cr.ac.una.paninisupport.domain.repository.TicketRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InMemoryTicketRepository(
    initialTickets: List<Ticket> = MockTicketDataSource.getTickets()
) : TicketRepository {

    private val _tickets: MutableStateFlow<List<Ticket>> =
        MutableStateFlow(sortByPriority(initialTickets))

    override val tickets: StateFlow<List<Ticket>> = _tickets.asStateFlow()

    private val _events: MutableSharedFlow<TicketEvent> =
        MutableSharedFlow(extraBufferCapacity = 16)

    override val events: SharedFlow<TicketEvent> = _events.asSharedFlow()

    override fun getTicketById(ticketId: String): Ticket? =
        _tickets.value.firstOrNull { it.id == ticketId }

    override fun createTicket(
        title: String,
        description: String,
        supplier: String,
        category: TicketCategory,
        priority: TicketPriority
    ): Ticket {
        val current = _tickets.value
        val newTicket = Ticket(
            id = nextTicketId(current),
            title = title,
            description = description,
            supplier = supplier,
            createdAt = today(),
            category = category,
            priority = priority,
            status = TicketStatus.Open
        )
        _tickets.value = sortByPriority(current + newTicket)
        _events.tryEmit(TicketEvent.TicketCreated(newTicket.id))
        return newTicket
    }

    private fun nextTicketId(current: List<Ticket>): String {
        val maxNumber = current
            .mapNotNull { it.id.removePrefix("TCK-").toIntOrNull() }
            .maxOrNull() ?: 0
        return "TCK-%03d".format(maxNumber + 1)
    }

    private fun today(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

    private fun sortByPriority(items: List<Ticket>): List<Ticket> =
        items.sortedWith(
            compareByDescending<Ticket> { it.priority.sortOrder }
                .thenByDescending { it.createdAt }
        )
}
