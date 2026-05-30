package cr.ac.una.paninisupport.data.mock

import cr.ac.una.paninisupport.domain.model.Ticket
import cr.ac.una.paninisupport.domain.model.TicketCategory
import cr.ac.una.paninisupport.domain.model.TicketPriority
import cr.ac.una.paninisupport.domain.model.TicketStatus

object MockTicketDataSource {

    fun getTickets(): List<Ticket> = listOf(
        Ticket(
            id = "TCK-001",
            title = "Faltante de sobres en punto de venta Mall San José",
            description = "El punto de venta del Mall San José reporta un faltante de 1.200 sobres del álbum FIFA 2026 antes del fin de semana de mayor afluencia. Se requiere reposición prioritaria desde la bodega central.",
            supplier = "Distribuidora Centro S.A.",
            createdAt = "2026-05-25",
            category = TicketCategory.Inventory,
            priority = TicketPriority.Critical,
            status = TicketStatus.Open
        ),
        Ticket(
            id = "TCK-002",
            title = "Retraso en entrega de paquetes promocionales",
            description = "El proveedor de transporte reporta un retraso estimado de 48 horas en la entrega de paquetes promocionales del álbum FIFA 2026 asignados a la ruta del Pacífico Central. Se requiere replanificar la coordinación con los puntos de venta afectados.",
            supplier = "TransExpress CR",
            createdAt = "2026-05-26",
            category = TicketCategory.Distribution,
            priority = TicketPriority.High,
            status = TicketStatus.InProgress
        ),
        Ticket(
            id = "TCK-003",
            title = "Lote con álbumes dañados en bodega central",
            description = "Se detecta el lote LT-2026-118 con tapas despegadas en aproximadamente el 12% de las unidades. Se solicita coordinación con la imprenta para gestionar la reposición y retirar el lote del inventario disponible.",
            supplier = "Impresora Italprint",
            createdAt = "2026-05-22",
            category = TicketCategory.Quality,
            priority = TicketPriority.Critical,
            status = TicketStatus.Blocked
        ),
        Ticket(
            id = "TCK-004",
            title = "Diferencia entre guía de despacho e inventario recibido",
            description = "El centro de distribución de Heredia recibe 480 cajas, pero la guía de despacho indica 510. Se requiere conciliación con el proveedor logístico y ajuste en el inventario antes de continuar con la distribución a puntos de venta.",
            supplier = "TransExpress CR",
            createdAt = "2026-05-28",
            category = TicketCategory.Logistics,
            priority = TicketPriority.High,
            status = TicketStatus.Open
        ),
        Ticket(
            id = "TCK-005",
            title = "Proveedor con entrega parcial de material de empaque",
            description = "El proveedor entregó únicamente el 65% del pedido de bolsas plásticas para empaque del álbum FIFA 2026. Se solicita confirmación de la fecha de entrega del saldo restante para evitar atrasos en la línea de empaque.",
            supplier = "Papelera Andina",
            createdAt = "2026-05-20",
            category = TicketCategory.Supplier,
            priority = TicketPriority.Medium,
            status = TicketStatus.InProgress
        ),
        Ticket(
            id = "TCK-006",
            title = "Punto de venta sin reposición programada en Liberia",
            description = "La tienda asociada en Liberia no aparece en la programación de reposición de la semana actual. El stock actual no cubre la demanda proyectada del fin de semana. Se requiere coordinar un despacho extraordinario.",
            supplier = "Distribuidora Centro S.A.",
            createdAt = "2026-05-27",
            category = TicketCategory.PointOfSale,
            priority = TicketPriority.Medium,
            status = TicketStatus.Open
        ),
        Ticket(
            id = "TCK-007",
            title = "Paquetes asignados a ruta incorrecta",
            description = "Treinta paquetes destinados a Guanacaste fueron cargados por error en la ruta de Cartago. Los paquetes fueron reasignados y reentregados el día siguiente. Se solicita revisar el proceso de etiquetado de rutas en el centro de distribución.",
            supplier = "TransExpress CR",
            createdAt = "2026-05-18",
            category = TicketCategory.Logistics,
            priority = TicketPriority.High,
            status = TicketStatus.Resolved
        ),
        Ticket(
            id = "TCK-008",
            title = "Reporte duplicado por coordinación entre equipos internos",
            description = "El incidente del punto de venta de Alajuela fue reportado de manera independiente por el equipo de logística y por el equipo comercial. Se consolida la información en el ticket TCK-006 para evitar duplicidad de seguimiento.",
            supplier = "Equipo interno de soporte",
            createdAt = "2026-05-15",
            category = TicketCategory.PointOfSale,
            priority = TicketPriority.Low,
            status = TicketStatus.Resolved
        ),
        Ticket(
            id = "TCK-009",
            title = "Diferencia de inventario en bodega central GAM",
            description = "El conteo cíclico reporta una diferencia de 850 sobres entre el sistema y el conteo físico en la bodega central del GAM. Se inicia revisión conjunta entre el equipo de inventario y el equipo de operaciones para identificar la causa.",
            supplier = "Bodega Central GAM",
            createdAt = "2026-05-29",
            category = TicketCategory.Inventory,
            priority = TicketPriority.Critical,
            status = TicketStatus.InProgress
        )
    )
}
