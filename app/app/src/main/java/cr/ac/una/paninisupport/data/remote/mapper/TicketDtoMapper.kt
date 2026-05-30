package cr.ac.una.paninisupport.data.remote.mapper

import cr.ac.una.paninisupport.data.remote.dto.TicketDto
import cr.ac.una.paninisupport.domain.model.Ticket
import cr.ac.una.paninisupport.domain.model.TicketCategory
import cr.ac.una.paninisupport.domain.model.TicketPriority
import cr.ac.una.paninisupport.domain.model.TicketStatus

fun TicketDto.toDomain(): Ticket = Ticket(
    id = id,
    title = title,
    description = description,
    supplier = supplier,
    createdAt = createdAt,
    category = category.toCategoryOrDefault(),
    priority = priority.toPriorityOrDefault(),
    status = status.toStatusOrDefault()
)

fun Ticket.toDto(): TicketDto = TicketDto(
    id = id,
    title = title,
    description = description,
    supplier = supplier,
    createdAt = createdAt,
    category = category.toApiValue(),
    priority = priority.toApiValue(),
    status = status.toApiValue()
)

fun TicketCategory.toApiValue(): String = when (this) {
    TicketCategory.Distribution -> "distribution"
    TicketCategory.Inventory -> "inventory"
    TicketCategory.Supplier -> "supplier"
    TicketCategory.Quality -> "quality"
    TicketCategory.PointOfSale -> "point_of_sale"
    TicketCategory.Logistics -> "logistics"
}

fun TicketPriority.toApiValue(): String = when (this) {
    TicketPriority.Critical -> "critical"
    TicketPriority.High -> "high"
    TicketPriority.Medium -> "medium"
    TicketPriority.Low -> "low"
}

fun TicketStatus.toApiValue(): String = when (this) {
    TicketStatus.Open -> "open"
    TicketStatus.InProgress -> "in_progress"
    TicketStatus.Blocked -> "blocked"
    TicketStatus.Resolved -> "resolved"
}

private fun String.toCategoryOrDefault(): TicketCategory = when (this.lowercase()) {
    "distribution" -> TicketCategory.Distribution
    "inventory" -> TicketCategory.Inventory
    "supplier" -> TicketCategory.Supplier
    "quality" -> TicketCategory.Quality
    "point_of_sale" -> TicketCategory.PointOfSale
    "logistics" -> TicketCategory.Logistics
    else -> TicketCategory.Distribution
}

private fun String.toPriorityOrDefault(): TicketPriority = when (this.lowercase()) {
    "critical" -> TicketPriority.Critical
    "high" -> TicketPriority.High
    "medium" -> TicketPriority.Medium
    "low" -> TicketPriority.Low
    else -> TicketPriority.Medium
}

private fun String.toStatusOrDefault(): TicketStatus = when (this.lowercase()) {
    "open" -> TicketStatus.Open
    "in_progress" -> TicketStatus.InProgress
    "blocked" -> TicketStatus.Blocked
    "resolved" -> TicketStatus.Resolved
    else -> TicketStatus.Open
}
