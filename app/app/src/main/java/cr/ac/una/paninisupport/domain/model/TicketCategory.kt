package cr.ac.una.paninisupport.domain.model

enum class TicketCategory(val label: String) {
    Distribution(label = "Distribución"),
    Inventory(label = "Inventario"),
    Supplier(label = "Proveedores"),
    Quality(label = "Calidad"),
    PointOfSale(label = "Puntos de venta"),
    Logistics(label = "Logística")
}
