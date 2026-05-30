# Flujo reactivo de tickets - Panini Support PoC

## 1. Objetivo del flujo reactivo
La aplicación móvil de Panini Support mantiene consistente la información
de tickets de soporte interno sin que el equipo deba refrescar las
pantallas manualmente. Cualquier cambio en los tickets (creación,
actualización de estado o de prioridad) se propaga automáticamente al
listado y al detalle. Esto reduce el riesgo de actuar sobre información
desactualizada durante la operación de distribución del álbum FIFA 2026 y
la coordinación con puntos de venta.

## 2. Fuente de verdad
`InMemoryTicketRepository` es la única fuente de verdad para los tickets
durante esta fase. Internamente mantiene un
`MutableStateFlow<List<Ticket>>` con la lista priorizada y expone un
`StateFlow<List<Ticket>>` solo lectura mediante la interfaz
`TicketRepository`. Toda mutación pasa por el repositorio; ningún
ViewModel modifica los datos por su lado.

La lista se ordena siempre con un comparador único:

- Prioridad descendente, usando `TicketPriority.sortOrder` (Crítica > Alta
  > Media > Baja).
- Fecha de creación descendente como desempate.

`StateFlow` se eligió como state holder principal porque las pantallas
solo necesitan el último estado conocido y un valor inicial garantizado al
suscribirse, lo cual evita parpadeos al navegar entre el listado y el
detalle.

## 3. Creación de tickets
1. El usuario completa el formulario en `CreateTicketScreen`.
2. `CreateTicketViewModel` valida los campos y llama a
   `repository.createTicket(...)`.
3. El repositorio genera un identificador correlativo (`TCK-XXX`), asigna
   la fecha actual y el estado inicial `Open`, agrega el ticket a la lista
   y reaplica el ordenamiento por prioridad.
4. El `StateFlow` emite la nueva lista a todos los colectores activos.
5. `TicketListViewModel`, que observa `repository.tickets` con `map` y
   `stateIn`, recompone su `uiState` y `TicketListScreen` muestra el
   ticket sin acción manual del usuario.

Si la prioridad del nuevo ticket es Crítica o Alta, aparece en la parte
superior del listado de manera natural por el ordenamiento del
repositorio.

## 4. Actualización de prioridad
1. En `TicketDetailScreen`, el usuario selecciona una nueva prioridad
   mediante los chips correspondientes (cuando el Feature Flag lo
   permite).
2. `TicketDetailViewModel.onPrioritySelected` invoca
   `repository.updateTicketPriority(ticketId, priority)`.
3. El repositorio reemplaza el ticket por una copia con la nueva
   prioridad y reaplica el ordenamiento.
4. `TicketDetailViewModel` refresca su `uiState` con el ticket
   actualizado que retorna el repositorio.
5. `TicketListViewModel` recibe la nueva lista por el mismo `StateFlow` y
   reordena la `LazyColumn` automáticamente. Al regresar al listado, el
   ticket aparece en su nueva posición sin recarga manual.

Este flujo demuestra que el cambio de prioridad reordena la lista sin que
la pantalla del listado tenga que solicitar datos otra vez.

## 5. Actualización de estado
1. En `TicketDetailScreen`, el usuario selecciona un nuevo estado entre
   los disponibles (`Open`, `InProgress`, `Blocked`, `Resolved`).
2. `TicketDetailViewModel.onStatusSelected` invoca
   `repository.updateTicketStatus(ticketId, status)`.
3. El repositorio reemplaza el ticket por una copia con el nuevo estado.
   La operación reaplica el ordenamiento por consistencia, aunque el
   estado no participa en el orden visible del listado.
4. `TicketDetailViewModel` refresca su `uiState`.
5. `TicketListViewModel` recibe la nueva lista. Como el estado no afecta
   el ordenamiento, el listado mantiene la misma posición pero refleja el
   nuevo estado en la tarjeta correspondiente.

## 6. Uso de StateFlow
`StateFlow` cumple tres funciones en la solución:

- Mantener la lista actual de tickets como única fuente de verdad
  observable.
- Garantizar un valor inicial para los suscriptores nuevos, evitando
  pantallas vacías al navegar entre listado y detalle.
- Habilitar la conexión natural con Compose: `collectAsState()` traduce
  el flujo en estado que provoca recomposición cuando cambia.

`TicketListViewModel.uiState` se construye con
`repository.tickets.map { ... }.stateIn(...)`, manteniendo el ciclo de
vida ligado al ViewModel.

## 7. Uso de TicketEvent
`TicketEvent`, en `domain/event/TicketEvent.kt`, modela eventos de una
sola vez relacionados con la operación sobre tickets:

- `TicketCreated(ticketId)`
- `TicketStatusUpdated(ticketId)`
- `TicketPriorityUpdated(ticketId)`

`InMemoryTicketRepository` expone estos eventos a través de un
`SharedFlow<TicketEvent>`. La actualización del listado y del detalle no
depende de los eventos: depende exclusivamente del `StateFlow`. Los
eventos están disponibles para casos puntuales que requieren ser
observados solo una vez, como mostrar un mensaje de confirmación tras
crear un ticket o disparar telemetría en una fase futura.

## 8. Continuidad técnica
Cuando se sustituya `InMemoryTicketRepository` por una implementación
remota:

- La nueva implementación debe seguir exponiendo `tickets` como
  `StateFlow<List<Ticket>>` para no romper a los ViewModels existentes.
- El `StateFlow` puede alimentarse desde la respuesta del backend al
  cargar y desde respuestas a `POST`/`PATCH` para reflejar los cambios.
- El `SharedFlow<TicketEvent>` puede conservarse o conectarse a un canal
  de mensajería en tiempo real si Panini decide actualizaciones push en
  una fase posterior.

Mantener este contrato reactivo en la capa de dominio permite que las
pantallas, los ViewModels y la lógica de Feature Flags continúen
funcionando sin cambios cuando se integre el backend real.
