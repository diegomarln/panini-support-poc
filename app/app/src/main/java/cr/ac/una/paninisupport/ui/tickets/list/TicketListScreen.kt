package cr.ac.una.paninisupport.ui.tickets.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import cr.ac.una.paninisupport.domain.model.TicketCategory

@Composable
fun TicketListScreen(
    uiState: TicketListUiState,
    ticketCreationEnabled: Boolean,
    inventoryCategoryVisible: Boolean,
    onOpenTicketDetail: (String) -> Unit,
    onCreateTicket: () -> Unit
) {
    val visibleTickets = if (inventoryCategoryVisible) {
        uiState.tickets
    } else {
        uiState.tickets.filter { it.category != TicketCategory.Inventory }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Tickets de soporte",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Listado priorizado de incidencias internas de Panini.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (ticketCreationEnabled) {
                OutlinedButton(
                    onClick = onCreateTicket,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Crear ticket")
                }
            } else {
                Text(
                    text = "La creación de tickets está deshabilitada durante la validación interna.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

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
                visibleTickets.isEmpty() -> {
                    Text(
                        text = "No hay tickets registrados.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(visibleTickets, key = { it.id }) { ticket ->
                            TicketRow(
                                ticket = ticket,
                                onClick = { onOpenTicketDetail(ticket.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TicketRow(
    ticket: Ticket,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = ticket.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Prioridad: ${ticket.priority.label}  ·  Estado: ${ticket.status.label}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Categoría: ${ticket.category.label}",
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
        }
    }
}
