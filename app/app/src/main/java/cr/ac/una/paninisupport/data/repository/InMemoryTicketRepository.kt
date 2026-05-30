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

    override fun updateTicketStatus(ticketId: String, status: TicketStatus): Ticket? {
        val updated = replaceTicket(ticketId) { it.copy(status = status) } ?: return null
        _events.tryEmit(TicketEvent.TicketStatusUpdated(ticketId))
        return updated
    }

    override fun updateTicketPriority(ticketId: String, priority: TicketPriority): Ticket? {
        val updated = replaceTicket(ticketId) { it.copy(priority = priority) } ?: return null
        _events.tryEmit(TicketEvent.TicketPriorityUpdated(ticketId))
        return updated
    }

    private fun replaceTicket(ticketId: String, transform: (Ticket) -> Ticket): Ticket? {
        val current = _tickets.value
        val index = current.indexOfFirst { it.id == ticketId }
        if (index < 0) return null
        val updated = transform(current[index])
        val newList = current.toMutableList().also { it[index] = updated }
        _tickets.value = sortByPriority(newList)
        return updated
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
