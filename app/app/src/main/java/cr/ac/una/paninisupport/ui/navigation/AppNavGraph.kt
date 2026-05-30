package cr.ac.una.paninisupport.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cr.ac.una.paninisupport.core.di.AppContainer
import cr.ac.una.paninisupport.ui.auth.LoginScreen
import cr.ac.una.paninisupport.ui.auth.LoginViewModel
import cr.ac.una.paninisupport.ui.tickets.create.CreateTicketScreen
import cr.ac.una.paninisupport.ui.tickets.detail.TicketDetailScreen
import cr.ac.una.paninisupport.ui.tickets.detail.TicketDetailViewModel
import cr.ac.una.paninisupport.ui.tickets.list.TicketListScreen
import cr.ac.una.paninisupport.ui.tickets.list.TicketListViewModel
import cr.ac.una.paninisupport.ui.viewmodel.TicketViewModelFactory

@Composable
fun AppNavGraph(appContainer: AppContainer) {
    val navController = rememberNavController()
    val ticketViewModelFactory = remember(appContainer) {
        TicketViewModelFactory(appContainer.ticketRepository)
    }

    NavHost(
        navController = navController,
        startDestination = AppRoute.Login
    ) {
        composable(AppRoute.Login) {
            val loginViewModel: LoginViewModel = viewModel()
            val uiState by loginViewModel.uiState.collectAsState()

            LaunchedEffect(uiState.isLoggedIn) {
                if (uiState.isLoggedIn) {
                    navController.navigate(AppRoute.TicketList) {
                        popUpTo(AppRoute.Login) { inclusive = true }
                    }
                }
            }

            LoginScreen(
                uiState = uiState,
                onEmailChanged = loginViewModel::onEmailChanged,
                onPasswordChanged = loginViewModel::onPasswordChanged,
                onLogin = loginViewModel::login
            )
        }

        composable(AppRoute.TicketList) {
            val ticketListViewModel: TicketListViewModel =
                viewModel(factory = ticketViewModelFactory)
            val uiState by ticketListViewModel.uiState.collectAsState()

            TicketListScreen(
                uiState = uiState,
                onOpenTicketDetail = { ticketId ->
                    navController.navigate(AppRoute.ticketDetail(ticketId))
                },
                onCreateTicket = {
                    navController.navigate(AppRoute.CreateTicket)
                }
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

            val ticketDetailViewModel: TicketDetailViewModel =
                viewModel(factory = ticketViewModelFactory)

            LaunchedEffect(ticketId) {
                ticketDetailViewModel.loadTicket(ticketId)
            }

            val uiState by ticketDetailViewModel.uiState.collectAsState()

            TicketDetailScreen(
                uiState = uiState,
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
