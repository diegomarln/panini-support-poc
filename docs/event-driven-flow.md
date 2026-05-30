# Flujo reactivo de tickets - Panini Support PoC

Este documento describe el flujo reactivo que la solución móvil de Panini
Support utiliza para mantener consistente la lista de tickets de soporte
interno sin necesidad de refrescar manualmente la UI. Está dirigido al
equipo de ingeniería de Panini que continuará el desarrollo en fases
posteriores.

## Flujo de extremo a extremo
1. Una persona del equipo interno crea un ticket desde la pantalla de
   creación.
2. El repositorio de tickets actualiza su estado en memoria, que es la
   única fuente de verdad para la lista.
3. El ViewModel de la lista observa ese estado mediante un `StateFlow`
   expuesto por el repositorio.
4. `TicketListScreen` se recompone automáticamente y muestra el nuevo
   ticket sin acción manual del usuario.
5. Cuando se actualiza la prioridad de un ticket existente, el repositorio
   emite el nuevo estado y la lista se reordena automáticamente según la
   prioridad, manteniendo arriba los casos críticos de soporte interno.

## State holder principal: StateFlow
- `StateFlow` es el state holder principal de la solución móvil. Las
  pantallas solo necesitan conocer el estado actual de la lista de tickets,
  no el historial de eventos.
- Al tratarse de un flujo en caliente con valor inicial, garantiza que
  cualquier nueva suscripción reciba inmediatamente el último estado
  conocido, lo que evita parpadeos y pantallas vacías al navegar entre
  vistas.

## Eventos de una sola vez: SharedFlow opcional
- Para eventos de UI que ocurren una sola vez, como mostrar un snackbar
  tras crear un ticket o notificar un error puntual, se puede emplear un
  `SharedFlow` o un pequeño flujo de eventos dedicado.
- Estos eventos no representan estado persistente y, por lo tanto, no
  pertenecen al `StateFlow` principal de la lista de tickets.

## Valor para la operación de Panini
Este flujo reactivo evita que el equipo interno de Panini tenga que
refrescar manualmente la pantalla de soporte para ver los cambios. La
lista de tickets se mantiene consistente durante la operación diaria, lo
que reduce el riesgo de actuar sobre información desactualizada en
incidencias logísticas, faltantes de inventario o coordinación con
puntos de venta. El mismo patrón es compatible con la integración futura
con backend: bastará con que el repositorio reciba el estado desde un
servicio remoto, sin cambios en la UI.
