package cr.ac.una.paninisupport.domain.model

enum class TicketPriority(val label: String, val sortOrder: Int) {
    Critical(label = "Crítica", sortOrder = 4),
    High(label = "Alta", sortOrder = 3),
    Medium(label = "Media", sortOrder = 2),
    Low(label = "Baja", sortOrder = 1)
}
