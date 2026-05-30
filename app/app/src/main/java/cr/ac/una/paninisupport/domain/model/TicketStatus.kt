package cr.ac.una.paninisupport.domain.model

enum class TicketStatus(val label: String) {
    Open(label = "Abierto"),
    InProgress(label = "En progreso"),
    Blocked(label = "Bloqueado"),
    Resolved(label = "Resuelto")
}
