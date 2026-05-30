package cr.ac.una.paninisupport.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cr.ac.una.paninisupport.ui.auth.LoginScreen
import cr.ac.una.paninisupport.ui.tickets.create.CreateTicketScreen
import cr.ac.una.paninisupport.ui.tickets.detail.TicketDetailScreen
import cr.ac.una.paninisupport.ui.tickets.list.TicketListScreen

private const val SAMPLE_TICKET_ID = "TCK-001"

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Login
    ) {
        composable(AppRoute.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppRoute.TicketList) {
                        popUpTo(AppRoute.Login) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoute.TicketList) {
            TicketListScreen(
                onOpenTicketDetail = { ticketId ->
                    navController.navigate(AppRoute.ticketDetail(ticketId))
                },
                onCreateTicket = {
                    navController.navigate(AppRoute.CreateTicket)
                },
                sampleTicketId = SAMPLE_TICKET_ID
            )
        }

        composable(
            route = AppRoute.TicketDetailPattern,
            arguments = listOf(
                navArgument(AppRoute.TicketDetailArgId) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments
                ?.getString(AppRoute.TicketDetailArgId)
                .orEmpty()
            TicketDetailScreen(
                ticketId = ticketId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.CreateTicket) {
            CreateTicketScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
