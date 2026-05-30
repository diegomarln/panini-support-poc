package cr.ac.una.paninisupport.ui.tickets.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TicketListScreen(
    onOpenTicketDetail: (String) -> Unit,
    onCreateTicket: () -> Unit,
    sampleTicketId: String
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tickets de soporte",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Listado de tickets internos de Panini.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onOpenTicketDetail(sampleTicketId) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ver detalle")
            }
            OutlinedButton(
                onClick = onCreateTicket,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Crear ticket")
            }
        }
    }
}
