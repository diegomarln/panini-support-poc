package cr.ac.una.paninisupport.ui.tickets.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cr.ac.una.paninisupport.domain.model.Ticket

@Composable
fun TicketDetailScreen(
    uiState: TicketDetailUiState,
    onBack: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Text(
                text = "Detalle del ticket",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    uiState.errorMessage != null -> {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    uiState.ticket != null -> {
                        TicketDetailContent(ticket = uiState.ticket)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Volver")
            }
        }
    }
}

@Composable
private fun TicketDetailContent(ticket: Ticket) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Identificador: ${ticket.id}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = ticket.title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Categoría: ${ticket.category.label}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Prioridad: ${ticket.priority.label}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Estado: ${ticket.status.label}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Proveedor: ${ticket.supplier}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Creado: ${ticket.createdAt}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Descripción",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = ticket.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
