package cr.ac.una.paninisupport.ui.tickets.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cr.ac.una.paninisupport.domain.model.Ticket
import cr.ac.una.paninisupport.domain.model.TicketPriority
import cr.ac.una.paninisupport.domain.model.TicketStatus

@Composable
fun TicketDetailScreen(
    uiState: TicketDetailUiState,
    priorityUpdateEnabled: Boolean,
    onStatusSelected: (TicketStatus) -> Unit,
    onPrioritySelected: (TicketPriority) -> Unit,
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
                    uiState.ticket != null -> {
                        TicketDetailContent(
                            ticket = uiState.ticket,
                            errorMessage = uiState.errorMessage,
                            priorityUpdateEnabled = priorityUpdateEnabled,
                            onStatusSelected = onStatusSelected,
                            onPrioritySelected = onPrioritySelected
                        )
                    }
                    uiState.errorMessage != null -> {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
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
private fun TicketDetailContent(
    ticket: Ticket,
    errorMessage: String?,
    priorityUpdateEnabled: Boolean,
    onStatusSelected: (TicketStatus) -> Unit,
    onPrioritySelected: (TicketPriority) -> Unit
) {
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
            text = "Proveedor: ${ticket.supplier}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Creado: ${ticket.createdAt}",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Estado actual: ${ticket.status.label}",
            style = MaterialTheme.typography.titleSmall
        )
        StatusChipsRow(
            selected = ticket.status,
            onSelect = onStatusSelected
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Prioridad actual: ${ticket.priority.label}",
            style = MaterialTheme.typography.titleSmall
        )
        if (priorityUpdateEnabled) {
            PriorityChipsRow(
                selected = ticket.priority,
                onSelect = onPrioritySelected
            )
        } else {
            Text(
                text = "La actualización de prioridad está deshabilitada durante la validación interna.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

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

@Composable
private fun StatusChipsRow(
    selected: TicketStatus,
    onSelect: (TicketStatus) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TicketStatus.values().forEach { status ->
            FilterChip(
                selected = selected == status,
                onClick = { onSelect(status) },
                label = { Text(text = status.label) },
                colors = FilterChipDefaults.filterChipColors()
            )
        }
    }
}

@Composable
private fun PriorityChipsRow(
    selected: TicketPriority,
    onSelect: (TicketPriority) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TicketPriority.values().forEach { priority ->
            FilterChip(
                selected = selected == priority,
                onClick = { onSelect(priority) },
                label = { Text(text = priority.label) },
                colors = FilterChipDefaults.filterChipColors()
            )
        }
    }
}
