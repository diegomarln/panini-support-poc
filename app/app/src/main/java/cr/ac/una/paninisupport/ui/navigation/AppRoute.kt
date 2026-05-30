package cr.ac.una.paninisupport.ui.navigation

object AppRoute {
    const val Login = "login"
    const val TicketList = "ticket_list"
    const val CreateTicket = "create_ticket"

    const val TicketDetailArgId = "ticketId"
    const val TicketDetailPattern = "ticket_detail/{$TicketDetailArgId}"

    fun ticketDetail(ticketId: String): String = "ticket_detail/$ticketId"
}
