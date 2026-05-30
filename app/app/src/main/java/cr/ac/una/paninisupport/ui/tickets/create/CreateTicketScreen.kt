package cr.ac.una.paninisupport.ui.tickets.create

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cr.ac.una.paninisupport.domain.model.TicketCategory
import cr.ac.una.paninisupport.domain.model.TicketPriority

@Composable
fun CreateTicketScreen(
    uiState: CreateTicketUiState,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSupplierChanged: (String) -> Unit,
    onCategorySelected: (TicketCategory) -> Unit,
    onPrioritySelected: (TicketPriority) -> Unit,
    onCreate: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Nuevo ticket",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Registre una incidencia interna de Panini Support.",
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChanged,
                label = { Text(text = "Título") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChanged,
                label = { Text(text = "Descripción") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.supplier,
                onValueChange = onSupplierChanged,
                label = { Text(text = "Proveedor o equipo interno") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Categoría",
                style = MaterialTheme.typography.titleSmall
            )
            CategoryChipsRow(
                selected = uiState.category,
                onSelect = onCategorySelected
            )

            Text(
                text = "Prioridad",
                style = MaterialTheme.typography.titleSmall
            )
            PriorityChipsRow(
                selected = uiState.priority,
                onSelect = onPrioritySelected
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onCreate,
                enabled = !uiState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Crear ticket")
            }
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
private fun CategoryChipsRow(
    selected: TicketCategory,
    onSelect: (TicketCategory) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TicketCategory.values().forEach { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(category) },
                label = { Text(text = category.label) },
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
