package cr.ac.una.paninisupport.core.di

import cr.ac.una.paninisupport.data.repository.InMemoryTicketRepository
import cr.ac.una.paninisupport.domain.repository.TicketRepository

class AppContainer {
    val ticketRepository: TicketRepository = InMemoryTicketRepository()
}
